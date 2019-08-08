package Fundamentals;

import libraries.*;

public class DataTypes {
    //the bag, the queue, and the stack

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int M = Integer.parseInt(args[1]);


        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        list.print();
//        Queue<Integer> queue = new Queue<>();
        for (int i = 0; i < N; i++) {
            list.insertRear(i);
        }
        list. print();

        while (!list.isEmpty()) {
            int i = 0;
            while (i < M-1) {
                i++;
            }

            StdOut.print(list.getNode(i).item + " ");
            list.removeNode(i);
        }
        StdOut.println();
//            queue.enqueue(i);

//        while (!queue.isEmpty()) {
//            for (int i = 0; i < m-1; i++)
//                queue.enqueue(queue.dequeue());
//            StdOut.print(queue.dequeue() + " ");
//        }
//        StdOut.println();
    }
}