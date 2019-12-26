package Sorting;

public class Selection extends Sort {
    public static void sort(Comparable[] arr) {
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            int min = i; // index of minimal entry in current loop.
            for (int j = i + 1; j < N; j++) {
                if (less(arr[j], arr[min])) min = j;
            }
            exch(arr, i, min); // Exchange arr[i] with smallest entry in arr[i+1...N).
        }
    }
}
