package Fundamentals;

import libraries.*;

public class DataTypes {
    //the bag, the queue, and the stack

    public static void main(String[] args) {
        StdOut.println(convertInfixToPostfix());
    }

    static String convertInfixToPostfix() {
        String postfix = "";
        Stack<String> oStack = new Stack<>();
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            if (str.equals("(")) {
                oStack.push("(");
            } else if (str.equals(")")) {
                String op = oStack.pop();
                while (!op.equals("(")) {
                    postfix += op;
                    op = oStack.pop();
                }
            } else if (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/") || str.equals("^")) {
                while (oStack.size() != 0) {
                    if (hasHigherPrecedence(str, oStack)) {
                        break;
                    } else {
                        postfix += oStack.pop();
                    }
                }
                oStack.push(str);
            } else {
                postfix += str;
            }
        }

        while (oStack.size() != 0) {
            postfix += oStack.pop();
        }
        return postfix;
    }

    private static boolean hasHigherPrecedence(String str, Stack<String> oStack) {
        int p1 = precedence(str), p2 = precedence(oStack.peek());
        return p1 > p2;
    }

    private static int precedence(String operator) {
        int precedence = 0;
        if (operator.equals("^")) {
            precedence = 5;
        } else if (operator.equals("*") || operator.equals("/")) {
            precedence = 3;
        } else if (operator.equals("+") || operator.equals("-")) {
            precedence = 2;
        }
        return precedence;
    }
}