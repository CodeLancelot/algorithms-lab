package Graphs.DirectedGraphs;

import Searching.RedBlackBSTrees;
import libraries.In;
import libraries.StdIn;
import libraries.StdOut;

import java.net.URL;

public class SymbolDigraph {
    private RedBlackBSTrees<String, Integer> st; // String keys (vertex names) and int values (indices)
    private String[] keys; // Inverted index
    private Digraph graph; // The underlying graph

    public SymbolDigraph(URL fileUrl, String delimiter) {
        st = new RedBlackBSTrees<>();
        In stream = new In(fileUrl);
        while (stream.hasNextLine()) {
            String[] keys = stream.readLine().split(delimiter);
            for (String key : keys)
                if (!st.contains(key)) st.put(key, st.size());
        }

        keys = new String[st.size()];
        for (String key : st.keys()) keys[st.get(key)] = key;

        graph = new Digraph(st.size());
        stream = new In(fileUrl);
        while (stream.hasNextLine()) {
            String[] keys = stream.readLine().split(delimiter);
            int v = st.get(keys[0]);
            for (int i = 1; i < keys.length; i++) graph.addEdge(v, st.get(keys[i]));
        }
    }

    public boolean contains(String key) {
        return st.contains(key);
    }

    public int indexOf(String key) {
        return st.get(key);
    }

    public String nameOf(int v) {
        validateVertex(v);
        return keys[v];
    }

    public Digraph digraph() {
        return graph;
    }

    private void validateVertex(int v) {
        int V = graph.V();
        if (v < 0 || v >= V) throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public static void main(String[] args) {
        try {
            SymbolDigraph sd = new SymbolDigraph(new URL("https://algs4.cs.princeton.edu/42digraph/routes.txt"), " ");
            Digraph graph = sd.digraph();
            while (StdIn.hasNextLine()) {
                String source = StdIn.readLine();
                if (sd.contains(source)) {
                    int s = sd.indexOf(source);
                    for (int v : graph.adj(s)) {
                        StdOut.println("   " + sd.nameOf(v));
                    }
                } else {
                    StdOut.println("input not contain '" + source + "'");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
