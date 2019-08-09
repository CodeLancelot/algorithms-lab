package Fundamentals;

import libraries.*;

public class DataTypes {
    //the bag, the queue, and the stack

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int M = Integer.parseInt(args[1]);

        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.print();

        for (int i = 0; i < N; i++) {
            list.insertRear(i);
        }
        list.print();

        int nodeIndex = 0, numberOff = 0;
        while (!list.isEmpty()) {
            if (numberOff < M - 1) {
                numberOff++;
                if (!list.isRear(nodeIndex)) {
                    nodeIndex++;
                } else {
                    nodeIndex = 0;
                }
            } else {
                Node<Integer> node = list.getNode(nodeIndex);
                if (list.isRear(node)) {
                    nodeIndex = 0;
                }
                StdOut.print(node.item + " ");
                list.removeNode(node);
                numberOff = 0;
            }
        }
        StdOut.println();
    }
}