package Fundamentals;

import java.awt.Point;

import libraries.*;

public class BasicProgrammingModel {
    public static void main(String[] args) {
        diceSimulation(Integer.parseInt(args[0]));
    }

    public static void diceSimulation (int N) {
        //N is at least at ten million level, I met the request when first trying 60000000
        int SIDES = 6;
        double[] dist = new double[2 * SIDES + 1];
        for (int i = 1; i <= SIDES; i++) {
            for (int j = 1; j <= SIDES; j++)
                dist[i + j]++;
        }
        for (int k = 2; k <= 2 * SIDES; k++)
            dist[k] /= 36.0;
        Tools.printArray(dist, 3);


        double[] test = new double[2 * SIDES + 1];
        for (int i = 0; i < N; i++) {
            int dice1 = StdRandom.uniform(SIDES) + 1;
            int dice2 = StdRandom.uniform(SIDES) + 1;
            test[dice1 + dice2]++;
        }
        for (int k = 2; k <= 2 * SIDES; k++)
            test[k] /= N;
        Tools.printArray(test, 3);
    }
}