package Graphs.EdgeWeightedGraphs;

import Fundamentals.Queue;
import Fundamentals.UnionFind.WeightedQuickUnion;
import Graphs.Edge;
import Sorting.MinPQ;
import libraries.In;
import libraries.StdOut;

import java.net.URL;

// Kruskal’s algorithm for computing minimum spanning tree
public class KruskalMST {
    private Queue<Edge> mst;

    public KruskalMST(EdgeWeightedGraph G) {
        mst = new Queue<>();
        MinPQ<Edge> pq = new MinPQ<>(G.E());
        for (Edge edge : G.edges())
            pq.insert(edge);
        WeightedQuickUnion unionFind = new WeightedQuickUnion(G.V());
        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge edge = pq.delMin();
            int v = edge.either();
            int w = edge.other(v);
            if (!unionFind.connected(v, w)) {
                unionFind.union(v, w);
                mst.enqueue(edge);
            }
        }
    }

    public Iterable<Edge> edges() {
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
            KruskalMST mst = new KruskalMST(G);
            for (Edge e : mst.edges())
                StdOut.println(e);
            StdOut.printf("%.5f\n", mst.weight());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
