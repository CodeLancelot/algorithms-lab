package Fundamentals;

import libraries.*;

public class Tools {
    public static void printIntArray(int[] arr) {
        StdOut.print('[');
        for (int i = 0; i < arr.length; i++) {
            StdOut.print(arr[i]);
            if (i != arr.length - 1) {
                StdOut.print(' ');
            }
        }
        StdOut.print(']');
    }

    public static void printTwoDimensionalIntArray(int[][] arr) {
        int m = arr.length;
        int n = arr[0].length;
        for (int i = 0; i < m; i++) {
            StdOut.print('(');
            for (int j = 0; j < n; j++) {
                StdOut.print(arr[i][j]);
                if (j < n - 1) {
                    StdOut.print(", ");
                }
            }
            StdOut.println(')');
        }
    }
}