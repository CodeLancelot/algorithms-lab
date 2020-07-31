package Strings;

import libraries.In;
import libraries.StdOut;
import libraries.StdRandom;

import java.net.URL;

// 3-way string quick sort
public class ThreeWayQuick {
    private static final int CUTOFF = 7; // cutoff to insertion sort

    private ThreeWayQuick() {
    }

    public static void sort(String[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1, 0);
    }

    private static void sort(String[] a, int lo, int hi, int d) {
        if (hi - lo <= CUTOFF) {
            insertion(a, lo, hi, d);
            return;
        }

        int lt = lo, gt = hi;
        int target = chartAt(a[lo], d);
        int i = lo + 1;
        while (i <= gt) {
            int current = chartAt(a[i], d);
            if (current < target)
                exch(a, lt++, i++);
            else if (current > target)
                exch(a, i, gt--);
            else i++;
        }
        sort(a, lo, lt - 1, d);
        if (target >= 0) sort(a, lt, gt, d + 1);
        sort(a, gt + 1, hi, d);
    }

    private static int chartAt(String s, int d) {
        assert d >= 0 && d <= s.length();
        if (d == s.length()) return -1;
        return s.charAt(d);
    }

    private static void insertion(String[] a, int lo, int hi, int d) {
        for (int i = lo + 1; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j - 1], d); j--)
                exch(a, j, j - 1);
    }

    private static void exch(String[] a, int i, int j) {
        String temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static boolean less(String v, String w, int d) {
        assert v.substring(0, d).equals(w.substring(0, d));
        for (int i = d; i < Math.min(v.length(), w.length()); i++) {
            if (v.charAt(i) < w.charAt(i)) return true;
            if (v.charAt(i) > w.charAt(i)) return false;
        }
        return v.length() < w.length();
    }

    public static void main(String[] args) {
        try {
            URL words = new URL("https://algs4.cs.princeton.edu/51radix/shells.txt");
            In in = new In(words);
            String[] a = in.readAllStrings();
            sort(a);
            for (String str : a) StdOut.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
