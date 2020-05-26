package Graphs.DirectedGraphs;

import Fundamentals.Stack;
import libraries.In;
import libraries.StdOut;

import java.net.URL;

public class Cycle {
    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle;
    private boolean[] onStack;

    public Cycle(Digraph G) {
        marked = new boolean[G.V()];
        onStack = new boolean[G.V()];
        edgeTo = new int[G.V()];
        if (hasSelfLoop(G)) return;
        for (int v = 0; v < G.V(); v++)
            if (!marked[v] && cycle == null) dfs(G, v);
    }

    private void dfs(Digraph G, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (hasCycle()) return;
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            } else if (onStack[w]) {
                cycle = new Stack<>();
                cycle.push(w);
                for (int x = v; x != w; x = edgeTo[x]) cycle.push(x);
                cycle.push(w);
            }
        }
        onStack[v] = false;
    }

    private boolean hasSelfLoop(Digraph G) {
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (w == v) {
                    cycle = new Stack<>();
                    cycle.push(v);
                    cycle.push(v);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    // returns a cycle in the graph
    public Iterable<Integer> cycle() {
        return cycle;
    }

    private static void unitTest(String url) {
        try {
            URL tingDG = new URL(url);
            In in = new In(tingDG);
            Digraph G = new Digraph(in);
            Cycle C = new Cycle(G);
            if (C.hasCycle()) {
                for (int v : C.cycle()) {
                    StdOut.print(v + " ");
                }
                StdOut.println();
            } else {
                StdOut.println("Graph is acyclic");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        unitTest("https://algs4.cs.princeton.edu/42digraph/tinyDG.txt");
        unitTest("https://algs4.cs.princeton.edu/42digraph/tinyDAG.txt");
    }
}
