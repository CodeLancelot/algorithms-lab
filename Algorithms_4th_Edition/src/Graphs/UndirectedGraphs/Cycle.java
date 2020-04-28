package Graphs.UndirectedGraphs;

import Fundamentals.Stack;
import libraries.*;

import java.net.URL;

public class Cycle {
    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle;

    public Cycle(Graph G) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        if (hasSelfLoop(G)) return;
        if (hasParallelEdges(G)) return;
        for (int v = 0; v < G.V(); v++)
            if (!marked[v] && !hasCycle()) dfs(G, -1, v);
    }

    // does this graph have a self loop?
    // side effect: initialize cycle to be self loop
    private boolean hasSelfLoop(Graph G) {
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

    private boolean hasParallelEdges(Graph G) {
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (marked[w]) {
                    cycle = new Stack<>();
                    cycle.push(v);
                    cycle.push(w);
                    cycle.push(v);
                    return true;
                } else marked[w] = true;
            }
            for (int w : G.adj(v)) marked[w] = false;
        }
        return false;
    }

    private void dfs(Graph G, int u, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            // short circuit if cycle already found
            if (hasCycle()) return;
            if (marked[w]) {
                if (w != u) {
                    cycle = new Stack<>();
                    cycle.push(w);
                    for (int x = v; x != w; x = edgeTo[x]) cycle.push(x);
                    cycle.push(w);
                }
            } else {
                edgeTo[w] = v;
                dfs(G, v, w);
            }
        }
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    // returns a cycle in the graph
    public Iterable<Integer> cycle() {
        return cycle;
    }

    public static void main(String[] args) {
        try {
            URL tingG = new URL("https://algs4.cs.princeton.edu/41graph/tinyG.txt");
            In in = new In(tingG);
            Graph G = new Graph(in);
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
}
