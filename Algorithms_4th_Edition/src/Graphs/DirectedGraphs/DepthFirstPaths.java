package Graphs.DirectedGraphs;

import Fundamentals.Stack;
import libraries.In;
import libraries.StdOut;

import java.net.URL;

public class DepthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private final int s;

    public DepthFirstPaths(Digraph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfs(G, s);
    }

    private void dfs(Digraph G, int v) {
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
            URL tingDG = new URL("https://algs4.cs.princeton.edu/42digraph/tinyDG.txt");
            In in = new In(tingDG);
            Digraph G = new Digraph(in);
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
