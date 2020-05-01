package Graphs.UndirectedGraphs;

import Fundamentals.Queue;
import Fundamentals.Stack;
import libraries.*;

import java.net.URL;

public class BipartiteX {
    private static final boolean WHITE = false;
    private static final boolean BLACK = true;

    private boolean[] color;       // color[v] gives vertices on one side of bipartition
    private boolean[] marked;      // marked[v] = true if v has been visited in BFS
    private int[] edgeTo;          // edgeTo[v] = last edge on path to v
    private Queue<Integer> cycle;  // odd-length cycle

    public BipartiteX(Graph G) {
        color = new boolean[G.V()];
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];

        for (int v = 0; v < G.V() && isBipartite(); v++) {
            if (!marked[v]) {
                bfs(G, v);
            }
        }
    }

    private void bfs(Graph G, int s) {
        marked[s] = true;
        color[s] = WHITE;
        Queue<Integer> queue = new Queue<>();
        queue.enqueue(s);
        while (!queue.isEmpty() && isBipartite()) {
            int v = queue.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    color[w] = !color[v];
                    queue.enqueue(w);
                } else if (color[w] == color[v]) {
                    cycle = new Queue<>();
                    Stack<Integer> path = new Stack<>();
                    int x = v, y = w;
                    while (x != y) {
                        path.push(x);
                        x = edgeTo[x];
                        cycle.enqueue(y);
                        y = edgeTo[y];
                    }
                    cycle.enqueue(x);
                    while (!path.isEmpty()) cycle.enqueue(path.pop());
                    cycle.enqueue(w);
                }
            }
        }
    }

    public boolean isBipartite() {
        return cycle == null;
    }

    public boolean color(int v) {
        if (!isBipartite())
            throw new UnsupportedOperationException("Graph is not bipartite");
        return color[v];
    }

    public Iterable<Integer> oddCycle() {
        return cycle;
    }

    public static void main(String[] args) {
        try {
            URL tingG = new URL("https://algs4.cs.princeton.edu/41graph/tinyG.txt");
            In in = new In(tingG);
            Graph G = new Graph(in);
            BipartiteX B = new BipartiteX(G);
            if (B.isBipartite()) {
                StdOut.println("Graph is bipartite");
                for (int v = 0; v < G.V(); v++) {
                    StdOut.println(v + ": " + B.color(v));
                }
            } else {
                StdOut.print("Graph has an odd-length cycle: ");
                for (int x : B.oddCycle()) {
                    StdOut.print(x + " ");
                }
                StdOut.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
