package ru.geekbrains.ntr_0307;

import ru.geekbrains.ntr_0307.annotations.AfterSuite;
import ru.geekbrains.ntr_0307.annotations.BeforeSuite;
import ru.geekbrains.ntr_0307.annotations.Test;

public class CalcTest {

    private static Calc calc;

    @BeforeSuite
    public static void init() {
        calc = new Calc(3, 2);
        System.out.println("Start of test");
    }

    @Test(priority = 10)
    public static void testSum() {
        System.out.print("Test sum: ");
        MyAssert.assertEquals(calc.sum(), 3 + 2);
    }

    @Test
    public static void testMul() {
        System.out.print("Test mul: ");
        MyAssert.assertEquals(calc.mul(), 3 * 2);
    }

    @Test(priority = 2)
    public static void testDiv() throws ArithmeticException {
        System.out.print("Test div: ");
        MyAssert.assertEquals(calc.div(), 3 / 2);
    }

    @Test(priority = 1)
    public static void testSub() {
        System.out.print("Test sub: ");
        MyAssert.assertEquals(calc.sub(), 3-2);
    }

    @AfterSuite
    public static void testEnd() {
        System.out.println("End of test");
    }
}
