package Sorting;

import Fundamentals.Tools;
import libraries.*;

public class Quick {
    // cutoff to insertion sort, must be >= 1
    private static final int CUTOFF = 8;

    private Quick() {
    }

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        int m = medianIndex(a, lo, (lo + hi) / 2, hi);
        Sort.exch(a, i, m);
        Comparable v = a[i];
        while (true) {
            while (Sort.less(a[++i], v)) {
                if (i == hi) break;
            }
            while (Sort.less(v, a[--j])) {
                if (j == lo) break;
            }
            if (i >= j) break;
            Sort.exch(a, i, j);
        }
        Sort.exch(a, lo, j);
        return j;
    }

    private static int medianIndex(Comparable[] a, int i, int j, int k) {
        return Sort.less(a[i], a[j]) ? (Sort.less(a[j], a[k]) ? j : Sort.less(a[i], a[k]) ? k : i) : (Sort.less(a[i], a[k]) ? i : Sort.less(a[j], a[k]) ? k : j);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi - lo <= CUTOFF) {
            Insertion.sort(a, lo, hi);
            return;
        }
        int p = partition(a, lo, hi);
        sort(a, lo, p - 1);
        sort(a, p + 1, hi);
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Integer[] a = new Integer[N];
        for (int i = 0; i < N; i++)
            a[i] = StdRandom.uniform(N);

        sort(a);
        assert Sort.isSorted(a);
        Tools.printArray(a);
    }
}
