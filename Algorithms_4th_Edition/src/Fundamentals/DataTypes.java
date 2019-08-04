package Fundamentals;

import libraries.*;

public class DataTypes {
    //the bag, the queue, and the stack

    public static void main(String[] args) {
        String line = "10 9 8 17 6 15 4 3 22 1";
        LinkedList<Integer> list = new LinkedList<>();
        StdOut.println("max: " + max(list.first));
        StdOut.println("max: " + maxRecursiveSolution(list.first));

        String[] arr = line.split("\\s+");
        for (String str : arr) {
            list.insertHead(Integer.parseInt(str));
        }
        list.print();
        StdOut.println("max: " + max(list.first));
        StdOut.println("max: " + maxRecursiveSolution(list.first));
    }

    static int max(Node<Integer> first) {
        int max = 0;
        if (first == null) {
            return max;
        }
        Node<Integer> node = first.next;
        max = first.item;
        while(node != null) {
            if (node.item > max) {
                max = node.item;
            }
            node = node.next;
        }
        return max;
    }

    static int maxRecursiveSolution(Node<Integer> first) {
        int max = 0;
        if (first == null) {
            return max;
        }
        else {
            max = maxRecursiveSolution(first.next);
            if (max < first.item) {
                max =  first.item;
            }
            return max;
        }
    }
}