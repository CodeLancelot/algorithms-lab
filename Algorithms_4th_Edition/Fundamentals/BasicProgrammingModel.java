package Fundamentals;

import java.util.ArrayList;
import java.util.List;
import libraries.*;

public class BasicProgrammingModel {
    public static void main(String[] args) {
        List<String> tableData = new ArrayList<String>();
        while (StdIn.hasNextLine()) {
            tableData.add(StdIn.readLine());
        }
        printTable(tableData);
    }

    public static void printTable(List<String> tableData) {
        StdOut.println("[Player Name] [Total Points] [Count] [Average Score]");
        tableData.forEach(rowStr -> {
            String[] row = rowStr.split(" ");
            String name = row[0];
            int points = Integer.parseInt(row[1]);
            int count = Integer.parseInt(row[2]);
            double score = (double)points/(double)count;
            StdOut.printf("[%-11s] [%12d] [%5d] [%13.3f]\n", name, points, count, score);
        });
    }
}