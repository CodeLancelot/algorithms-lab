package Graphs.EdgeWeightedDigraphs;

import libraries.StdOut;

// Critical path method for parallel precedence-constrained job scheduling
public class ParallelJobScheduling {
    private ParallelJobScheduling() {
    }

    public static void main(String[] args) {
        int N = 10;
        String[] jobsPC = {"41.0 1 7 9", "51.0 2", "50.0", "36.0", "38.0", "45.0", "21.0 3 8", "32.0 3 8", "32.0 2", "29.0 4 6"};
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(2 * N + 2);
        int source = 2 * N, sink = 2 * N + 1;
        for (int i = 0; i < N; i++) {
            int end = i + N;
            G.addEdge(source, i, 0.0);
            G.addEdge(end, sink, 0.0);
            String[] a = jobsPC[i].split("\\s+");
            double duration = Double.parseDouble(a[0]);
            G.addEdge(i, end, duration);
            for (int j = 1; j < a.length; j++) {
                int successor = Integer.parseInt(a[j]);
                G.addEdge(end, successor, 0.0);
            }
        }
        AcyclicLP lp = new AcyclicLP(G, source);
        StdOut.println(" job   start  finish");
        StdOut.println("--------------------");
        for (int i = 0; i < N; i++) {
            StdOut.printf("%4d %7.1f %7.1f\n", i, lp.distTo(i), lp.distTo(i + N));
        }
        StdOut.printf("Finish time: %7.1f\n", lp.distTo(sink));
    }
}
