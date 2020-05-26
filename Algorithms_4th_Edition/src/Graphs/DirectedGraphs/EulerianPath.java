package Graphs.DirectedGraphs;

import Fundamentals.Queue;
import Fundamentals.Stack;
import Graphs.Edge;
import Graphs.UndirectedGraphs.ConnectedComponents;
import Graphs.UndirectedGraphs.Graph;
import libraries.StdOut;

public class EulerianPath {
    private Stack<Integer> path = null;   // Eulerian path; null if no such path

    public EulerianPath(Digraph G) {
        if (!hasMatchedPreconditions(G)) return;
        Queue<Edge>[] adj = Edge.adjOfEdges(G);
        int s = 0;
        for (int v = 0; v < G.V(); v++) {
            if (G.outdegree(v) - G.indegree(v) == 1) {
                s = v;
                break;
            }
        }

        Stack<Integer> stack = new Stack<>();
        stack.push(s);
        path = new Stack<>();
        while (!stack.isEmpty()) {
            int v = stack.pop();
            while (!adj[v].isEmpty()) {
                Edge edge = adj[v].dequeue();
                stack.push(v);
                v = edge.other(v);
            }
            path.push(v);
        }
        // check if all edges have been used
        if (path.size() != G.E() + 1) path = null;
    }

    private static boolean hasMatchedPreconditions(Digraph G) {
        if (G.E() == 0) return false;

        // Condition 1: indegree(v) equals outdegree(v) except for possibly two vertices which satisfy outdegree(sv) - indegree(sv) = 1 and outdegree(ev) - indegree(ev) = -1
        int degreeOffset = 0;
        for (int v = 0; v < G.V(); v++)
            degreeOffset += (G.outdegree(v) - G.indegree(v));
        if (degreeOffset != 0) return false;

        // Condition 2: graph is connected
        Graph H = new Graph(G.V());
        for (int v = 0; v < G.V(); v++)
            for (int w : G.adj(v))
                H.addEdge(v, w);
        ConnectedComponents components = new ConnectedComponents(H);
        return components.count() == 1;
    }

    public boolean hasPath() {
        return path != null;
    }

    public Iterable<Integer> path() {
        return path;
    }

    private static void unitTest(Digraph G) {
        StdOut.println(G.toString());
        EulerianPath euler = new EulerianPath(G);
        StdOut.print("Eulerian path:  ");
        if (euler.hasPath()) {
            for (int v : euler.path()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        } else StdOut.println("NOT EXISTS");
    }

    public static void main(String[] args) {
        Digraph G1 = new Digraph(6);
        G1.addEdge(0, 2);
        G1.addEdge(1, 5);
        G1.addEdge(2, 1);
        G1.addEdge(2, 3);
        G1.addEdge(3, 3);
        G1.addEdge(3, 4);
        G1.addEdge(4, 2);
        G1.addEdge(5, 2);
        unitTest(G1);
        Digraph G2 = new Digraph(6);
        G2.addEdge(0, 1);
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
