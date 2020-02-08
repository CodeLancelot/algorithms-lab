package Fundamentals;

import libraries.*;

public class Tools {
    static void printArray(int[] arr) {
        StdOut.println();
        StdOut.print('[');
        for (int i = 0; i < arr.length; i++) {
            StdOut.print(arr[i]);
            if (i != arr.length - 1) {
                StdOut.print(' ');
            }
        }
        StdOut.println(']');
    }

    public static <T> void printArray(T[] arr) {
        StdOut.println();
        StdOut.print('[');
        for (int i = 0; i < arr.length; i++) {
            StdOut.print(arr[i]);
            if (i != arr.length - 1) {
                StdOut.print(' ');
            }
        }
        StdOut.println(']');
    }

    public static void printArray(double[] arr, int precision) {
        StdOut.println();
        StdOut.print('[');
        for (int i = 0; i < arr.length; i++) {
            String formatStr = "%." + precision + 'f';
            StdOut.printf(formatStr, arr[i]);
            if (i != arr.length - 1) {
                StdOut.print(' ');
            }
        }
        StdOut.println(']');
    }

    static void printMatrix(int[][] matrix) {
        int count = matrix[0].length;
        StdOut.println();
        StdOut.println();
        for (int[] row : matrix) {
            StdOut.print('(');
            for (int col = 0; col < count; col++) {
                StdOut.print(row[col]);
                if (col < count - 1) {
                    StdOut.print(" ");
                }
            }
            StdOut.println(')');
        }
        StdOut.println();
    }

    static <T> void printMatrix(T[][] matrix) {
        int count = matrix[0].length;
        StdOut.println();
        StdOut.println();
        for (T[] row : matrix) {
            StdOut.print('(');
            for (int col = 0; col < count; col++) {
                StdOut.print(row[col]);
                if (col < count - 1) {
                    StdOut.print(" ");
                }
            }
            StdOut.println(')');
        }
        StdOut.println();
    }

    static void printMatrix(double[][] matrix, int precision) {
        int count = matrix[0].length;
        StdOut.println();
        StdOut.println();
        for (double[] row : matrix) {
            StdOut.print('(');
            for (int col = 0; col < count; col++) {
                String formatStr = "%." + precision + 'f';
                StdOut.printf(formatStr, row[col]);
                if (col < count - 1) {
                    StdOut.print(" ");
                }
            }
            StdOut.println(')');
        }
        StdOut.println();
    }

    static String convertInfixToPostfix(String infix) {
        String[] arr = infix.split("\\s+");
        String postfix = "";
        Stack<String> oStack = new Stack<>();
        for (String str : arr) {
            if (str.equals("(")) {
                oStack.push("(");
            } else if (str.equals(")")) {
                String op = oStack.pop();
                while (!op.equals("(")) {
                    postfix += op + " ";
                    op = oStack.pop();
                }
            } else if (isOperator(str)) {
                while (oStack.size() != 0) {
                    int p1 = precedence(str), p2 = precedence(oStack.peek());
                    if (p1 > p2) {
                        break;
                    } else {
                        postfix += oStack.pop() + " ";
                    }
                }
                oStack.push(str);
            } else {
                postfix += str + " ";
            }
        }

        while (oStack.size() != 0) {
            postfix += oStack.pop() + " ";
        }
        return postfix;
    }

    private static int precedence(String operator) {
        int precedence = 0;
        switch (operator) {
            case "+":
            case "-":
                precedence = 1;
                break;
            case "*":
            case "/":
                precedence = 3;
                break;
            case "^":
                precedence = 5;
                break;
        }
        return precedence;
    }

    static boolean isOperator(String str) {
        return (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/") || str.equals("^"));
    }

    static double evaluateBinaryOperation(String operator, double leftOperand, double rightOperand) {
        double result = Math.PI;
        switch (operator) {
            case "+":
                result = leftOperand + rightOperand;
                break;
            case "-":
                result = leftOperand - rightOperand;
                break;
            case "*":
                result = leftOperand * rightOperand;
                break;
            case "/":
                result = leftOperand / rightOperand;
                break;
        }
        return result;
    }

    static double evaluatePostfix(String postfix) {
        String[] arr = postfix.split("\\s+");
        Stack<Double> vStack = new Stack<>();
        for (String str : arr) {
            if (isOperator(str)) {
                double ro = vStack.pop(), lo = vStack.pop();
                double v = evaluateBinaryOperation(str, lo, ro);
                vStack.push(v);
            } else {
                vStack.push(Double.parseDouble(str));
            }
        }
        return vStack.pop();
    }

    public static <T> void shuffle(T[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            // Exchange a[i] with random element in a[i..N-1]
            int r = i + StdRandom.uniform(N - i);
            T temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
}