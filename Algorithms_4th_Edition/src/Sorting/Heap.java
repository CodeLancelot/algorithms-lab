package Sorting;

import Fundamentals.Tools;
import libraries.*;

public class Heap {
    private Heap() {
    }

    public static void sort(Comparable[] a) {
        int n = a.length;
        for (int k = n / 2; k >= 1; k--)
            sink(a, k, n);
        while (n > 1) {
            exch(a, 1, n--);
            sink(a, 1, n);
        }
    }

    private static void sink(Comparable[] a, int k, int n) {
        while (2 * k <= n) {
            int c = 2 * k;
            if (c < n && less(a, c, c + 1)) c++;
            if (!less(a, k, c)) break;
            exch(a, k, c);
            k = c;
        }
    }

    private static boolean less(Comparable[] a, int i, int j) {
        return a[i - 1].compareTo(a[j - 1]) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i - 1];
        a[i - 1] = a[j - 1];
        a[j - 1] = swap;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Integer[] a = new Integer[N];
        for (int i = 0; i < N; i++)
            a[i] = StdRandom.uniform(17);

        sort(a);
        assert Sort.isSorted(a);
        Tools.printArray(a);
    }
}
