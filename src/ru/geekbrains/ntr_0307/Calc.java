package ru.geekbrains.ntr_0307;

public class Calc {
    private int x;
    private int y;

    public Calc(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int sum() {
        return x + y;
    }

    public int mul() {
        return x * y;
    }

    public int div() throws ArithmeticException {
        if (y == 0) throw new ArithmeticException();
        return x / y;
    }

    public int sub() {
        return x - y;
    }

}
