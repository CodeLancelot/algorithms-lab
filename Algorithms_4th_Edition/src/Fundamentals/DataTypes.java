package Fundamentals;

import libraries.*;

public class DataTypes {
    //the bag, the queue, and the stack

    public static void main(String[] args) {
        String line = "10 9 8 7 6 5 4 3 2 1 0";
        LinkedList<String> list = new LinkedList<>();

        String[] arr = line.split("\\s+");
        for (String str : arr) {
            list.insertHead(str);
        }
        list.print();
        for (String str : args) {
            int index = Integer.parseInt(str);
            list.removeAfter(list.getNode(index));
            list.print();
        }
        list.insertAfter(list.getNode(5), list.createNode("b"));
        list.print();
    }
}