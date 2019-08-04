package Fundamentals;

import libraries.*;

public class DataTypes {
    //the bag, the queue, and the stack

    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        String line = "10 9 8 7 6 5 4 3 2 1";
        String[] arr = line.split("\\s+");
        for (String str : arr) {
            list.insertHead(Integer.parseInt(str));
        }
        list.print();
        list.first = reverseIterativeSolution(list.first);
        list.print();
        list.first = reverseRecursiveSolution(list.first);
        list.print();
    }

    static Node<Integer> reverseIterativeSolution(Node<Integer> first) {
        if (first != null && first.next != null) {
            Node<Integer> reverse = null;
            while (first != null) {
                Node<Integer> second = first.next;
                first.next = reverse;
                reverse = first;
                first = second;
            }
            return reverse;
        }
        return first;
    }

    static Node<Integer> reverseRecursiveSolution(Node<Integer> first) {
        if (first == null || first.next == null) {
            return first;
        }
        Node<Integer> second = first.next;
        Node<Integer> reverse = reverseRecursiveSolution(second);
        second.next = first;
        first.next = null;
        return reverse;
    }
}