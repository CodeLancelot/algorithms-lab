package Strings;

import libraries.In;
import libraries.StdOut;

import java.net.URL;

public class LSD {
    private LSD() {
    }

    // Rearranges the array of w-character strings in ascending order.
    public static void sort(String[] a, int w) {
        int n = a.length;
        // extend ASCII alphabet size
        int R = 256;
        String[] aux = new String[n];
        for (int d = w - 1; d >= 0; d--) {
            int[] count = new int[R + 1];
            // calculate frequency
            for (int i = 0; i < n; i++)
                count[a[i].charAt(d) + 1]++;
            // calculate start index
            for (int r = 0; r < R; r++)
                count[r + 1] += count[r];
            // distribute data
            for (int i = 0; i < n; i++)
                aux[count[a[i].charAt(d)]++] = a[i];
            // copy back
            for (int i = 0; i < n; i++)
                a[i] = aux[i];
        }
    }

    // Rearranges the array of 32-bit integers in ascending order.
    public static void sort(int[] a) {
        final int BITS_PER_BYTE = 8;
        // each int is 32 bits
        final int BITS = 32;
        // each bytes is between 0 and 255
        final int R = 1 << BITS_PER_BYTE;
        // MASK = 0xFF
        final int MASK = R - 1;
        // each int is 4 bytes
        final int w = BITS / BITS_PER_BYTE;

        int n = a.length;
        int[] aux = new int[n];

        for (int d = 0; d < w; d++) {
            int[] count = new int[R + 1];
            for (int i = 0; i < n; i++) {
                int c = (a[i] >> BITS_PER_BYTE * d) & MASK;
                count[c + 1]++;
            }

            for (int r = 0; r < R; r++)
                count[r + 1] += count[r];

            // for most significant byte, 0x80-0xFF comes before 0x00-0x7F
            if (d == w - 1) {
                int shift1 = count[R] - count[R / 2];
                int shift2 = count[R / 2];
                for (int r = 0; r < R / 2; r++)
                    count[r] += shift1;
                for (int r = R / 2; r < R; r++)
                    count[r] -= shift2;
            }

            for (int i = 0; i < n; i++) {
                int c = (a[i] >> BITS_PER_BYTE * d) & MASK;
                aux[count[c]++] = a[i];
            }

            for (int i = 0; i < n; i++)
                a[i] = aux[i];
        }
    }

    public static void main(String[] args) {
        try {
            URL words = new URL("https://algs4.cs.princeton.edu/51radix/words3.txt");
            In in = new In(words);
            String[] a = in.readAllStrings();
            int n = a.length;

            // check that strings have fixed length
            int w = a[0].length();
            for (String str : a)
                assert str.length() == w : "Strings must have fixed length";

            sort(a, w);

            for (String str : a)
                StdOut.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
