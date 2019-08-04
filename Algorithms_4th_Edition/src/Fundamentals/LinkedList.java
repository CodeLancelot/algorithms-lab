package Fundamentals;

import libraries.*;

class LinkedList<Type> {
    private Node first;
    private int N; // number of items

    private class Node { // nested class to define nodes
        Type item;
        Node next;
    }

    void insertHead(Type item) {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        N++;
    }

    void delete(int k) {
        if (k <= N) {
            if (k == 1) {
                first = first.next;
                N--;
            } else if (k > 1) {
                int i = 1;
                for (Node node = first; node != null; node = node.next) {
                    if (i == k - 1) {
                        node.next = node.next.next;
                        N--;
                        break;
                    }
                    i++;
                }
            }
        }
    }

    Node getNode(int k) {
        if (1 <= k && k <= N) {
            int i = 1;
            for (Node node = first; node != null; node = node.next) {
                if (i == k) {
                    return node;
                }
                i++;
            }
        }
        return null;
    }

    void removeAfter(Node node) {
        if (node != null && node.next != null) {
            node.next = node.next.next;
        }
    }

    Node createNode(Type item) {
        Node node = new Node();
        node.item = item;
        return node;
    }

    void insertAfter(Node targetNode, Node newNode) {
        if (targetNode != null && newNode != null) {
            newNode.next = targetNode.next;
            targetNode.next = newNode;
            N++;
        }
    }

    void print() {
        for (Node node = first; node != null; node = node.next) {
            StdOut.print(node.item + " -> ");
        }
        StdOut.println("NULL");
    }
}