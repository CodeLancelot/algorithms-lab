package Fundamentals.UnionFind;

import libraries.*;

public class QuickFind implements UnionFind {
    private int[] idArr; // access to component id (site indexed)
    private int count; // number of components

    QuickFind(int N) {
        count = N;
        idArr = new int[N];
        for (int i = 0; i < N; i++) idArr[i] = i;
    }

    public int find(int p) {
        return idArr[p];
    }

    public void union(int p, int q) {
        int pID = find(p);
        int qID = find(q);
        if (pID == qID) return;
        for (int i = 0; i < idArr.length; i++) {
            if (idArr[i] == pID) idArr[i] = qID;
        }
        count--;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int count() {
        return count;
    }

    public void printAllComponents() {
        boolean[] printed = new boolean[idArr.length];
        for (int i = 0; i < printed.length; i++) {
            printed[i] = false;
        }

        for (int i = 0; i < idArr.length; i++) {
            if (!printed[i]) {
                int component = idArr[i];
                StdOut.print("Component " + component + ":");
                for (int j = i; j < idArr.length; j++) {
                    if (idArr[j] == component) {
                        StdOut.print(" " + j);
                        printed[j] = true;
                    }
                }
                StdOut.println();
            }
        }
    }
}
