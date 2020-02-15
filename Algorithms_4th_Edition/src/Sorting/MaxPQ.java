package Sorting;

import java.util.NoSuchElementException;

import libraries.*;

public class MaxPQ<Key extends Comparable<Key>> {
    private Key[] pq; // heap-ordered complete binary tree
    private int N = 0; // in pq[1..N] with pq[0] unused

    public MaxPQ() {
        this(7);
    }

    public MaxPQ(int capacity) {
        pq = (Key[]) new Comparable[capacity + 1];
    }

    public MaxPQ(Key[] keys) {
        N = keys.length;
        pq = (Key[]) new Comparable[N + 1];
        for (int i = 0; i < N; i++)
            pq[i + 1] = keys[i];
        for (int k = N / 2; k >= 1; k--)
            sink(k);
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void insert(Key v) {
        if (N == pq.length - 1) resize(2 * pq.length);
        pq[++N] = v;
        swim(N);
    }

    public Key delMax() {
        Key max = pq[1];
        exch(1, N);
        pq[N--] = null; // to avoid loitering and help with garbage collection
        if ((N > 0) && (N == (pq.length - 1) / 4)) resize(pq.length / 2);
        sink(1);
        return max;
    }

    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    private void resize(int capacity) {
        assert capacity > N;
        Key[] temp = (Key[]) new Comparable[capacity];
        for (int i = 1; i <= N; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }

    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k / 2, k);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (k * 2 <= N) {
            int p = k * 2;
            if (p < N && less(p, p + 1)) p++;
            if (!less(k, p)) break;
            exch(k, p);
            k = p;
        }
    }

    public static void main(String[] args) {
        String line = "A B C F G I I Z B D H P Q Q A B E F J N";
        String[] arr = line.split("\\s+");
        MaxPQ<String> pq = new MaxPQ<>(arr);
        pq.insert("L");
        while (!pq.isEmpty()) {
            StdOut.print(pq.delMax() + " ");
        }
    }
}
