package Graphs.UndirectedGraphs;

import Searching.RedBlackBSTrees;
import libraries.*;

import java.net.URL;

public class SymbolGraph {
    private RedBlackBSTrees<String, Integer> st; // String keys (vertex names) and int values (indices)
    private String[] keys; // Inverted index
    private Graph graph; // The underlying graph

    public SymbolGraph(URL fileUrl, String sp) {
        st = new RedBlackBSTrees<>();
        In stream = new In(fileUrl);
        while (stream.hasNextLine()) {
            String[] keys = stream.readLine().split(sp);
            for (String key : keys)
                if (!st.contains(key)) st.put(key, st.size());
        }

        keys = new String[st.size()];
        for (String key : st.keys()) keys[st.get(key)] = key;

        graph = new Graph(st.size());
        stream = new In(fileUrl);
        while (stream.hasNextLine()) {
            String[] keys = stream.readLine().split(sp);
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

    public Graph graph() {
        return graph;
    }

    private void validateVertex(int v) {
        int V = graph.V();
        if (v < 0 || v >= V) throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public static void main(String[] args) {
        try {
            // routes.txt " "
            // movies.txt "/"
            String filename = args[0];
            String delimiter = args[1];
            SymbolGraph sg = new SymbolGraph(new URL("https://algs4.cs.princeton.edu/41graph/" + filename), delimiter);
            Graph graph = sg.graph();
            while (StdIn.hasNextLine()) {
                String source = StdIn.readLine();
                if (sg.contains(source)) {
                    int s = sg.indexOf(source);
                    for (int v : graph.adj(s)) {
                        StdOut.println("   " + sg.nameOf(v));
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
