package Fundamentals;

import libraries.*;

public class BasicProgrammingModel {
    public static void main(String[] args) {
        StdOut.println(multiply(2, 25));
        StdOut.println(multiply(3, 11));
        StdOut.println(multiply(19, 3));
        StdOut.println(exponentCalculate(2, 5));
        StdOut.println(exponentCalculate(3, 3));
        StdOut.println(exponentCalculate(25, 2));
    }

    public static int multiply(int a, int b) {
        if (b == 0) return 0;
        if (b % 2 == 0) return multiply(a+a, b/2);
        return multiply(a+a, b/2) + a;
    }

    public static int exponentCalculate(int a, int b) {
        if (b == 0) return 1;
        if (b % 2 == 0) return exponentCalculate(a*a, b/2);
        return exponentCalculate(a*a, b/2) * a;
    }
}