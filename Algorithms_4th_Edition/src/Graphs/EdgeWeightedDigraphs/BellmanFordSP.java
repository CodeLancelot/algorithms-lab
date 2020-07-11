package Graphs.EdgeWeightedDigraphs;

import Fundamentals.Queue;
import Fundamentals.Stack;
import Graphs.DirectedEdge;
import libraries.In;
import libraries.StdOut;

import java.net.URL;

// Queue-based Bellman-Ford algorithm for computing shortest-paths
public class BellmanFordSP {
    private double[] distTo;
    private DirectedEdge[] edgeTo;
    private boolean[] onQueue;
    private Queue<Integer> queue;
    private int cost; // number of calls to relax()
    private Iterable<DirectedEdge> negativeCycle; // cycle in edgeTo[]

    public BellmanFordSP(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        onQueue = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        queue = new Queue<>();
        queue.enqueue(s);
        onQueue[s] = true;
        while (!queue.isEmpty() && !hasNegativeCycle()) {
            int v = queue.dequeue();
            onQueue[v] = false;
            relax(G, v);
        }
    }

    // relax vertex v and put other endpoints on queue if changed
    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge edge : G.adj(v)) {
            int w = edge.to();
            double weight = edge.weight();
            if (distTo[w] > distTo[v] + weight) {
                distTo[w] = distTo[v] + weight;
                edgeTo[w] = edge;
                if (!onQueue[w]) {
                    queue.enqueue(w);
                    onQueue[w] = true;
                }
            }
            if (++cost % G.V() == 0)
                findNegativeCycle();
        }
    }

    private void findNegativeCycle() {
        EdgeWeightedDigraph spt = new EdgeWeightedDigraph(edgeTo.length);
        for (DirectedEdge edge : edgeTo) {
            if (edge != null)
                spt.addEdge(edge);
        }
        Cycle cycleFinder = new Cycle(spt);
        negativeCycle = cycleFinder.cycle();
    }

    public boolean hasNegativeCycle() {
        return negativeCycle != null;
    }

    public Iterable<DirectedEdge> negativeCycle() {
        return negativeCycle;
    }

    public double distTo(int v) {
        validatePrerequisite(v);
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        validatePrerequisite(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        validatePrerequisite(v);
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    private void validatePrerequisite(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
    }

    private static void unitTest(String url, int s) {
        try {
            URL tingEWD = new URL(url);
            In in = new In(tingEWD);
            EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
            BellmanFordSP sp = new BellmanFordSP(G, s);
            if (sp.hasNegativeCycle()) {
                for (DirectedEdge e : sp.negativeCycle())
                    StdOut.println(e);
            } else {
                for (int v = 0; v < G.V(); v++) {
                    if (sp.hasPathTo(v)) {
                        StdOut.printf("%d to %d (%5.2f)  ", s, v, sp.distTo(v));
                        for (DirectedEdge e : sp.pathTo(v)) {
                            StdOut.print(e + "   ");
                        }
                        StdOut.println();
                    } else StdOut.printf("%d to %d           no path\n", s, v);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        unitTest("https://algs4.cs.princeton.edu/44sp/tinyEWDn.txt", 0);
        unitTest("https://algs4.cs.princeton.edu/44sp/tinyEWDnc.txt", 7);
    }
}
