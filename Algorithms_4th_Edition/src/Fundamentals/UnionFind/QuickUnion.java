package Fundamentals.UnionFind;

import libraries.StdOut;

public class QuickUnion implements UnionFind {
    private int[] links;
    private int count;

    QuickUnion(int N) {
        count = N;
        links = new int[N];
        for (int i = 0; i < N; i++) links[i] = i;
    }

    public int find(int p) {
        while (p != links[p]) {
            p = links[p];
        }
        return p;
    }

    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot == qRoot) return;
        links[pRoot] = qRoot;
        count--;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int count() {
        return count;
    }

    void printAllComponents() {
        boolean[] printed = new boolean[links.length];
        for (int i = 0; i < printed.length; i++) {
            printed[i] = false;
        }

        for (int i = 0; i < links.length; i++) {
            if (i == links[i]) {
                StdOut.print("Component " + i + ":");
                for (int j = 0; j < links.length; j++) {
                    if (!printed[j] && find(j) == i) {
                        StdOut.print(" " + j);
                        printed[j] = true;
                    }
                }
                StdOut.println();
            }
        }
    }
}
