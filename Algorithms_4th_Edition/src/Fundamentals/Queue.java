package Fundamentals;

import java.util.Iterator;

public class Queue<Item> implements Iterable<Item> {
    private Node<Item> first; // link to least recently added node
    private Node<Item> last; // link to most recently added node
    private int N; // number of items on the queue

    public boolean isEmpty() {
        return first == null;
    } // Or: N == 0.

    public int size() {
        return N;
    }

    public void enqueue(Item item) { // Add item to the end of the list.
        Node<Item> oldLast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else oldLast.next = last;
        N++;
    }

    public Item dequeue() { // Remove item from the beginning of the list.
        Item item = first.item;
        first = first.next;
        if (isEmpty()) last = null;
        N--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node<Item> current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}