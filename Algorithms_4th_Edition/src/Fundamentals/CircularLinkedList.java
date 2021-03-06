package Fundamentals;

import libraries.*;

public class CircularLinkedList<Type> {
    private Node<Type> head;
    private Node<Type> rear;
    private int N; // number of items

    int size() {
        return N;
    }

    boolean isEmpty() {
        return N == 0;
    }

    void insertHead(Type item) {
        Node<Type> oldHead = head;
        head = new Node<>();
        head.item = item;
        if (oldHead != null) {
            head.next = oldHead;
            rear.next = head;
        } else {
            rear = head;
            head.next = rear;
        }
        N++;
    }

    void insertRear(Type item) {
        int rearIndex = 0;
        if (size() > 0) {
            rearIndex = size() - 1;
            insertAfter(rearIndex, item);
        } else {
            rear = new Node<>();
            rear.item = item;
            head = rear;
            rear.next = head;
            N++;
        }
    }

    void insertAfter(int index, Type item) {
        Node<Type> node = getNode(index);
        if (node != null) {
            Node<Type> newNode = new Node<>();
            newNode.item = item;
            newNode.next = node.next;
            node.next = newNode;
            if (node == rear) {
                rear = newNode;
            }
            N++;
        }
    }

    Node<Type> getNode(int index) {
        if (index >= 0) {
            index = index % N;
            int k = 0;
            for (Node node = head; node != null; node = node.next) {
                if (k == index) {
                    return node;
                }
                k++;
            }
        }
        return null;
    }

    Boolean isRear(Node<Type> node) {
        if (node != null && rear !=null) {
            return node == rear;
        }
        return false;
    }

    Boolean isRear(int index) {
        if (index >= 0) {
            index = index % N;
            Node<Type> node = getNode(index);
            return isRear(node);
        }
        return false;
    }

    void removeHead() {
        if (head == null) {
            return;
        }
        if (head == rear) {
            head = rear = null;
        } else {
            head = head.next;
            rear.next = head;
        }
        N--;
    }

    void removeNode(Node<Type> node) {
        if (node == null || head == null) {
            return;
        }
        if (node == head) {
            removeHead();
        } else {
            Node<Type> current = head;
            while (current.next != node) {
                current = current.next;
            }
            current.next = node.next;
            if (node == rear) {
                rear = current;
            }
            N--;
        }
    }

    void removeNode(int index) {
        Node<Type> node = getNode(index);
        removeNode(node);
    }

    void print() {
        if (isEmpty()) {
            StdOut.print("NULL ");
        } else {
            for (Node<Type> node = head; node.next != head; node = node.next) {
                StdOut.print(node.item + " <-> ");
            }
            StdOut.print(rear.item);
        }
        StdOut.println(" (SIZE: " + size() + ")");
    }
}