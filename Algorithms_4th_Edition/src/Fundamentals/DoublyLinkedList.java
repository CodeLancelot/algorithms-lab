package Fundamentals;

import libraries.*;

public class DoublyLinkedList<Type> {
    private DoubleNode head;
    private DoubleNode tail;
    private int N; // number of items

    private class DoubleNode { // nested class to define nodes
        Type item;
        DoubleNode last;
        DoubleNode next;
    }

    int size() {
        return N;
    }

    boolean isEmpty() {
        return N == 0;
    }

    void insertHead(Type item) {
        DoubleNode oldHead = head;
        head = new DoubleNode();
        head.item = item;
        if (oldHead != null) {
            head.next = oldHead;
            oldHead.last = head;
        } else {
            tail = head;
        }
        N++;
    }

    void insertTail(Type item) {
        DoubleNode oldTail = tail;
        tail = new DoubleNode();
        tail.item = item;
        if (oldTail != null) {
            tail.last = oldTail;
            oldTail.next = tail;
        } else {
            head = tail;
        }
        N++;
    }

    void insertBefore(int index, Type item) {
        DoubleNode node = getNode(index);
        if (node == null) {
            return;
        }
        if (node == head) {
            insertHead(item);
        } else {
            DoubleNode newNode = new DoubleNode();
            newNode.item = item;
            node.last.next = newNode;
            newNode.last = node.last;
            newNode.next = node;
            node.last = newNode;
            N++;
        }
    }

    void insertAfter(int index, Type item) {
        DoubleNode node = getNode(index);
        if (node == null) {
            return;
        }
        if (node == tail) {
            insertTail(item);
        } else {
            DoubleNode newNode = new DoubleNode();
            newNode.item = item;
            node.next.last = newNode;
            newNode.next = node.next;
            newNode.last = node;
            node.next = newNode;
            N++;
        }
    }

    private DoubleNode getNode(int k) {
        if (1 <= k && k <= N) {
            int i = 1;
            for (DoubleNode node = head; node != null; node = node.next) {
                if (i == k) {
                    return node;
                }
                i++;
            }
        }
        return null;
    }

    void removeHead() {
        if (head == tail) {
            head = tail = null;
        } else {
            head = head.next;
            head.last = null;
        }
        N--;
    }

    void removeTail() {
        if (head == tail) {
            head = tail = null;
        } else {
            tail = tail.last;
            tail.next = null;
        }
        N--;
    }

    void removeNode(int index) {
        DoubleNode node = getNode(index);
        if (node == null) {
            return;
        }
        if (node == head) {
            removeHead();
        } else if (node == tail) {
            removeTail();
        } else {
            node.last.next = node.next;
            node.next.last = node.last;
            N--;
        }
    }

    void print() {
        if (!isEmpty()) {
            StdOut.print("NULL <-> ");
        }
        for (DoubleNode node = head; node != null; node = node.next) {
            StdOut.print(node.item + " <-> ");
        }
        StdOut.print("NULL");
        StdOut.println(" (SIZE: " + size() + ")");
    }
}