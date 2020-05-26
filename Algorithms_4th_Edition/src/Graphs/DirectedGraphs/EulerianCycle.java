package Graphs.DirectedGraphs;

import Fundamentals.Stack;
import Graphs.UndirectedGraphs.ConnectedComponents;
import Graphs.UndirectedGraphs.Graph;
import libraries.StdOut;

import java.util.Iterator;

public class EulerianCycle {
    private Stack<Integer> cycle = new Stack<>();

    public EulerianCycle(Digraph G) {
        if (!hasMatchedPreconditions(G)) return;
        int s = 0;
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.V()];
        for (int v = 0; v < G.V(); v++)
            adj[v] = G.adj(v).iterator();

        Stack<Integer> stack = new Stack<>();
        stack.push(s);
        cycle = new Stack<>();
        while (!stack.isEmpty()) {
            int v = stack.pop();
            while (adj[v].hasNext()) {
                stack.push(v);
                v = adj[v].next();
            }
            cycle.push(v);
        }
        if (cycle.size() != G.E() + 1) cycle = null;
    }

    private static boolean hasMatchedPreconditions(Digraph G) {
        if (G.E() == 0) return false;
        // Condition 1: indegree(v) equals outdegree(v)
        for (int v = 0; v < G.V(); v++)
            if (G.outdegree(v) != G.indegree(v)) return false;

        // Condition 2: graph is connected
        Graph H = new Graph(G.V());
        for (int v = 0; v < G.V(); v++)
            for (int w : G.adj(v))
                H.addEdge(v, w);
        ConnectedComponents components = new ConnectedComponents(H);
        return components.count() == 1;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    private static void unitTest(Digraph G) {
        StdOut.println(G.toString());
        EulerianCycle euler = new EulerianCycle(G);
        StdOut.print("Eulerian cycle:  ");
        if (euler.hasCycle()) {
            for (int v : euler.cycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        } else StdOut.println("NOT EXISTS");
    }

    public static void main(String[] args) {
        Digraph G1 = new Digraph(6);
        G1.addEdge(0, 2);
        G1.addEdge(1, 0);
        G1.addEdge(2, 5);
        G1.addEdge(2, 3);
        G1.addEdge(3, 4);
        G1.addEdge(4, 2);
        G1.addEdge(5, 1);
        unitTest(G1);
        Digraph G2 = new Digraph(6);
        G2.addEdge(0, 1);
        G2.addEdge(1, 0);
        G2.addEdge(1, 2);
        G2.addEdge(2, 1);
        G2.addEdge(2, 3);
        G2.addEdge(3, 4);
        G2.addEdge(3, 5);
        G2.addEdge(4, 3);
        G2.addEdge(5, 2);
        unitTest(G2);
    }
}
