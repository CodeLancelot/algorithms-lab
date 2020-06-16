package Graphs.EdgeWeightedGraphs;

import Fundamentals.Queue;
import Graphs.Edge;
import Sorting.IndexMinPQ;
import libraries.In;
import libraries.StdOut;

import java.net.URL;

// Eager version of Prim’s MST algorithm
public class PrimMST {
    private boolean[] marked;
    private Edge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Edge> pq;


    public PrimMST(EdgeWeightedGraph G) {
        marked = new boolean[G.V()];
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        pq = new IndexMinPQ<>(G.V());
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) prim(G, v);
    }

    private void prim(EdgeWeightedGraph G, int s) {
        visit(G, s);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            visit(G, v);
        }
    }

    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge edge : G.adj(v)) {
            int w = edge.other(v);
            double weight = edge.weight();
            if (!marked[w]) {
                if (weight < distTo[w]) {
                    edgeTo[w] = edge;
                    distTo[w] = edge.weight();
                    if (pq.contains(w)) pq.changeKey(w, edge);
                    else pq.insert(w, edge);
                }
            }
        }
    }

    public Iterable<Edge> edges() {
        Queue<Edge> mst = new Queue<>();
        for (Edge edge : edgeTo) {
            if (edge != null) mst.enqueue(edge);
        }
        return mst;
    }

    public double weight() {
        double weight = 0.0;
        for (Edge edge : edges())
            weight += edge.weight();
        return weight;
    }

    public static void main(String[] args) {
        try {
            URL tingEWG = new URL("https://algs4.cs.princeton.edu/43mst/tinyEWG.txt");
            In in = new In(tingEWG);
            EdgeWeightedGraph G = new EdgeWeightedGraph(in);
            PrimMST mst = new PrimMST(G);
            for (Edge e : mst.edges())
                StdOut.println(e);
            StdOut.printf("%.5f\n", mst.weight());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
