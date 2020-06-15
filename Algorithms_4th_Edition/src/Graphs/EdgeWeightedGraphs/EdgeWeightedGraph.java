package Graphs.EdgeWeightedGraphs;

import Fundamentals.Bag;
import Graphs.Edge;
import libraries.In;
import libraries.StdOut;

import java.util.NoSuchElementException;

public class EdgeWeightedGraph {
    private final int V;
    private int E;
    private Bag<Edge>[] adj;

    public EdgeWeightedGraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Edge>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<>();
    }

    public EdgeWeightedGraph(In in) {
        if (in == null) throw new IllegalArgumentException("argument is null");
        try {
            V = in.readInt();
            adj = (Bag<Edge>[]) new Bag[V];
            for (int v = 0; v < V; v++)
                adj[v] = new Bag<>();

            int E = in.readInt();
            if (E < 0) throw new IllegalArgumentException("Number of edges must be non-negative");
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                validateVertex(v);
                validateVertex(w);
                double weight = in.readDouble();
                Edge e = new Edge(v, w, weight);
                addEdge(e);
            }
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in EdgeWeightedGraph constructor", e);
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public void addEdge(Edge edge) {
        int v = edge.either();
        int w = edge.other(v);
        if (v == w) adj[v].add(edge);
        else {
            adj[v].add(edge);
            adj[w].add(edge);
        }
        E++;
    }

    public void addEdge(int v, int w, double weight) {
        Edge edge = new Edge(v, w, weight);
        addEdge(edge);
    }

    public Iterable<Edge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    public int degree(int v) {
        int degree = 0;
        for (Edge edge : adj(v)) {
            int w = edge.other(v);
            if (v != w) degree++;
            else degree = degree + 2;
        }
        return degree;
    }

    public Iterable<Edge> edges() {
        Bag<Edge> edges = new Bag<>();
        for (int v = 0; v < V; v++) {
            for (Edge edge : adj[v]) {
                int w = edge.other(v);
                if (v <= w) edges.add(edge);
            }
        }
        return edges;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + "\n");
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (Edge e : adj[v]) {
                s.append(e + "  ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        EdgeWeightedGraph G = new EdgeWeightedGraph(6);
        G.addEdge(0, 2, 1);
        G.addEdge(1, 2, 1);
        G.addEdge(1, 5, 1);
        G.addEdge(2, 2, 1);
        G.addEdge(2, 3, 1);
        G.addEdge(2, 4, 1);
        G.addEdge(2, 5, 1);
        G.addEdge(3, 4, 1);
        StdOut.println(G);
    }
}
