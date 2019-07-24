package Fundamentals;

import libraries.*;

public class Tools {
    static <T> void printArray(T[] arr) {
        StdOut.println();
        StdOut.print('[');
        for (int i = 0; i < arr.length; i++) {
            StdOut.print(arr[i]);
            if (i != arr.length - 1) {
                StdOut.print(' ');
            }
        }
        StdOut.println(']');
    }

    static void printArray(double[] arr, int precision) {
        StdOut.println();
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

    static <T> void printMatrix(T[][] matrix) {
        int count = matrix[0].length;
        StdOut.println();
        StdOut.println();
        for (T[] row : matrix) {
            StdOut.print('(');
            for (int col = 0; col < count; col++) {
                StdOut.print(row[col]);
                if (col < count - 1) {
                    StdOut.print(" ");
                }
            }
            StdOut.println(')');
        }
        StdOut.println();
    }

    static void printMatrix(double[][] matrix, int precision) {
        int count = matrix[0].length;
        StdOut.println();
        StdOut.println();
        for (double[] row : matrix) {
            StdOut.print('(');
            for (int col = 0; col < count; col++) {
                String formatStr = "%." + precision + 'f';
                StdOut.printf(formatStr, row[col]);
                if (col < count - 1) {
                    StdOut.print(" ");
                }
            }
            StdOut.println(')');
        }
        StdOut.println();
    }
}