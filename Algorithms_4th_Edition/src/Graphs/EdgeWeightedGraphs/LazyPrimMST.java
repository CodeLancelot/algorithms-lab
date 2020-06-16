package Graphs.EdgeWeightedGraphs;

import Fundamentals.Queue;
import Graphs.Edge;
import Sorting.MinPQ;
import libraries.In;
import libraries.StdOut;

import java.net.URL;

// Lazy version of Prim’s MST algorithm
public class LazyPrimMST {
    private boolean[] marked;
    private Queue<Edge> mst;
    private MinPQ<Edge> pq;

    public LazyPrimMST(EdgeWeightedGraph G) {
        marked = new boolean[G.V()];
        mst = new Queue<>();
        pq = new MinPQ<>();
        // run Prim from all vertices to get a minimum spanning forest
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) prim(G, v);
    }

    private void prim(EdgeWeightedGraph G, int s) {
        visit(G, s);
        while (!pq.isEmpty()) {
            Edge edge = pq.delMin();
            int v = edge.either();
            int w = edge.other(v);
            if (marked[v] && marked[w]) continue; // Skip if ineligible.
            mst.enqueue(edge);
            if (!marked[v]) visit(G, v);
            else visit(G, w);
        }
    }

    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge edge : G.adj(v))
            if (!marked[edge.other(v)]) pq.insert(edge);
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        double weight = 0.0;
        for (Edge e : edges()) weight += e.weight();
        return weight;
    }

    public static void main(String[] args) {
        try {
            URL tingEWG = new URL("https://algs4.cs.princeton.edu/43mst/tinyEWG.txt");
            In in = new In(tingEWG);
            EdgeWeightedGraph G = new EdgeWeightedGraph(in);
            LazyPrimMST mst = new LazyPrimMST(G);
            for (Edge e : mst.edges())
                StdOut.println(e);
            StdOut.printf("%.5f\n", mst.weight());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
