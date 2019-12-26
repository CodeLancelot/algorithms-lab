package Sorting;

public class Insertion extends Sort {
    public static void sort(Comparable[] arr) {
        int N = arr.length;
        for (int i = 1; i < N; i++) {
            for (int j = i; j > 0 && less(arr[j], arr[j - 1]); j--)
                exch(arr, j, j - 1);
        }
    }
}
