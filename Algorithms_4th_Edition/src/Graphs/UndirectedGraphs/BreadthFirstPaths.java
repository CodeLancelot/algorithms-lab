package Graphs.UndirectedGraphs;

import Fundamentals.Queue;
import Fundamentals.Stack;
import libraries.*;

import java.net.URL;

public class BreadthFirstPaths {
    private boolean[] marked; // Is a shortest path to this vertex known?
    private int[] edgeTo; // last vertex on known path to this vertex
    private final int s; // source

    public BreadthFirstPaths(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        bfs(G, s);
    }

    private void bfs(Graph G, int s) {
        marked[s] = true;
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(s);
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    queue.enqueue(w);
                }
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }

    public static void main(String[] args) {
        try {
            URL tingCG = new URL("https://algs4.cs.princeton.edu/41graph/tinyCG.txt");
            In in = new In(tingCG);
            Graph G = new Graph(in);
            int s = Integer.parseInt(args[0]);
            BreadthFirstPaths paths = new BreadthFirstPaths(G, s);
            for (int v = 0; v < G.V(); v++) {
                if (paths.hasPathTo(v)) {
                    StdOut.print(s + " to " + v + ": ");
                    for (int x : paths.pathTo(v)) {
                        if (x == s) StdOut.print(x);
                        else StdOut.print("-" + x);
                    }
                    StdOut.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
