package Graphs;

import Fundamentals.Queue;
import Graphs.UndirectedGraphs.Graph;

public class Edge implements Comparable<Edge> {
    private final int v;
    private final int w;
    private final double weight;
    public boolean isUsed;

    public Edge(int v, int w) {
        this.v = v;
        this.w = w;
        this.weight = 0;
        isUsed = false;
    }

    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
        isUsed = false;
    }

    public double weight() {
        return weight;
    }

    public int either() {
        return v;
    }

    // returns the other vertex of the edge
    public int other(int vertex) {
        if (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new IllegalArgumentException("Illegal endpoint");
    }

    public int compareTo(Edge that) {
        return Double.compare(this.weight, that.weight);
    }

    public String toString() {
        return String.format("%d-%d %.3f", v, w, weight);
    }

    // create adjacency lists for Undirected Graph
    public static Queue<Edge>[] adjOfEdges(Graph G) {
        Queue<Edge>[] adj = (Queue<Edge>[]) new Queue[G.V()];
        for (int v = 0; v < G.V(); v++)
            adj[v] = new Queue<>();
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (v == w) {
                    Edge e = new Edge(v, w);
                    adj[v].enqueue(e);
                } else if (v < w) {
                    Edge e = new Edge(v, w);
                    adj[v].enqueue(e);
                    adj[w].enqueue(e);
                }
            }
        }
        return adj;
    }
}
