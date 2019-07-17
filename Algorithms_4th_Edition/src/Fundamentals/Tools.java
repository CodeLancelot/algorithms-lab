package Fundamentals;

import libraries.*;

public class Tools {
    public static void printArray(int[] arr) {
        StdOut.print('[');
        for (int i = 0; i < arr.length; i++) {
            StdOut.print(arr[i]);
            if (i != arr.length - 1) {
                StdOut.print(' ');
            }
        }
        StdOut.println(']');
    }

    public static void printArray(double[] arr, int precision) {
        StdOut.print('[');
        for (int i = 0; i < arr.length; i++) {
            String formatStr = "%." + precision + 'f';
            StdOut.printf(formatStr, arr[i]);
            if (i != arr.length - 1) {
                StdOut.print(' ');
            }
        }
        StdOut.println(']');
    }

    public static void printArray(String[] arr) {
        StdOut.print('[');
        for (int i = 0; i < arr.length; i++) {
            StdOut.print(arr[i]);
            if (i != arr.length - 1) {
                StdOut.print(' ');
            }
        }
        StdOut.println(']');
    }

    public static void printTwoDimensionalArray(int[][] arr) {
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

    public static void printTwoDimensionalArray(double[][] arr, int precision) {
        int m = arr.length;
        int n = arr[0].length;
        for (int i = 0; i < m; i++) {
            StdOut.print('(');
            for (int j = 0; j < n; j++) {
                String formatStr = "%." + precision + 'f';
                StdOut.printf(formatStr, arr[i][j]);
                if (j < n - 1) {
                    StdOut.print(", ");
                }
            }
            StdOut.println(')');
        }
    }

    public static void printTwoDimensionalArray(String[][] arr) {
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