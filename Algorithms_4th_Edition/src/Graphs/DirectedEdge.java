package Graphs;

import Fundamentals.Queue;
import Graphs.DirectedGraphs.Digraph;

public class DirectedEdge {
    private final int v;
    private final int w;
    private final double weight;
    public boolean isUsed;

    public DirectedEdge(int v, int w) {
        this.v = v;
        this.w = w;
        this.weight = 0;
        isUsed = false;
    }

    public DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
        isUsed = false;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public double weight() {
        return weight;
    }

    public String toString() {
        return v + "->" + w + " " + String.format("%5.2f", weight);
    }

    // create adjacency lists for Directed Graph
    public static Queue<DirectedEdge>[] adjOfEdges(Digraph G) {
        Queue<DirectedEdge>[] adj = (Queue<DirectedEdge>[]) new Queue[G.V()];
        for (int v = 0; v < G.V(); v++)
            adj[v] = new Queue<>();
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                DirectedEdge e = new DirectedEdge(v, w);
                adj[v].enqueue(e);
            }
        }
        return adj;
    }
}
