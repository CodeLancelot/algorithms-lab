package Sorting;

import libraries.*;

public class Quick {
    private Quick() {
    }

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
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

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int p = partition(a, lo, hi);
        sort(a, lo, p - 1);
        sort(a, p + 1, hi);
    }
}
