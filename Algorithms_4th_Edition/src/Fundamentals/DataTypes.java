package Fundamentals;

import libraries.*;

public class DataTypes {
    //the bag, the queue, and the stack

    public static void main(String[] args) {
        int operandCount = 0, operatorCount = 0;
        Stack<String> iStack = new Stack<>();
        Stack<String> oStack = new Stack<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            iStack.push(s);
        }

        for (String s : iStack) {
            StdOut.print(s + ' ');
        }

        while (!iStack.isEmpty()) {
            String str = iStack.pop();
            if (str.equals(")")) {
                oStack.push(str);
                if (operandCount > 0) {
                    operandCount--;
                }
            } else if (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/")) {
                oStack.push(str);
                operatorCount++;
            } else {
                oStack.push(str);
                operandCount++;
                if (operandCount == 2) {
                    while (operatorCount > 0) {
                        oStack.push("(");
                        operatorCount--;
                    }
                    operandCount = 0;
                }
            }
        }
        StdOut.println();
        for (String s : oStack) {
            StdOut.print(s + ' ');
        }
    }
}