package Graphs.EdgeWeightedDigraphs;

import Fundamentals.Stack;
import Graphs.DirectedEdge;
import Sorting.IndexMinPQ;
import libraries.In;
import libraries.StdOut;

import java.net.URL;

// Dijkstra’s algorithm for computing shortest-paths
public class DijkstraSP {
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq;

    public DijkstraSP(EdgeWeightedDigraph G, int s) {
        preconditionsCheck(G);
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        pq = new IndexMinPQ<>(G.V());
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) relax(G, pq.delMin());
    }

    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge edge : G.adj(v)) {
            int w = edge.to();
            double weight = edge.weight();
            if (distTo[w] > distTo[v] + weight) {
                distTo[w] = distTo[v] + weight;
                edgeTo[w] = edge;
                if (pq.contains(w)) pq.changeKey(w, distTo[w]);
                else pq.insert(w, distTo[w]);
            }
        }
    }

    private void preconditionsCheck(EdgeWeightedDigraph G) {
        for (DirectedEdge edge : G.edges()) {
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

    public Iterable<DirectedEdge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge edge = edgeTo[v]; edge != null; edge = edgeTo[edge.from()])
            path.push(edge);
        return path;
    }

    public static void main(String[] args) {
        try {
            URL tingDG = new URL("https://algs4.cs.princeton.edu/44sp/tinyEWD.txt");
            In in = new In(tingDG);
            EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
            int s = Integer.parseInt(args[0]);
            DijkstraSP sp = new DijkstraSP(G, s);
            for (int v = 0; v < G.V(); v++) {
                if (sp.hasPathTo(v)) {
                    StdOut.printf("%d to %d (%.2f)  ", s, v, sp.distTo(v));
                    for (DirectedEdge edge : sp.pathTo(v)) {
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
