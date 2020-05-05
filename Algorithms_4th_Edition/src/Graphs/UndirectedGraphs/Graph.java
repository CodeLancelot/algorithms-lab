package Graphs.UndirectedGraphs;

import libraries.*;
import Fundamentals.Bag;

import java.net.URL;

public class Graph {
    private final int V; // number of vertices
    private int E; // number of edges
    private Bag<Integer>[] adj; // adjacency lists

    public Graph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<>();
    }

    public Graph(In in) {
        this(in.readInt()); // Read V and construct this graph.
        int E = in.readInt(); // Read E.
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            addEdge(v, w);
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(int v, int w) {
        if (v == w) adj[v].add(v);
        else {
            adj[v].add(w); // Add w to v’s list.
            adj[w].add(v); // Add v to w’s list.
        }
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public int degree(int v) {
        int degree = 0;
        for (int w : adj(v)) {
            if (v != w) degree++;
            else degree = degree + 2;
        }
        return degree;
    }

    public int maxDegree() {
        int max = 0;
        for (int v = 0; v < V(); v++)
            if (degree(v) > max)
                max = degree(v);
        return max;
    }

    public int avgDegree() {
        return 2 * E() / V();
    }

    public String toString() {
        String s = V + " vertices, " + E + " edges\n";
        for (int v = 0; v < V; v++) {
            s += v + ": ";
            for (int w : this.adj(v))
                s += w + " ";
            s += "\n";
        }
        return s;
    }

    public static void main(String[] args) {
        try {
            URL tingG = new URL("https://algs4.cs.princeton.edu/41graph/tinyG.txt");
            In in = new In(tingG);
            Graph G = new Graph(in);
            StdOut.println(G);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
