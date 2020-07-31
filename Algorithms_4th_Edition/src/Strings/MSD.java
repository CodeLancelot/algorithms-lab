package Strings;

import libraries.In;
import libraries.StdOut;

import java.net.URL;

public class MSD {
    private static final int R = 256; // extended ASCII alphabet size
    private static final int CUTOFF = 7; // cutoff to insertion sort
    private static String[] aux; // auxiliary array for distribution

    private MSD() {
    }

    public static void sort(String[] a) {
        int n = a.length;
        aux = new String[n];
        sort(a, 0, n - 1, 0);
    }

    private static void sort(String[] a, int lo, int hi, int d) {
        if (hi - lo <= CUTOFF) {
            insert(a, lo, hi, d);
            return;
        }

        int[] count = new int[R + 2];
        // Compute frequency counts.
        for (int i = lo; i <= hi; i++)
            count[charAt(a[i], d) + 2]++;
        // Transform counts to start indices.
        for (int r = 0; r < R + 1; r++)
            count[r + 1] += count[r];
        // Distribute data.
        for (int i = lo; i <= hi; i++)
            aux[lo + count[charAt(a[i], d) + 1]++] = a[i];
        // Copy back.
        for (int i = lo; i <= hi; i++)
            a[i] = aux[i];
        // Recursively sort for each character value.
        for (int r = 0; r < R; r++)
            sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1);
    }

    private static int charAt(String s, int d) {
        if (d < s.length()) return s.charAt(d);
        return -1;
    }

    private static void insert(String[] a, int lo, int hi, int d) {
        for (int i = lo + 1; i <= hi; i++) {
            for (int j = i; j > lo && less(a[j], a[j - 1], d); j--)
                exch(a, j, j - 1);
        }
    }

    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // Is v less than w, starting at character d
    private static boolean less(String v, String w, int d) {
        assert v.substring(0, d).equals(w.substring(0, d));
        for (int i = d; i < Math.min(v.length(), w.length()); i++) {
            if (v.charAt(i) < w.charAt(i)) return true;
            if (v.charAt(i) > w.charAt(i)) return false;
        }
        return v.length() < w.length();
    }

    public static void sort(int[] a) {
        int n = a.length;
        int[] aux = new int[n];
        sort(a, 0, n - 1, 0, aux);
    }

    // MSD sort from a[lo] to a[hi], starting at the dth byte
    private static void sort(int[] a, int lo, int hi, int d, int[] aux) {
        if (hi - lo <= CUTOFF) {
            insertion(a, lo, hi);
            return;
        }

        final int BITS_PER_BYTE = 8;
        // each Java int is 32 bits
        final int BITS_PER_INT = 32;

        int[] count = new int[R + 1];
        int mask = R - 1;
        int shift = BITS_PER_INT - BITS_PER_BYTE * d - BITS_PER_BYTE;

        for (int i = lo; i <= hi; i++) {
            int c = (a[i] >> shift) & mask;
            count[c + 1]++;
        }

        for (int r = 0; r < R; r++)
            count[r + 1] += count[r];

        for (int i = lo; i <= hi; i++) {
            int c = (a[i] >> shift) & mask;
            aux[count[c]++] = a[i];
        }

        for (int i = lo; i <= hi; i++)
            a[i] = aux[i - lo];

        // no more bits
        if (d == 4) return;

        // recursively sort for each character
        if (count[0] > 0)
            sort(a, lo, lo + count[0] - 1, d + 1, aux);
        for (int r = 0; r < R; r++) {
            if (count[r + 1] > count[r])
                sort(a, lo + count[r], lo + count[r + 1] - 1, d + 1, aux);
        }
    }

    private static void insertion(int[] a, int lo, int hi) {
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && a[j] < a[j - 1]; j--)
                exch(a, j, j - 1);
        }
    }

    private static void exch(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static void main(String[] args) {
        try {
            URL words = new URL("https://algs4.cs.princeton.edu/51radix/shells.txt");
            In in = new In(words);
            String[] a = in.readAllStrings();

            sort(a);

            for (String str : a)
                StdOut.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
