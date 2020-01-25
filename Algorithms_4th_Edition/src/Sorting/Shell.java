package Sorting;

public class Shell extends Sort {
    private Shell() {
    }

    public static void sort(Comparable[] arr) {
        int N = arr.length;
        int h = 1;
        while (h < N / 3) h = h * 3 + 1;
        while (h >= 1) {
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && less(arr[j], arr[j - h]); j -= h)
                    exch(arr, j, j - h);
            }
            h /= 3;
        }
    }
}
