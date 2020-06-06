package Graphs.DirectedGraphs;

import Fundamentals.Queue;
import Fundamentals.Stack;
import libraries.StdOut;

// Gabow’s algorithm (path-based) for computing strongly connected components
public class GabowSCC {
    private boolean[] marked;
    private int[] preorder;
    private int preCounter;
    private int[] id;
    private int count;
    private Stack<Integer> stack1;
    private Stack<Integer> stack2;

    public GabowSCC(Digraph G) {
        marked = new boolean[G.V()];
        preorder = new int[G.V()];
        id = new int[G.V()];
        stack1 = new Stack<>();
        stack2 = new Stack<>();
        for (int v = 0; v < G.V(); v++)
            id[v] = -1;
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) dfs(G, v);
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        preorder[v] = preCounter++;
        stack1.push(v);
        stack2.push(v);
        for (int w : G.adj(v)) {
            if (!marked[w]) dfs(G, w);
            else if (id[w] == -1) {
                // later iterating vertexes on stack1, those removed from stack2 already are in the same component
                while (preorder[stack2.peek()] > preorder[w])
                    stack2.pop();
            }
        }

        // trace back to the start point of a subtree which is strongly connected component
        if (stack2.peek() == v) {
            stack2.pop();
            int w;
            do {
                w = stack1.pop();
                id[w] = count;
            } while (w != v);
            count++;
        }
    }

    public int count() {
        return count;
    }

    public boolean isStronglyConnected(int v, int w) {
        return id[v] == id[w];
    }

    public int id(int v) {
        return id[v];
    }

    private static void unitTest(Digraph G) {
        GabowSCC scc = new GabowSCC(G);
        int componentCount = scc.count();
        StdOut.println(componentCount + " strongly connected components");

        // compute list of vertices in each strong component
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[componentCount];
        for (int i = 0; i < componentCount; i++)
            components[i] = new Queue<>();
        for (int v = 0; v < G.V(); v++)
            components[scc.id(v)].enqueue(v);

        for (int i = 0; i < componentCount; i++) {
            for (int v : components[i])
                StdOut.print(v + " ");
            StdOut.println();
        }
    }

    public static void main(String[] args) {
        Digraph G1 = new Digraph(6);
        G1.addEdge(0, 1);
        G1.addEdge(0, 2);
        G1.addEdge(1, 3);
        G1.addEdge(2, 3);
        G1.addEdge(2, 4);
        G1.addEdge(3, 0);
        G1.addEdge(3, 5);
        G1.addEdge(4, 5);
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
        Digraph G3 = new Digraph(10);
        G3.addEdge(0, 1);
        G3.addEdge(1, 2);
        G3.addEdge(2, 0);
        G3.addEdge(2, 4);
        G3.addEdge(2, 3);
        G3.addEdge(3, 0);
        G3.addEdge(4, 5);
        G3.addEdge(5, 6);
        G3.addEdge(5, 7);
        G3.addEdge(6, 2);
        G3.addEdge(6, 4);
        G3.addEdge(7, 8);
        G3.addEdge(8, 9);
        G3.addEdge(8, 5);
        G3.addEdge(9, 5);
        unitTest(G3);
    }
}
