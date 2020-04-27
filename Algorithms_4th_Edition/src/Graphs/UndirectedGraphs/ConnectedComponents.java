package Graphs.UndirectedGraphs;

import Fundamentals.Bag;
import libraries.*;

import java.net.URL;

public class ConnectedComponents {
    private boolean[] marked;
    private int[] id;
    private int count = 0;

    public ConnectedComponents(Graph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        for (int s = 0; s < G.V(); s++) {
            if (!marked[s]) {
                dfs(G, s);
                count++;
            }
        }
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj(v))
            if (!marked[w]) dfs(G, w);
    }

    boolean connected(int v, int w) {
        return id[v] == id[w];
    }

    // number of connected components
    public int count() {
        return count;
    }

    // component identifier for v ( between 0 and count()-1 )
    public int id(int v) {
        return id[v];
    }

    public static void main(String[] args) {
        try {
            URL tingG = new URL("https://algs4.cs.princeton.edu/41graph/tinyG.txt");
            In in = new In(tingG);
            Graph G = new Graph(in);
            ConnectedComponents cc = new ConnectedComponents(G);
            int componentCount = cc.count();
            StdOut.println(componentCount + " components");
            Bag<Integer>[] components;
            components = (Bag<Integer>[]) new Bag[componentCount];
            for (int i = 0; i < componentCount; i++)
                components[i] = new Bag<>();
            for (int v = 0; v < G.V(); v++) {
                components[cc.id(v)].add(v);
            }
            for (int i = 0; i < componentCount; i++) {
                for (int v : components[i])
                    StdOut.print(v + " ");
                StdOut.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
