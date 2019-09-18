package Fundamentals.UnionFind;

import libraries.*;

public class MainTest {
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]); // N sites.
        QuickFind quickFind = new QuickFind(N); // Initialize N components.
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (quickFind.connected(p, q)) continue; // Ignore if connected.
            quickFind.union(p, q); // Combine components
            StdOut.println("A new connection created: " + p + " " + q); // and print connection.
        }
        StdOut.println(quickFind.count() + " components");
        quickFind.printAllComponents();
    }
}
