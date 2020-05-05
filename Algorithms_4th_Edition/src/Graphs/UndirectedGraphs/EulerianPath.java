package Graphs.UndirectedGraphs;

import Fundamentals.Queue;
import Fundamentals.Stack;
import libraries.StdOut;

/**
 * An Eulerian path is a path that goes through each vertex in the graph and uses every edge in the graph exactly once.
 */
public class EulerianPath {
    private Stack<Integer> path = null;   // Eulerian path; null if no such path

    public EulerianPath(Graph G) {
        if (!hasMatchedPreconditions(G)) return;
        Queue<Edge>[] adj = Edge.adjOfEdges(G);

        int s = 0;
        for (int v = 0; v < G.V(); v++) {
            if (G.degree(v) % 2 != 0) {
                // if exist odd degree vertex, pick it up as start point
                s = v;
                break;
            }
        }

        // greedily search through edges in iterative DFS style
        Stack<Integer> searchStack = new Stack<>();
        searchStack.push(s);
        path = new Stack<>();
        while (!searchStack.isEmpty()) {
            int v = searchStack.pop();
            while (!adj[v].isEmpty()) {
                Edge edge = adj[v].dequeue();
                if (edge.isUsed) continue;
                edge.isUsed = true;
                searchStack.push(v);
                v = edge.other(v);
            }
            path.push(v);
        }

        if (path.size() != G.E() + 1) path = null;
    }

    private static boolean hasMatchedPreconditions(Graph G) {
        if (G.E() == 0) return false;

        // Condition 1: degree(v) is even except for possibly two
        int oddDegreeVertices = 0;
        for (int v = 0; v < G.V(); v++)
            if (G.degree(v) % 2 != 0)
                oddDegreeVertices++;
        if (oddDegreeVertices > 2) return false;

        // Condition 2: graph is connected
        ConnectedComponents components = new ConnectedComponents(G);
        return components.count() == 1;
    }

    public boolean hasPath() {
        return path != null;
    }

    public Iterable<Integer> path() {
        return path;
    }

    private static void unitTest(Graph G) {
        StdOut.println(G.toString());
        EulerianPath euler = new EulerianPath(G);
        StdOut.print("Eulerian path:  ");
        if (euler.hasPath()) {
            for (int v : euler.path()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        } else {
            StdOut.println("NOT EXISTS");
        }
    }

    public static void main(String[] args) {
        Graph G1 = new Graph(6);
        G1.addEdge(0, 1);
        G1.addEdge(0, 2);
        G1.addEdge(1, 2);
        G1.addEdge(2, 3);
        G1.addEdge(3, 4);
        G1.addEdge(4, 5);
        G1.addEdge(5, 5);
        unitTest(G1);
        Graph G2 = new Graph(5);
        G2.addEdge(0, 2);
        G2.addEdge(1, 2);
        G2.addEdge(2, 3);
        G2.addEdge(2, 4);
        G2.addEdge(3, 4);
        unitTest(G2);
        Graph G3 = new Graph(8);
        G3.addEdge(0, 1);
        G3.addEdge(0, 6);
        G3.addEdge(1, 2);
        G3.addEdge(1, 4);
        G3.addEdge(1, 5);
        G3.addEdge(2, 3);
        G3.addEdge(3, 4);
        G3.addEdge(3, 5);
        G3.addEdge(3, 7);
        G3.addEdge(4, 5);
        G3.addEdge(5, 6);
        unitTest(G3);
    }
}
