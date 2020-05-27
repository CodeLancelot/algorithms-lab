package Graphs;

import Fundamentals.Queue;
import Graphs.UndirectedGraphs.Graph;
import Graphs.DirectedGraphs.Digraph;

public class Edge {
    private final int v;
    private final int w;
    public boolean isUsed;

    public Edge(int v, int w) {
        this.v = v;
        this.w = w;
        isUsed = false;
    }

    // returns the other vertex of the edge
    public int other(int vertex) {
        if (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new IllegalArgumentException("Illegal endpoint");
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

    // create adjacency lists for Directed Graph
    public static Queue<Edge>[] adjOfEdges(Digraph G) {
        Queue<Edge>[] adj = (Queue<Edge>[]) new Queue[G.V()];
        for (int v = 0; v < G.V(); v++)
            adj[v] = new Queue<>();
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                Edge e = new Edge(v, w);
                adj[v].enqueue(e);
            }
        }
        return adj;
    }
}