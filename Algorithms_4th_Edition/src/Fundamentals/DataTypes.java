package Fundamentals;

import libraries.*;

public class DataTypes {
    //the bag, the queue, and the stack

    public static void main(String[] args) {
        String line = "10 9 8 7 6 5 4 3 2 1";
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
        list.insertHead("a");
        list.insertAfter(list.getNode(1), list.createNode("a"));
        list.insertAfter(list.getNode(2), list.createNode("a"));
        list.insertAfter(list.getNode(6), list.createNode("b"));
        list.insertAfter(list.getNode(11), list.createNode("a"));
        list.insertAfter(list.getNode(15), list.createNode("a"));
        list.print();
        remove(list, "a");
        list.print();
    }

    static void remove(LinkedList<String> list, String key) {
        Node node = list.first;
        while (node != null) {
            if (key.equals(node.item)) {
                Node next = node.next;
                list.removeNode(node);
                node = next;
            }
            else {
                node = node.next;
            }
        }
    }
}