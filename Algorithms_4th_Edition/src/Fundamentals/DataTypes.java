package Fundamentals;

import libraries.*;

public class DataTypes {
    //the bag, the queue, and the stack

    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        String str = "a b c d e f g h i j k l";
        String[] arr = str.split(" ");
        for (String s : arr) {
            stack.push(s);
        }
        for (String s : stack) {
            StdOut.print(s + ' ');
        }

        Queue<Integer> queue = new Queue<>();
        int[] intArr = {1 ,2,3,4,5,6,7,8,9,10};
        for (int i : intArr) {
            queue.enqueue(i);
        }
        for (int i : queue) {
            StdOut.print(i);
            StdOut.print(' ');
        }

        Bag<String> bag = new Bag<>();
        for (String s : arr) {
            bag.add(s);
        }
        for (String s : bag) {
            StdOut.print(s + ' ');
        }
    }
}