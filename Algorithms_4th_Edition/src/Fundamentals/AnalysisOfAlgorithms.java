package Fundamentals;

import libraries.*;

public class AnalysisOfAlgorithms {
    public static void main(String[] args) {
        doublingRatio();
    }

    static void doublingRatio() {
        int N = 125;
        double prev = timeTrial(125);

        while (!StdIn.isEmpty()) {
            N += N;
            double time = timeTrial(N);
            StdOut.printf("%6d %7.1f ", N, time);
            StdOut.printf("%5.1f\n", time / prev);
            prev = time;
        }
    }

    static double timeTrial(int N) {
        int MAX = 1000000;
        int[] a = new int[N];
        for (int i = 0; i < N; i++)
            a[i] = StdRandom.uniform(-MAX, MAX);
        Stopwatch timer = new Stopwatch();
        countThreeSum(a);
        return timer.elapsedTime();
    }

    static int countThreeSum(int[] a) { // Count triples that sum to 0.
        int N = a.length;
        int cnt = 0;
        for (int i = 0; i < N; i++)
            for (int j = i + 1; j < N; j++)
                for (int k = j + 1; k < N; k++)
                    if (a[i] + a[j] + a[k] == 0)
                        cnt++;
        return cnt;
    }
}