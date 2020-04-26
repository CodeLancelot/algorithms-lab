package Graphs.UndirectedGraphs;

import libraries.*;

import java.net.URL;

public class DepthFirstSearch {
    private boolean[] marked;
    private int count;

    public DepthFirstSearch(Graph G, int s) {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        count++;
        for (int w : G.adj(v))
            if (!marked[w]) dfs(G, w);
    }

    public boolean marked(int w) {
        return marked[w];
    }

    public int count() {
        return count;
    }

    public static void main(String[] args) {
        try {
            URL tingG = new URL("https://algs4.cs.princeton.edu/41graph/tinyG.txt");
            In in = new In(tingG);
            Graph G = new Graph(in);
            int s = Integer.parseInt(args[0]);
            DepthFirstSearch search = new DepthFirstSearch(G, s);
            for (int v = 0; v < G.V(); v++)
                if (search.marked(v))
                    StdOut.print(v + " ");
            StdOut.println();
            if (search.count() != G.V())
                StdOut.print("NOT ");
            StdOut.println("CONNECTED");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
