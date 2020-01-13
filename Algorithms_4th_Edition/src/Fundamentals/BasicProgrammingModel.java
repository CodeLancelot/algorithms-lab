package Fundamentals;

import libraries.*;

public class BasicProgrammingModel {
    public static void main(String[] args) {
        shuffleTest();
    }

    public static void shuffleTest() {
        int M = 10, N = 500;
        int[] a = new int[M];
        int[][] counts = new int[M][M];

        //N shuffle
        for (int round = 0; round < N; round++) {
            initArray(a);
            shuffle(a);
            for (int i = 0; i < M; i++) {
                counts[i][a[i]]++;
            }
        }
        Tools.printArray(a);
        Tools.printMatrix(counts);
    }

    private static void initArray(int[] a) {
        for (int i = 0, len = a.length; i < len; i++) {
            a[i] = i;
        }
    }

    public static void shuffle(int[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            // Exchange a[i] with random element in a[i..N-1]
            int r = i + StdRandom.uniform(N - i);
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public static void badShuffling(int[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            int r = StdRandom.uniform(N);
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
}