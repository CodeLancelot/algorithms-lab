package Fundamentals;

import libraries.*;

public class DataTypes {
    //the bag, the queue, and the stack

    public static void main(String[] args) {
        while (!StdIn.isEmpty()) {
            String infix = StdIn.readLine();
            String postfix = Tools.convertInfixToPostfix(infix);
            StdOut.println(postfix);
            StdOut.println(Tools.evaluatePostfix(postfix));
        }
    }
}