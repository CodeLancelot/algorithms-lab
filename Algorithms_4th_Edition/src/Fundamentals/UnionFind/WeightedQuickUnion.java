package Fundamentals.UnionFind;

import libraries.StdOut;

public class WeightedQuickUnion implements UnionFind {
    private int[] links;
    private int[] sizes;
    private int count;

    public WeightedQuickUnion(int N) {
        count = N;
        links = new int[N];
        sizes = new int[N];
        for (int i = 0; i < N; i++) {
            links[i] = i;
            sizes[i] = 1;
        }
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
        if (sizes[pRoot] < sizes[qRoot]) {
            links[pRoot] = qRoot;
            sizes[qRoot] += sizes[pRoot];
        } else {
            links[qRoot] = pRoot;
            sizes[pRoot] += sizes[qRoot];
        }
        count--;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int count() {
        return count;
    }

    void printAllSitesPath() {
        for (int i = 0; i < links.length; i++) {
            int j = i;
            while (j != links[j]) {
                StdOut.print(j + " ");
                j = links[j];
            }
            StdOut.print(j + " ");
            StdOut.print("( Component " + j + " )");
            StdOut.println();
        }
    }
}
