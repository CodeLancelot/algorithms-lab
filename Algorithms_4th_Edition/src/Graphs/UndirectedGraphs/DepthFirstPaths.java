package Graphs.UndirectedGraphs;

import Fundamentals.Stack;
import libraries.*;

import java.net.URL;

public class DepthFirstPaths {
    private boolean[] marked; // Has dfs() been called for this vertex?
    private int[] edgeTo; // last vertex on known path to this vertex
    private final int s; // source

    public DepthFirstPaths(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfs(G, s);
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }

    public static void main(String[] args) {
        try {
            URL tingCG = new URL("https://algs4.cs.princeton.edu/41graph/tinyCG.txt");
            In in = new In(tingCG);
            Graph G = new Graph(in);
            int s = Integer.parseInt(args[0]);
            DepthFirstPaths paths = new DepthFirstPaths(G, s);
            for (int v = 0; v < G.V(); v++) {
                if (paths.hasPathTo(v)) {
                    StdOut.print(s + " to " + v + ": ");
                    for (int x : paths.pathTo(v)) {
                        if (x == s) StdOut.print(x);
                        else StdOut.print("-" + x);
                    }
                    StdOut.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
