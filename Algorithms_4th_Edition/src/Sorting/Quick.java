package Sorting;

import Fundamentals.Tools;
import libraries.*;

public class Quick {
    // cutoff to insertion sort, must be >= 1
    private static final int CUTOFF = 8;

    private Quick() {
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

    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
    }

    private static void sortDuplicateKeys(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int lt = lo, i = lo + 1, gt = hi;
        Comparable v = a[lo];
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if (cmp < 0) Sort.exch(a, lt++, i++);
            else if (cmp > 0) Sort.exch(a, i, gt--);
            else i++;
        }
        sortDuplicateKeys(a, lo, lt - 1);
        sortDuplicateKeys(a, gt + 1, hi);
    }

    public static void sortDuplicateKeys(Comparable[] a) {
        StdRandom.shuffle(a);
        sortDuplicateKeys(a, 0, a.length - 1);
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Integer[] a = new Integer[N];
        for (int i = 0; i < N; i++)
            a[i] = StdRandom.uniform(3);

        sortDuplicateKeys(a);
        assert Sort.isSorted(a);
        Tools.printArray(a);
    }
}
