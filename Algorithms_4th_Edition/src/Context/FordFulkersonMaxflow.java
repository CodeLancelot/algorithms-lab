package Context;

import Fundamentals.Bag;
import Fundamentals.Queue;
import libraries.StdOut;

public class FordFulkersonMaxflow {
    private static final double EPSILON = 1E-11;

    private final int V;
    private boolean[] marked;
    private FlowEdge[] edgeTo;
    private double value;

    public FordFulkersonMaxflow(FlowNetwork network, int s, int t) {
        V = network.V();
        validate(s);
        validate(t);
        if (s == t) throw new IllegalArgumentException("Source equals sink");
        if (!isFeasible(network, s, t)) throw new IllegalArgumentException("Initial flow is infeasible");

        while (hasAugmentingPath(network, s, t)) {
            double minUnusedCapacity = Double.POSITIVE_INFINITY;
            for (int v = t; v != s; v = edgeTo[v].other(v))
                minUnusedCapacity = Math.min(minUnusedCapacity, edgeTo[v].residualCapacityTo(v));
            for (int v = t; v != s; v = edgeTo[v].other(v))
                edgeTo[v].addResidualFlowTo(v, minUnusedCapacity);
            value += minUnusedCapacity;
        }

        // check optimality conditions
        assert check(network, s, t);
    }

    public double value() {
        return value;
    }

    public boolean inSourceSetOfCut(int v) {
        validate(v);
        return marked[v];
    }

    public Bag<FlowEdge> minCutSet(FlowNetwork network) {
        Bag<FlowEdge> edges = new Bag<>();
        for (int v = 0; v < network.V(); v++) {
            for (FlowEdge edge : network.adj(v))
                if (v == edge.from() && inSourceSetOfCut(edge.from()) && !inSourceSetOfCut(edge.to()))
                    edges.add(edge);
        }
        return edges;
    }

    // throw an IllegalArgumentException if v is outside prescibed range
    private void validate(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    private boolean hasAugmentingPath(FlowNetwork network, int s, int t) {
        marked = new boolean[network.V()];
        edgeTo = new FlowEdge[network.V()];
        Queue<Integer> q = new Queue<>();
        marked[s] = true;
        q.enqueue(s);

        while (!q.isEmpty() && !marked[t]) {
            int v = q.dequeue();
            for (FlowEdge edge : network.adj(v)) {
                int w = edge.other(v);
                if (edge.residualCapacityTo(w) > 0 && !marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = edge;
                    q.enqueue(w);
                }
            }
        }

        return marked[t];
    }

    // return excess flow at vertex v
    private double excess(FlowNetwork network, int v) {
        double excess = 0.0;
        for (FlowEdge e : network.adj(v)) {
            if (v == e.from()) excess -= e.flow();
            else excess += e.flow();
        }
        return excess;
    }

    // return excess flow at vertex v
    private boolean isFeasible(FlowNetwork network, int s, int t) {
        // check that capacity constraints are satisfied
        for (int v = 0; v < network.V(); v++) {
            for (FlowEdge e : network.adj(v)) {
                if (e.flow() < 0 || e.flow() > e.capacity()) {
                    System.err.println("Edge does not satisfy capacity constraints: " + e);
                    return false;
                }
            }
        }

        // check that net flow into a vertex equals zero, except at source and sink
        if (Math.abs(value + excess(network, s)) > EPSILON) {
            System.err.println("Excess at source = " + excess(network, s));
            System.err.println("Max flow         = " + value);
            return false;
        }
        if (Math.abs(value - excess(network, t)) > EPSILON) {
            System.err.println("Excess at sink   = " + excess(network, t));
            System.err.println("Max flow         = " + value);
            return false;
        }
        for (int v = 0; v < network.V(); v++) {
            if (!(v == s || v == t) && Math.abs(excess(network, v)) > EPSILON) {
                System.err.println("Net flow out of " + v + " doesn't equal zero");
                return false;
            }
        }
        return true;
    }

    private boolean check(FlowNetwork network, int s, int t) {
        // check that flow is feasible
        if (!isFeasible(network, s, t)) {
            System.err.println("Flow is infeasible");
            return false;
        }

        // check that value of min cut = value of max flow
        double minCutValue = 0.0;
        for (FlowEdge e : minCutSet(network))
            minCutValue += e.capacity();

        if (Math.abs(minCutValue - value) > EPSILON) {
            System.err.println("Max flow value = " + value + ", min cut value = " + minCutValue);
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        int V = 6;
        int s = 0, t = V - 1;
        FlowNetwork network = new FlowNetwork(V);
        network.addEdge(0, 1, 2.0);
        network.addEdge(0, 2, 3.0);
        network.addEdge(1, 3, 3.0);
        network.addEdge(1, 4, 1.0);
        network.addEdge(2, 3, 1.0);
        network.addEdge(2, 4, 1.0);
        network.addEdge(3, 5, 2.0);
        network.addEdge(4, 5, 3.0);

        // compute maximum flow and minimum cut
        FordFulkersonMaxflow maxflow = new FordFulkersonMaxflow(network, s, t);
        StdOut.println("Max flow from " + s + " to " + t);
        for (int v = 0; v < network.V(); v++) {
            for (FlowEdge e : network.adj(v)) {
                if ((v == e.from()) && e.flow() > 0)
                    StdOut.println("   " + e);
            }
        }

        // print min-cut
        StdOut.print("The source side vertex-set of the min cut: ");
        for (int v = 0; v < network.V(); v++) {
            if (maxflow.inSourceSetOfCut(v)) StdOut.print(v + " ");
        }
        StdOut.println();

        StdOut.println("Max flow value = " + maxflow.value());
    }
}
