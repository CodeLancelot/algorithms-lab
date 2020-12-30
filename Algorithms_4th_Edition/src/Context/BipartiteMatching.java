package Context;

import Fundamentals.Queue;
import Graphs.UndirectedGraphs.BipartiteX;
import Graphs.UndirectedGraphs.Graph;

/**
 * The {@code BipartiteMatching} class represents a data type for computing a
 * <em>maximum (cardinality) matching</em> and a
 * <em>minimum (cardinality) vertex cover</em> in a bipartite graph.
 * A bipartite graph in a graph whose vertices can be partitioned
 * into two disjoint sets such that every edge has one endpoint in either set.
 * A matching in a graph is a subset of its edges with no common vertices.
 * A maximum matching is a matching with the maximum number of edges.
 * A perfect matching is a matching which matches all vertices in the graph.
 * A vertex cover in a graph is a subset of its vertices such that
 * every edge is incident to at least one vertex.
 * A minimum vertex cover is a vertex cover with the minimum number of vertices.
 * In any biparite graph, the maximum number of edges in matching
 * equals the minimum number of vertices in a vertex cover.
 * The maximum matching problem in <em>non-bipartite</em> graphs is
 * also important, but all known algorithms for this more general problem
 * are substantially more complicated.
 * <p>
 * This implementation uses the alternating-path algorithm.
 * It is equivalent to reducing to the maximum-flow problem and running
 * the augmenting-path algorithm on the resulting flow network, but it
 * does so with less overhead.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class BipartiteMatching {
    private static final int UNMATCHED = -1;

    private final int V;                 // number of vertices in the graph
    private BipartiteX bipartition;      // the bipartition
    private int cardinality;             // cardinality of current matching
    private int[] mate;                  // mate[v] =  w if v-w is an edge in current matching, mate[v] = -1 if v is not in current matching
    private boolean[] inMinVertexCover;  // inMinVertexCover[v] = true iff v is in min vertex cover
    private boolean[] marked;            // marked[v] = true if v is reachable via alternating path
    private int[] edgeTo;                // edgeTo[v] = last edge on alternating path to v

    /**
     * Determines a maximum matching (and a minimum vertex cover) in a bipartite graph.
     */
    public BipartiteMatching(Graph G) {
        bipartition = new BipartiteX(G);
        if (!bipartition.isBipartite()) {
            throw new IllegalArgumentException("graph is not bipartite");
        }

        this.V = G.V();

        // initialize empty matching
        mate = new int[V];
        for (int v = 0; v < V; v++)
            mate[v] = UNMATCHED;

        // alternating path algorithm
        while (hasAugmentingPath(G)) {
            // find one endpoint t in alternating path
            int t = -1;
            for (int v = 0; v < G.V(); v++) {
                if (!isMatched(v) && edgeTo[v] != -1) {
                    t = v;
                    break;
                }
            }

            // update the matching according to alternating path in edgeTo[] array
            for (int v = t; v != -1; v = edgeTo[edgeTo[v]]) {
                int w = edgeTo[v];
                mate[v] = w;
                mate[w] = v;
            }
            cardinality++;
        }

        // find min vertex cover from marked[] array
        inMinVertexCover = new boolean[V];
        for (int v = 0; v < V; v++) {
            if (bipartition.color(v) && !marked[v]) inMinVertexCover[v] = true;
            if (!bipartition.color(v) && marked[v]) inMinVertexCover[v] = true;
        }

        assert certifySolution(G);
    }

    /**
     * is there an augmenting path?
     * - if so, upon termination adj[] contains the level graph;
     * - if not, upon termination marked[] specifies those vertices reachable via an alternating
     * path from one side of the bipartition
     * <p>
     * an alternating path is a path whose edges belong alternately to the matching and not to the matching
     * an augmenting path is an alternating path that starts and ends at unmatched vertices
     * <p>
     * this implementation finds a shortest augmenting path (fewest number of edges)
     */
    private boolean hasAugmentingPath(Graph G) {
        marked = new boolean[V];
        edgeTo = new int[V];
        for (int v = 0; v < V; v++)
            edgeTo[v] = -1;

        // breadth-first search (starting from all unmatched vertices on one side of bipartition)
        Queue<Integer> queue = new Queue<>();
        for (int v = 0; v < V; v++) {
            if (bipartition.color(v) && !isMatched(v)) {
                queue.enqueue(v);
                marked[v] = true;
            }
        }

        // run BFS, stopping as soon as an alternating path is found
        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : G.adj(v)) {

                // either (1) forward edge not in matching or (2) backward edge in matching
                if (isResidualGraphEdge(v, w) && !marked[w]) {
                    edgeTo[w] = v;
                    marked[w] = true;
                    if (!isMatched(w)) return true;
                    queue.enqueue(w);
                }
            }
        }

        return false;
    }

    // is the edge v-w a forward edge not in the matching or a reverse edge in the matching?
    private boolean isResidualGraphEdge(int v, int w) {
        if ((mate[v] != w) && bipartition.color(v)) return true;
        if ((mate[v] == w) && !bipartition.color(v)) return true;
        return false;
    }

    /**
     * Returns the vertex to which the specified vertex is matched in
     * the maximum matching computed by the algorithm.
     */
    public int mate(int v) {
        validate(v);
        return mate[v];
    }

    /**
     * Returns true if the specified vertex is matched in the maximum matching
     * computed by the algorithm.
     */
    public boolean isMatched(int v) {
        validate(v);
        return mate[v] != UNMATCHED;
    }

    public int size() {
        return cardinality;
    }

    public boolean isPerfect() {
        return cardinality * 2 == V;
    }

    public boolean inMinVertexCover(int v) {
        validate(v);
        return inMinVertexCover[v];
    }

    private void validate(int v) {
        if (v < 0 || v >= V) throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    // check that mate[] and inVertexCover[] define a max matching and min vertex cover, respectively
    private boolean certifySolution(Graph G) {
        // check that mate(v) = w iff mate(w) = v
        for (int v = 0; v < V; v++) {
            if (mate(v) == -1) continue;
            if (mate(mate(v)) != v) return false;
        }

        // check that size() is consistent with mate()
        int matchedVertices = 0;
        for (int v = 0; v < V; v++) {
            if (mate(v) != -1) matchedVertices++;
        }
        if (2 * size() != matchedVertices) return false;

        // check that size() is consistent with minVertexCover()
        int sizeOfMinVertexCover = 0;
        for (int v = 0; v < V; v++) {
            if (inMinVertexCover(v)) sizeOfMinVertexCover++;
        }
        if (size() != sizeOfMinVertexCover) return false;

        // check that mate() uses each vertex at most once
        boolean[] isMatched = new boolean[V];
        for (int v = 0; v < V; v++) {
            int w = mate[v];
            if (w == -1) continue;
            if (v == w) return false;
            if (v >= w) continue;
            if (isMatched[v] || isMatched[w]) return false;
            isMatched[v] = true;
            isMatched[w] = true;
        }

        // check that mate() uses only edges that appear in the graph
        for (int v = 0; v < V; v++) {
            if (mate(v) == -1) continue;
            boolean isEdge = false;
            for (int w : G.adj(v)) {
                if (mate(v) == w) isEdge = true;
            }
            if (!isEdge) return false;
        }

        // check that inMinVertexCover() is a vertex cover
        for (int v = 0; v < V; v++) {
            for (int w : G.adj(v)) {
                if (!inMinVertexCover(v) && !inMinVertexCover(w)) return false;
            }
        }
        return true;
    }
}
