package Fundamentals;

import libraries.*;

public class DataTypes {
    //the bag, the queue, and the stack

    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        String str = "0 1 2 3 4 5 6 7 8 9";
        String[] arr = str.split(" ");

        int i = 0, len = arr.length;
        while (!StdIn.isEmpty()) {
            String cmd = StdIn.readString();
            if ("push".equals(cmd) && i < len) {
                stack.push(arr[i++]);
            } else {
                String s = stack.pop();
                StdOut.println(s);
            }
            StdOut.print("Stack: ");
            for (String s : stack) {
                StdOut.print(s + " ");
            }
            StdOut.println();
            if (stack.isEmpty()) {
                break;
            }
        }
    }
}