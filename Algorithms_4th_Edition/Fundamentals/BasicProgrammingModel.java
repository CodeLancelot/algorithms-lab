package Fundamentals;

import libraries.*;

public class BasicProgrammingModel {
    public static void main(String[] args) {
        int m = 2, n = 3;
        int[][] a = {{1,2,3},{1,2,3}}, b = new int[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                b[j][i] = a[i][j];
            }
        }
        printDArray(b);
    }

    public static void printDArray (int[][] arr) {
        int m = arr.length;
        int n = arr[0].length;
        for (int i = 0; i < m; i++) {
            StdOut.print('(');
            for (int j = 0; j < n; j++) {
                StdOut.print(arr[i][j]);
                if (j < n-1) {
                    StdOut.print(", ");
                }
            }
            StdOut.println(')');
        }
    }
}