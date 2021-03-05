package ru.geekbrains.ntr_0307;

import ru.geekbrains.ntr_0307.annotations.AfterSuite;
import ru.geekbrains.ntr_0307.annotations.BeforeSuite;
import ru.geekbrains.ntr_0307.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleTests {

    private static Class testedClass;
    private static List<Method> methods;
    private static List<Method> beforeMethods;
    private static List<Method> afterMethods;
    private static List<Method> testMethods;

    private static Class test = Test.class;
    private static Class before = BeforeSuite.class;
    private static Class after = AfterSuite.class;

    private static final int MIN_PRIORITY = 0;
    private static final int MAX_PRIORITY = 10;

    public static void start(Class testedClass) {
        try {
            init(testedClass);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return;
        }

        try {
            testing();
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();

        }
    }

    private static void init(Class testedClass) throws RuntimeException {
        SimpleTests.testedClass = testedClass;
        methods = Arrays.asList(testedClass.getMethods());
        beforeMethods = getMethodsAnnotatedWith(testedClass, before);
        if (beforeMethods.size() > 1) {
            throw new RuntimeException(before.getName() + " annotation сan be used no more than once.");
        }
        afterMethods = getMethodsAnnotatedWith(testedClass, after);
        if (beforeMethods.size() > 1) {
            throw new RuntimeException(after.getName() + " annotation сan be used no more than once.");
        }

        testMethods = getMethodsAnnotatedWith(testedClass, test);

        testMethods.sort((o1, o2) -> getPriorityForTestAnnotation(o2) - getPriorityForTestAnnotation(o1));
    }

    private static void testing() throws InvocationTargetException, IllegalAccessException {

        if (!beforeMethods.isEmpty()) {
            beforeMethods.get(0).invoke(null);
        }

        for (Method method : testMethods) {
            method.invoke(null);
        }

        if (!afterMethods.isEmpty()) {
            afterMethods.get(0).invoke(null);
        }
    }

    private static int getPriorityForTestAnnotation(Method method) throws IllegalArgumentException {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Test) {
                int priority = ((Test) annotation).priority();
                if (priority < MIN_PRIORITY || priority > MAX_PRIORITY) {
                    throw new IllegalArgumentException(
                            String.format("Test priority for %s = %d. Priority must be in range [%d..%d].",
                                    method.getName(), priority, MIN_PRIORITY, MAX_PRIORITY)
                    );
                }
                return (priority);
            }
        }
        return 0;
    }

    public static List<Method> getMethodsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation) {
        final List<Method> methods = new ArrayList<Method>();
        Class<?> klass = type;
        while (klass != Object.class) {
            for (final Method method : klass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotation)) {
                    methods.add(method);
                }
            }
            klass = klass.getSuperclass();
        }
        return methods;
    }

    public static void main(String[] args) {
        start(CalcTest.class);
    }
}
