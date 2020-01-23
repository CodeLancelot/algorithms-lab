package Sorting;

public class Merge extends Sort {
    private static Comparable[] aux;

    public static void sort(Comparable[] arr, DivideAndConquer type) {
        aux = new Comparable[arr.length];
        if (type == DivideAndConquer.TOP_DOWN) topDownSort(arr, 0, arr.length - 1);
        else bottomUpSort(arr);
    }

    private static void merge(Comparable[] arr, int lo, int mid, int hi) {
        if (hi + 1 - lo >= 0) System.arraycopy(arr, lo, aux, lo, hi + 1 - lo);
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) arr[k] = aux[j++];
            else if (j > hi) arr[k] = aux[i++];
            else if (less(aux[i], aux[j])) arr[k] = aux[i++];
            else arr[k] = aux[j++];
        }
    }

    private static void topDownSort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int mid = (lo + hi) / 2;
        topDownSort(a, lo, mid);
        topDownSort(a, mid + 1, hi);
        merge(a, lo, mid, hi);
    }

    private static void bottomUpSort(Comparable[] a) {
        int N = a.length;
        for (int size = 1; size < N; size *= 2) {
            for (int lo = 0; lo + size < N; lo += size * 2) {
                int mid = lo + size - 1;
                int hi = Math.min(mid + size, N - 1);
                merge(a, lo, mid, hi);
            }
        }
    }

    enum DivideAndConquer {
        TOP_DOWN,
        BOTTOM_UP
    }
}
