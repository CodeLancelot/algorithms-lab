package Graphs.UndirectedGraphs;

import Fundamentals.Stack;
import libraries.*;

import java.net.URL;

public class Bipartite {
    private boolean[] color;       // color[v] gives vertices on one side of bipartition
    private boolean[] marked;      // marked[v] = true if v has been visited in DFS
    private int[] edgeTo;          // edgeTo[v] = last edge on path to v
    private Stack<Integer> cycle;  // odd-length cycle

    public Bipartite(Graph G) {
        color = new boolean[G.V()];
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        for (int v = 0; v < G.V() && isBipartite(); v++) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!isBipartite()) return;
            if (!marked[w]) {
                color[w] = !color[v];
                edgeTo[w] = v;
                dfs(G, w);
            } else if (color[w] == color[v]) {
                cycle = new Stack<>();
                for (int x = v; x != w; x = edgeTo[x]) cycle.push(x);
                cycle.push(w);
                cycle.push(v);
            }
        }
    }

    public boolean isBipartite() {
        return cycle == null;
    }

    public boolean color(int v) {
        if (!isBipartite())
            throw new UnsupportedOperationException("graph is not bipartite");
        return color[v];
    }

    public Iterable<Integer> oddCycle() {
        return cycle;
    }

    public static void main(String[] args) {
        try {
            URL tingG = new URL("https://algs4.cs.princeton.edu/41graph/tinyG.txt");
            In in = new In(tingG);
            Graph G = new Graph(in);
            Bipartite B = new Bipartite(G);
            if (B.isBipartite()) {
                StdOut.println("Graph is bipartite");
                for (int v = 0; v < G.V(); v++) {
                    StdOut.println(v + ": " + B.color(v));
                }
            } else {
                StdOut.print("Graph has an odd-length cycle: ");
                for (int x : B.oddCycle()) {
                    StdOut.print(x + " ");
                }
                StdOut.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
