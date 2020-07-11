package Graphs.EdgeWeightedDigraphs;

import Graphs.DirectedEdge;
import libraries.In;
import libraries.StdOut;

import java.net.URL;

// Arbitrage in currency exchange
public class Arbitrage {
    private Arbitrage() {
    }

    public static void main(String[] args) {
        try {
            URL rates = new URL("https://algs4.cs.princeton.edu/44sp/rates.txt");
            In in = new In(rates);

            // V currencies
            int V = in.readInt();
            String[] name = new String[V];

            // create complete network
            EdgeWeightedDigraph G = new EdgeWeightedDigraph(V);
            for (int v = 0; v < V; v++) {
                name[v] = in.readString();
                for (int w = 0; w < V; w++) {
                    double rate = in.readDouble();
                    // use logarithm to transform the operations among the vertexes
                    DirectedEdge e = new DirectedEdge(v, w, -Math.log(rate));
                    G.addEdge(e);
                }
            }

            // find negative cycle
            BellmanFordSP spt = new BellmanFordSP(G, 0);
            if (spt.hasNegativeCycle()) {
                double stake = 1000.0;
                for (DirectedEdge e : spt.negativeCycle()) {
                    StdOut.printf("%10.5f %s ", stake, name[e.from()]);
                    stake *= Math.exp(-e.weight());
                    StdOut.printf("= %10.5f %s\n", stake, name[e.to()]);
                }
            } else {
                StdOut.println("No arbitrage opportunity");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}