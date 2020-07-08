package Graphs.EdgeWeightedDigraphs;

import Fundamentals.Stack;
import Graphs.DirectedEdge;
import libraries.StdOut;

public class Cycle {
    private boolean[] marked;
    private DirectedEdge[] edgeTo;
    private Stack<DirectedEdge> cycle;
    private boolean[] onStack;

    public Cycle(EdgeWeightedDigraph G) {
        marked = new boolean[G.V()];
        onStack = new boolean[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        if (hasSelfLoop(G)) return;
        for (int v = 0; v < G.V(); v++)
            if (!marked[v] && !hasCycle()) dfs(G, v);
    }

    private void dfs(EdgeWeightedDigraph G, int v){
        onStack[v] = true;
        marked[v] = true;
        for (DirectedEdge edge : G.adj(v)) {
            if (hasCycle()) return;
            int w = edge.to();
            if (!marked[w]) {
                edgeTo[w] = edge;
                dfs(G, w);
            }
            else if (onStack[w]) {
                cycle = new Stack<>();
                cycle.push(edge);
                int x = v;
                while (x != w) {
                    DirectedEdge e = edgeTo[x];
                    cycle.push(e);
                    x = e.from();
                }
            }
        }
        onStack[v] = false;
    }

    private boolean hasSelfLoop(EdgeWeightedDigraph G){
        for (int v = 0; v < G.V(); v++) {
            for(DirectedEdge edge : G.adj(v)){
                if (edge.to() == v) {
                    cycle = new Stack<>();
                    cycle.push(edge);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<DirectedEdge> cycle() {
        return cycle;
    }

    private static void unitTest(EdgeWeightedDigraph G) {
        Cycle finder = new Cycle(G);
        if (finder.hasCycle()) {
            StdOut.print("Cycle: ");
            for (DirectedEdge e : finder.cycle()) {
                StdOut.print(e + " ");
            }
            StdOut.println();
        }
        else {
            StdOut.println("No directed cycle");
        }
    }
    public static void main(String[] args) {
        EdgeWeightedDigraph G1 = new EdgeWeightedDigraph(6);
        G1.addEdge(0, 2, 1.0);
        G1.addEdge(1, 5, 1.0);
        G1.addEdge(2, 1, 1.0);
        G1.addEdge(2, 3, 1.0);
        G1.addEdge(3, 4, 1.0);
        G1.addEdge(4, 2, 1.0);
        G1.addEdge(5, 2, 1.0);
        unitTest(G1);
        EdgeWeightedDigraph G2 = new EdgeWeightedDigraph(6);
        G2.addEdge(0, 2, 1.0);
        G2.addEdge(1, 5, 1.0);
        G2.addEdge(2, 1, 1.0);
        G2.addEdge(2, 3, 1.0);
        G2.addEdge(2, 5, 1.0);
        G2.addEdge(3, 4, 1.0);
        unitTest(G2);
    }
}
