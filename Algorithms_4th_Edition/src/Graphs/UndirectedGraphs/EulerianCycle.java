package Graphs.UndirectedGraphs;

import Fundamentals.Queue;
import Fundamentals.Stack;
import libraries.StdOut;

public class EulerianCycle {
    private Stack<Integer> cycle = new Stack<Integer>();

    public EulerianCycle(Graph G) {
        if (!hasMatchedPreconditions(G)) return;
        Queue<Edge>[] adj = Edge.adjOfEdges(G);
        int s = 0;
        dfs(adj, s);
        if (cycle.size() != G.E() + 1) cycle = null;
    }

    private void dfs(Queue<Edge>[] adj, int v) {
        while (!adj[v].isEmpty()) {
            Edge edge = adj[v].dequeue();
            if (!edge.isUsed) {
                edge.isUsed = true;
                int w = edge.other(v);
                dfs(adj, w);
            }
        }
        cycle.push(v);
    }

    private static boolean hasMatchedPreconditions(Graph G) {
        if (G.E() == 0) return false;

        // Condition 1: all vertices have even degree
        for (int v = 0; v < G.V(); v++)
            if (G.degree(v) % 2 != 0) return false;

        // Condition 2: graph is connected
        ConnectedComponents components = new ConnectedComponents(G);
        return components.count() == 1;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    private static void unitTest(Graph G) {
        StdOut.println(G.toString());
        EulerianCycle euler = new EulerianCycle(G);
        StdOut.print("Eulerian cycle:  ");
        if (euler.hasCycle()) {
            for (int v : euler.cycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        } else {
            StdOut.println("NOT EXISTS");
        }
    }

    public static void main(String[] args) {
        Graph G1 = new Graph(7);
        G1.addEdge(0, 1);
        G1.addEdge(0, 6);
        G1.addEdge(1, 2);
        G1.addEdge(1, 4);
        G1.addEdge(1, 5);
        G1.addEdge(2, 3);
        G1.addEdge(3, 4);
        G1.addEdge(3, 4);
        G1.addEdge(3, 5);
        G1.addEdge(4, 5);
        G1.addEdge(5, 6);
        unitTest(G1);
        Graph G2 = new Graph(9);
        G2.addEdge(0, 1);
        G2.addEdge(0, 5);
        G2.addEdge(1, 2);
        G2.addEdge(1, 3);
        G2.addEdge(1, 6);
        G2.addEdge(2, 4);
        G2.addEdge(3, 4);
        G2.addEdge(5, 6);
        G2.addEdge(6, 7);
        G2.addEdge(6, 8);
        G2.addEdge(7, 8);
        unitTest(G2);
    }
}
