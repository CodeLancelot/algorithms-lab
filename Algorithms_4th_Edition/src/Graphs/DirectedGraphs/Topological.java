package Graphs.DirectedGraphs;

import libraries.StdOut;

import java.net.URL;

public class Topological {
    private Iterable<Integer> order; // topological order
    private int[] rank; // rank[v] = rank of vertex v in order

    // A digraph has a topological order if and only if it is a DAG.
    public Topological(Digraph G) {
        Cycle cycleFinder = new Cycle(G);
        if (!cycleFinder.hasCycle()) {
            DepthFirstOrder dfo = new DepthFirstOrder(G);
            order = dfo.reversePost();
            rank = new int[G.V()];
            int i = 0;
            for (int v : order) rank[v] = i++;
        }
    }

    public Iterable<Integer> order() {
        return order;
    }

    public boolean hasOrder() {
        return order != null;
    }

    private void validateVertex(int v) {
        int V = rank.length;
        if (v < 0 || v >= V) throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public int rank(int v) {
        validateVertex(v);
        if (hasOrder()) return rank[v];
        else return -1;
    }

    public static void main(String[] args) {
        try {
            SymbolDigraph sd = new SymbolDigraph(new URL("https://algs4.cs.princeton.edu/42digraph/jobs.txt"), "/");
            Topological topological = new Topological(sd.digraph());
            for (int v : topological.order()) StdOut.println(sd.nameOf(v));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
