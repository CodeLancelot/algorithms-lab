package Fundamentals;

import libraries.*;

public class BasicProgrammingModel {
    public static void main(String[] args) {
        int m = 10;
        int[] a = {1, 2, 2, 5, 1, 7, 5, 6, 1, 9};
        Tools.printIntArray(histogram(a, m));
    }

    public static int[] histogram(int[] arr, int m) {
        int[] results = new int[m];
        boolean isNeedExtraCheck = true;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] >= 0 && arr[i] < m) {
                results[arr[i]]++;
            } else {
                isNeedExtraCheck = false;
            }
        }
        if (isNeedExtraCheck) {
            int sum = 0;
            for (int i = 0; i < results.length; i++) {
                sum += results[i];
            }
            if (sum == arr.length) {
                StdOut.println("Fine");
            } else {
                StdOut.println("Illegal");
            }
        }
        return results;
    }
}