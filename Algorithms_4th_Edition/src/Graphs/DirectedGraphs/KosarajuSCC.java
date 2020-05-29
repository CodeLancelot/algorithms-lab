package Graphs.DirectedGraphs;

import Fundamentals.Queue;
import libraries.In;
import libraries.StdOut;

import java.net.URL;

// Kosaraju’s algorithm for computing strongly connected components
public class KosarajuSCC {
    private boolean[] marked;
    private int[] id; // component identifiers
    private int count; // number of components

    public KosarajuSCC(Digraph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        DepthFirstOrder dfo = new DepthFirstOrder(G.reverse());
        for (int s : dfo.reversePost()) {
            if (!marked[s]) {
                dfs(G, s);
                count++;
            }
        }
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj(v)) {
            if (!marked[w]) dfs(G, w);
        }
    }

    public boolean isStronglyConnected(int v, int w) {
        return id[v] == id[w];
    }

    public int id(int v) {
        return id[v];
    }

    public int count() {
        return count;
    }

    public static void main(String[] args) {
        try {
            URL tingDG = new URL("https://algs4.cs.princeton.edu/42digraph/tinyDG.txt");
            In in = new In(tingDG);
            Digraph G = new Digraph(in);
            KosarajuSCC scc = new KosarajuSCC(G);
            int componentCount = scc.count();
            StdOut.println(componentCount + " strongly connected components");

            // compute list of vertices in each strong component
            Queue<Integer>[] components = (Queue<Integer>[]) new Queue[componentCount];
            for (int i = 0; i < componentCount; i++) components[i] = new Queue<Integer>();
            for (int v = 0; v < G.V(); v++) components[scc.id(v)].enqueue(v);

            for (int i = 0; i < componentCount; i++) {
                for (int v : components[i]) StdOut.print(v + " ");
                StdOut.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
