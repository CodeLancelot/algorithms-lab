package Fundamentals;

import libraries.*;

public class DataTypes {
    //the bag, the queue, and the stack

    public static void main(String[] args) {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        String line = "10 9 8 7 6 5 4 3 2 1";
        String[] arr = line.split("\\s+");
        for (String str : arr) {
            list.insertHead(str);
            list.insertTail(str);
        }
        list.print();

        for (int i = 0; i < 5; i++) {
            list.removeHead();
            list.removeTail();
        }
        list.print();

        list.insertAfter(10, "tail");
        list.insertBefore(1,"head");
        list.insertAfter(6, "a");
        list.insertBefore(8,"b");
        list.insertAfter(7, "c");
        list.print();

        list.removeNode(1);
        list.removeNode(list.size());
        list.removeNode(7);
        list.print();
    }
}