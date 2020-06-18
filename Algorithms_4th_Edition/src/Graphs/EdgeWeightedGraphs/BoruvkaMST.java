package Graphs.EdgeWeightedGraphs;

import Fundamentals.Queue;
import Fundamentals.UnionFind.WeightedQuickUnion;
import Graphs.Edge;
import libraries.In;
import libraries.StdOut;

import java.net.URL;

public class BoruvkaMST {
    private Queue<Edge> mst = new Queue<>();

    public BoruvkaMST(EdgeWeightedGraph G) {
        WeightedQuickUnion unionFind = new WeightedQuickUnion(G.V());
        // repeat at most log V times or until we have V-1 edges
        for (int times = 1; times < G.V() && mst.size() < G.V() - 1; times *= 2) {
            // find the minimum weight edge for each tree in the forest
            Edge[] cutOfTrees = new Edge[G.V()];
            for (Edge edge : G.edges()) {
                int v = edge.either(), w = edge.other(v);
                int i = unionFind.find(v), j = unionFind.find(w);
                // two vertexes not on the same tree yet
                if (i != j) {
                    if (cutOfTrees[i] == null || edge.compareTo(cutOfTrees[i]) < 0) cutOfTrees[i] = edge;
                    if (cutOfTrees[j] == null || edge.compareTo(cutOfTrees[j]) < 0) cutOfTrees[j] = edge;
                }
            }

            // add cut edges to the forest
            for (Edge cut : cutOfTrees) {
                if (cut != null) {
                    int v = cut.either(), w = cut.other(v);
                    if (!unionFind.connected(v, w)) {
                        unionFind.union(v, w);
                        mst.enqueue(cut);
                    }
                }
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
            BoruvkaMST mst = new BoruvkaMST(G);
            for (Edge e : mst.edges())
                StdOut.println(e);
            StdOut.printf("%.5f\n", mst.weight());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
