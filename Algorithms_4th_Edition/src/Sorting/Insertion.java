package Sorting;

import Fundamentals.Tools;
import libraries.*;

public class Insertion extends Sort {
    private Insertion() {
    }

    public static void sort(Comparable[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    // Mainly for some other high level sort method to use to deal with sub arrays
    public static void sort(Comparable[] arr, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            for (int j = i; j > lo && less(arr[j], arr[j - 1]); j--)
                exch(arr, j, j - 1);
        }
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
