package Graphs.DirectedGraphs;

import libraries.In;
import libraries.StdOut;

import java.net.URL;

public class DepthFirstSearch {
    private boolean[] marked;
    private int count;

    DepthFirstSearch(Digraph G, int s) {
        marked = new boolean[G.V()];
        dfs(G, s);
    }

    public DepthFirstSearch(Digraph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        for (int v : sources)
            if (!marked[v]) dfs(G, v);
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        count++;
        for (int w : G.adj(v))
            if (!marked[w]) dfs(G, w);
    }

    public boolean marked(int v) {
        return marked[v];
    }

    public int count() {
        return count;
    }

    public boolean hasParallelEdges(Digraph G) {
        for (int v = 0; v < G.V(); v++) {
            for (int w : G.adj(v)) {
                if (marked[w]) {
                    return true;
                } else marked[w] = true;
            }
            for (int w : G.adj(v))
                marked[w] = false;
        }
        return false;
    }

    public static void main(String[] args) {
        try {
            URL tingDG = new URL("https://algs4.cs.princeton.edu/42digraph/tinyDG.txt");
            In in = new In(tingDG);
            Digraph G = new Digraph(in);
            int s = Integer.parseInt(args[0]);
            DepthFirstSearch search = new DepthFirstSearch(G, s);
            for (int v = 0; v < G.V(); v++)
                if (search.marked(v)) StdOut.print(v + " ");
            StdOut.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
