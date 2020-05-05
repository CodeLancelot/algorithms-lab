package Graphs.UndirectedGraphs;

import libraries.*;

import java.net.URL;

public class DegreesOfSeparation {
    // this class cannot be instantiated
    private DegreesOfSeparation() {
    }

    public static void main(String[] args) {
        try {
            String filename = args[0];
            String delimiter = args[1];
            String source = args[2];
            SymbolGraph sg = new SymbolGraph(new URL("https://algs4.cs.princeton.edu/41graph/" + filename), delimiter);
            Graph graph = sg.graph();
            if (!sg.contains(source)) {
                StdOut.println(source + " not in database.");
                return;
            }
            ;
            BreadthFirstPaths bfs = new BreadthFirstPaths(graph, sg.indexOf(source));
            while (!StdIn.isEmpty()) {
                String target = StdIn.readLine();
                if (sg.contains(target)) {
                    int v = sg.indexOf(target);
                    if (bfs.hasPathTo(v)) {
                        for (int w : bfs.pathTo(v)) StdOut.println(" " + sg.nameOf(w));
                    } else StdOut.println("Not connected with " + source);
                } else StdOut.println("Not in database.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
