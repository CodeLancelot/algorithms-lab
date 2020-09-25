package Strings;

import Fundamentals.Bag;
import Fundamentals.Stack;
import Graphs.DirectedGraphs.DepthFirstSearch;
import Graphs.DirectedGraphs.Digraph;
import libraries.StdOut;

// Nondeterministic Finite Automaton
public class NFA {
    private char[] states; // each state correspond to a character of the regular expression
    private Digraph G; // hold epsilon transitions
    private int M;

    public NFA(String regexp) {
        states = regexp.toCharArray();
        M = states.length;
        G = new Digraph(M + 1);
        Stack<Integer> ops = new Stack<>();

        for (int i = 0; i < M; i++) {
            int lp = i;
            if (states[i] == '(' || states[i] == '|')
                ops.push(i);
            else if (states[i] == ')') {
                int op = ops.pop();
                if (states[op] == '|') {
                    lp = ops.pop();
                    G.addEdge(lp, op + 1);
                    G.addEdge(op, i);
                } else lp = op;
            }
            if (i < M - 1 && states[i + 1] == '*') {
                G.addEdge(lp, i + 1);
                G.addEdge(i + 1, lp);
            }
            if (states[i] == '(' || states[i] == '*' || states[i] == ')')
                G.addEdge(i, i + 1);
        }
    }

    public boolean recognizes(String txt) {
        // the states of NFA that is reachable from the source state
        Bag<Integer> stateSet = new Bag<>();
        DepthFirstSearch dfs = new DepthFirstSearch(G, 0);
        for (int v = 0; v < G.V(); v++)
            if (dfs.marked(v)) stateSet.add(v);
        for (int i = 0; i < txt.length(); i++) {
            Bag<Integer> sourceStates = new Bag<>();
            for (int state : stateSet) {
                if (state == M) return true;
                if (this.states[state] == txt.charAt(i) || this.states[state] == '.')
                    sourceStates.add(state + 1); // a match transaction
            }

            stateSet = new Bag<>();
            dfs = new DepthFirstSearch(G, sourceStates);
            for (int v = 0; v < G.V(); v++)
                if (dfs.marked(v)) stateSet.add(v);
        }

        for (int state : stateSet)
            if (state == M) return true;
        return false;
    }

    public static void main(String[] args) {
        String[] regexps = {
                "(A*B|AC)D", "(A*B|AC)D", "(a|(bc)*d)*", "(a|(bc)*d)*", ".* AB((C|D*E)F)*G"
        };
        String[] txts = {
                "AAAABD", "AAAAC", "abcbcd", "abcbcbcdaaaabcbcdaaaddd", "TEST ABDDEFCFG"
        };
        for (int i = 0; i < 5; i++) {
            String regexp = "(" + regexps[i] + ")";
            NFA nfa = new NFA(regexp);
            StdOut.println(nfa.recognizes(txts[i]));
        }
    }
}
