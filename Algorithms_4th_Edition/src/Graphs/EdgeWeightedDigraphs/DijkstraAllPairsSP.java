package Graphs.EdgeWeightedDigraphs;

import Graphs.DirectedEdge;
import libraries.In;
import libraries.StdOut;

import java.net.URL;

public class DijkstraAllPairsSP {
    private DijkstraSP[] all;

    public DijkstraAllPairsSP(EdgeWeightedDigraph G) {
        all = new DijkstraSP[G.V()];
        for (int v = 0; v < G.V(); v++) all[v] = new DijkstraSP(G, v);
    }

    public Iterable<DirectedEdge> path(int s, int t) {
        validateVertex(s);
        validateVertex(t);
        return all[s].pathTo(t);
    }

    public boolean hasPath(int s, int t) {
        validateVertex(s);
        validateVertex(t);
        return dist(s, t) < Double.POSITIVE_INFINITY;
    }

    public double dist(int s, int t) {
        validateVertex(s);
        validateVertex(t);
        return all[s].distTo(t);
    }

    private void validateVertex(int v) {
        int V = all.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public static void main(String[] args) {
        try {
            URL tingDG = new URL("https://algs4.cs.princeton.edu/44sp/tinyEWD.txt");
            In in = new In(tingDG);
            EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
            DijkstraAllPairsSP spt = new DijkstraAllPairsSP(G);
            // print all-pairs shortest paths
            for (int v = 0; v < G.V(); v++) {
                for (int w = 0; w < G.V(); w++) {
                    if (spt.hasPath(v, w)) {
                        StdOut.printf("%d to %d (%5.2f)  ", v, w, spt.dist(v, w));
                        for (DirectedEdge edge : spt.path(v, w)) StdOut.print(edge + "  ");
                        StdOut.println();
                    } else StdOut.printf("%d to %d no path\n", v, w);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
