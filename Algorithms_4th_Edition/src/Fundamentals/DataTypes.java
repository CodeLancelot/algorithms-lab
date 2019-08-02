package Fundamentals;

import libraries.*;

public class DataTypes {
    //the bag, the queue, and the stack

    public static void main(String[] args) {
        Queue<String> operandQueue = new Queue<String>();
        Queue<String> operatorQueue = new Queue<String>();
        Stack<String> iStack = new Stack<String>();
        Stack<String> oStack = new Stack<String>();
        while (!StdIn.isEmpty()) { // Read token, push if operator.
            String s = StdIn.readString();
            iStack.push(s);
//            else if (s.equals(")"))
        }

        for (String s : iStack) {
            StdOut.print(s + ' ');
        }

        while (!iStack.isEmpty()) {
            String str = iStack.pop();
            if (str.equals(")")) {
                oStack.push(str);
            }
            else if (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/")) {
                operatorQueue.enqueue(str);
                oStack.push(str);
            }
            else {
                if (operatorQueue.size() == 0) {
                    oStack.push(str);
                }
                else {
                    operandQueue.enqueue(str);
                    String oprStr = operatorQueue.dequeue();
                    oStack.push(oprStr);
                    oStack.push(str);
                    oStack.push("(");
                }
            }
        }
    }
}