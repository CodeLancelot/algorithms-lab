package Fundamentals;

import libraries.*;

public class DataTypes {
    //the bag, the queue, and the stack

    public static void main(String[] args) {
        Stack<String> vStack = new Stack<>();
        Stack<String> oStack = new Stack<>();
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            if (str.equals(")")) {
                String value1 = vStack.pop(), value2 = vStack.pop(), operator = oStack.pop();
                String value = "(" + value2 + " " + operator + " " + value1 + ")";
                vStack.push(value);
            } else if (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/")) {
                oStack.push(str);
            } else {
                vStack.push(str);
            }
        }
        StdOut.println(vStack.pop());
    }
}