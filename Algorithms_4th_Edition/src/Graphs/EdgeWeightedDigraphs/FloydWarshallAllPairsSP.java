package Graphs.EdgeWeightedDigraphs;

import Fundamentals.Stack;
import Graphs.DirectedEdge;
import libraries.In;
import libraries.StdOut;

import java.net.URL;

// Floyd-Warshall algorithm for computing all-pairs shortest-paths
public class FloydWarshallAllPairsSP {
    private boolean hasNegativeCycle;  // is there a negative cycle?
    private double[][] distTo;         // distTo[v][w] = length of shortest v->w path
    private DirectedEdge[][] edgeTo;   // edgeTo[v][w] = last edge on shortest v->w path

    public FloydWarshallAllPairsSP(AdjMatrixEdgeWeightedDigraph G) {
        int V = G.V();
        distTo = new double[V][V];
        edgeTo = new DirectedEdge[V][V];

        // initialize distances to infinity
        for (int v = 0; v < V; v++) {
            for (int w = 0; w < V; w++) {
                distTo[v][w] = Double.POSITIVE_INFINITY;
            }
        }

        // initialize distances using edge-weighted digraph's
        for (int v = 0; v < G.V(); v++) {
            for (DirectedEdge e : G.adj(v)) {
                distTo[e.from()][e.to()] = e.weight();
                edgeTo[e.from()][e.to()] = e;
            }
            // in case of self-loops
            if (distTo[v][v] >= 0.0) {
                distTo[v][v] = 0.0;
                edgeTo[v][v] = null;
            }
        }

        // Floyd-Warshall updates
        for (int i = 0; i < V; i++) {
            // compute shortest paths using only 0, 1, ..., i as intermediate vertices
            for (int v = 0; v < V; v++) {
                if (edgeTo[v][i] == null) continue;  // optimization
                for (int w = 0; w < V; w++) {
                    if (distTo[v][w] > distTo[v][i] + distTo[i][w]) {
                        distTo[v][w] = distTo[v][i] + distTo[i][w];
                        edgeTo[v][w] = edgeTo[i][w];
                    }
                }
                // check for negative cycle
                if (distTo[v][v] < 0.0) {
                    hasNegativeCycle = true;
                    return;
                }
            }
        }
    }

    public boolean hasNegativeCycle() {
        return hasNegativeCycle;
    }

    public Iterable<DirectedEdge> negativeCycle() {
        for (int v = 0; v < distTo.length; v++) {
            // negative cycle in v's predecessor graph
            if (distTo[v][v] < 0.0) {
                int V = edgeTo.length;
                EdgeWeightedDigraph spt = new EdgeWeightedDigraph(V);
                for (int w = 0; w < V; w++)
                    if (edgeTo[v][w] != null)
                        spt.addEdge(edgeTo[v][w]);
                Cycle finder = new Cycle(spt);
                assert finder.hasCycle();
                return finder.cycle();
            }
        }
        return null;
    }

    public boolean hasPath(int s, int t) {
        validateVertex(s);
        validateVertex(t);
        return distTo[s][t] < Double.POSITIVE_INFINITY;
    }

    public double dist(int s, int t) {
        validateVertex(s);
        validateVertex(t);
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
        return distTo[s][t];
    }

    public Iterable<DirectedEdge> path(int s, int t) {
        validateVertex(s);
        validateVertex(t);
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
        if (!hasPath(s, t)) return null;
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[s][t]; e != null; e = edgeTo[s][e.from()]) {
            path.push(e);
        }
        return path;
    }

    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    private static void unitTest(String url) {
        try {
            URL tinyEWD = new URL(url);
            In in = new In(tinyEWD);
            AdjMatrixEdgeWeightedDigraph G = new AdjMatrixEdgeWeightedDigraph(in);
            FloydWarshallAllPairsSP spt = new FloydWarshallAllPairsSP(G);
            // print negative cycle
            if (spt.hasNegativeCycle()) {
                StdOut.println("Negative cost cycle:");
                for (DirectedEdge e : spt.negativeCycle())
                    StdOut.println(e);
                StdOut.println();
            }

            // print all-pairs shortest paths
            else {
                for (int v = 0; v < G.V(); v++) {
                    for (int w = 0; w < G.V(); w++) {
                        if (spt.hasPath(v, w)) {
                            StdOut.printf("%d to %d (%5.2f)  ", v, w, spt.dist(v, w));
                            for (DirectedEdge edge : spt.path(v, w))
                                StdOut.print(edge + "  ");
                            StdOut.println();
                        } else StdOut.printf("%d to %d no path\n", v, w);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        unitTest("https://algs4.cs.princeton.edu/44sp/tinyEWDn.txt");
        unitTest("https://algs4.cs.princeton.edu/44sp/tinyEWDnc.txt");
    }
}
