package Sorting;

public class Merge extends Sort {
    private Merge() {
    }

    public static void sort(Comparable[] arr, DivideAndConquer type) {
        Comparable[] aux = new Comparable[arr.length];
        if (type == DivideAndConquer.TOP_DOWN) topDownSort(arr, 0, arr.length - 1, aux);
        else bottomUpSort(arr, aux);
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i - 1])) return false;
        return true;
    }

    private static void merge(Comparable[] a, int lo, int mid, int hi, Comparable[] aux) {
        // precondition: a[lo .. mid] and a[mid+1 .. hi] are sorted subarrays
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid + 1, hi);

        if (hi + 1 - lo >= 0) System.arraycopy(a, lo, aux, lo, hi + 1 - lo);
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[i], aux[j])) a[k] = aux[i++];
            else a[k] = aux[j++];
        }
    }

    private static void topDownSort(Comparable[] a, int lo, int hi, Comparable[] aux) {
        if (hi <= lo) return;
        int mid = (lo + hi) / 2;
        topDownSort(a, lo, mid, aux);
        topDownSort(a, mid + 1, hi, aux);
        merge(a, lo, mid, hi, aux);
    }

    private static void bottomUpSort(Comparable[] a, Comparable[] aux) {
        int N = a.length;
        for (int size = 1; size < N; size *= 2) {
            for (int lo = 0; lo + size < N; lo += size * 2) {
                int mid = lo + size - 1;
                int hi = Math.min(mid + size, N - 1);
                merge(a, lo, mid, hi, aux);
            }
        }
    }

    enum DivideAndConquer {
        TOP_DOWN,
        BOTTOM_UP
    }
}
