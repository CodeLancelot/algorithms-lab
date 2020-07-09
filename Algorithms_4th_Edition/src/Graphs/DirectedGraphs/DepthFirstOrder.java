package Graphs.DirectedGraphs;

import Fundamentals.Queue;
import Fundamentals.Stack;
import Graphs.DirectedEdge;
import Graphs.EdgeWeightedDigraphs.EdgeWeightedDigraph;
import libraries.In;
import libraries.StdOut;

import java.net.URL;

public class DepthFirstOrder {
    private boolean[] marked;
    private int[] preIndex;
    private int[] postIndex;
    private int preCounter;
    private int postCounter;
    private Queue<Integer> preorder;
    private Queue<Integer> postorder;

    public DepthFirstOrder(Digraph G) {
        marked = new boolean[G.V()];
        preIndex = new int[G.V()];
        postIndex = new int[G.V()];
        postorder = new Queue<>();
        preorder = new Queue<>();
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) dfs(G, v);
        assert check();
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        preorder.enqueue(v);
        preIndex[v] = preCounter++;
        for (int w : G.adj(v))
            if (!marked[w]) dfs(G, w);
        postorder.enqueue(v);
        postIndex[v] = postCounter++;
    }

    public DepthFirstOrder(EdgeWeightedDigraph G) {
        marked = new boolean[G.V()];
        preIndex = new int[G.V()];
        postIndex = new int[G.V()];
        postorder = new Queue<>();
        preorder = new Queue<>();
        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) dfs(G, v);
        assert check();
    }

    private void dfs(EdgeWeightedDigraph G, int v) {
        marked[v] = true;
        preorder.enqueue(v);
        preIndex[v] = preCounter++;
        for (DirectedEdge edge : G.adj(v)) {
            int w = edge.to();
            if (!marked[w]) dfs(G, w);
        }
        postorder.enqueue(v);
        postIndex[v] = postCounter++;
    }

    public Iterable<Integer> pre() {
        return preorder;
    }

    public Iterable<Integer> post() {
        return postorder;
    }

    public Iterable<Integer> reversePost() {
        Stack<Integer> reverse = new Stack<Integer>();
        for (int v : postorder)
            reverse.push(v);
        return reverse;
    }

    public int preIndex(int v) {
        return preIndex[v];
    }

    public int postIndex(int v) {
        return postIndex[v];
    }

    private boolean check() {
        int r = 0;
        for (int v : pre()) {
            if (preIndex(v) != r) {
                StdOut.println("pre(v) and pre() inconsistent");
                return false;
            }
            r++;
        }
        r = 0;
        for (int v : post()) {
            if (postIndex(v) != r) {
                StdOut.println("post(v) and post() inconsistent");
                return false;
            }
            r++;
        }
        return true;
    }

    public static void main(String[] args) {
        try {
            URL tingDAG = new URL("https://algs4.cs.princeton.edu/42digraph/tinyDAG.txt");
            In in = new In(tingDAG);
            Digraph G = new Digraph(in);
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            StdOut.println("   v  pre post");
            StdOut.println("--------------");
            for (int v = 0; v < G.V(); v++)
                StdOut.printf("%4d %4d %4d\n", v, dfs.preIndex(v), dfs.postIndex(v));

            StdOut.print("Preorder:  ");
            for (int v : dfs.pre())
                StdOut.print(v + " ");
            StdOut.println();

            StdOut.print("Postorder: ");
            for (int v : dfs.post())
                StdOut.print(v + " ");
            StdOut.println();

            StdOut.print("Reverse postorder: ");
            for (int v : dfs.reversePost())
                StdOut.print(v + " ");
            StdOut.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
