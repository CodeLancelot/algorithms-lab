package Fundamentals.UnionFind;

import libraries.*;

public class MainTest {
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]); // N sites.
        QuickUnion quickUnion = new QuickUnion(N);
        WeightedQuickUnion weightedQuickUnion = new WeightedQuickUnion(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (quickUnion.connected(p, q)) continue; // Ignore if connected.
            quickUnion.union(p, q); // Combine components
            weightedQuickUnion.union(p, q);
            StdOut.println("A new connection created: " + p + " " + q);
        }
        StdOut.println(quickUnion.count() + " components");
        quickUnion.printAllComponents();
        weightedQuickUnion.printAllSitesPath();
    }
}
