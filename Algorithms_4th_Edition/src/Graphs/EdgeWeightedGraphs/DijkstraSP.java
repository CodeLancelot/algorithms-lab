package Graphs.EdgeWeightedGraphs;

import Fundamentals.Stack;
import Graphs.Edge;
import Sorting.IndexMinPQ;
import libraries.In;
import libraries.StdOut;

import java.net.URL;

public class DijkstraSP {
    private Edge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq;

    public DijkstraSP(EdgeWeightedGraph G, int s) {
        preconditionsCheck(G);
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        pq = new IndexMinPQ<>(G.V());
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) relax(G, pq.delMin());
    }

    private void relax(EdgeWeightedGraph G, int v) {
        for (Edge edge : G.adj(v)) {
            int w = edge.other(v);
            double weight = edge.weight();
            if (distTo[w] > distTo[v] + weight) {
                distTo[w] = distTo[v] + weight;
                edgeTo[w] = edge;
                if (pq.contains(w)) pq.changeKey(w, distTo[w]);
                else pq.insert(w, distTo[w]);
            }
        }
    }

    private void preconditionsCheck(EdgeWeightedGraph G) {
        for (Edge edge : G.edges()) {
            if (edge.weight() < 0)
                throw new IllegalArgumentException("edge " + edge + " has negative weight");
        }
    }

    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<Edge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Edge> path = new Stack<>();
        int x = v;
        for (Edge edge = edgeTo[x]; edge != null; edge = edgeTo[x]) {
            path.push(edge);
            x= edge.other(x);
        }
        return path;
    }

    public static void main(String[] args) {
        try {
            URL tingDG = new URL("https://algs4.cs.princeton.edu/43mst/tinyEWG.txt");
            In in = new In(tingDG);
            EdgeWeightedGraph G = new EdgeWeightedGraph(in);
            int s = Integer.parseInt(args[0]);
            DijkstraSP sp = new DijkstraSP(G, s);
            for (int v = 0; v < G.V(); v++) {
                if (sp.hasPathTo(v)) {
                    StdOut.printf("%d to %d (%.2f)  ", s, v, sp.distTo(v));
                    for (Edge edge : sp.pathTo(v)) {
                        StdOut.print(edge + "   ");
                    }
                    StdOut.println();
                } else {
                    StdOut.printf("%d to %d         no path\n", s, v);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}