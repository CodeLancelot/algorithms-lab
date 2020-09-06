package Strings;

import libraries.StdOut;

// Knuth-Morris-Pratt algorithm for substring search
public class SubstringSearchKMP {
    private String pat;
    private int[][] dfa; //deterministic finite-state automaton (DFA)

    public SubstringSearchKMP(String pat) {
        this.pat = pat;
        int M = pat.length();
        int R = 256;
        dfa = new int[R][M];
        dfa[pat.charAt(0)][0] = 1;
        for (int backToState = 0, state = 1; state < M; state++) {
            for (int c = 0; c < R; c++)
                dfa[c][state] = dfa[c][backToState]; // Copy mismatch cases.
            dfa[pat.charAt(state)][state] = state + 1; // Set match case.
            backToState = dfa[pat.charAt(state)][backToState]; // Update restart state.
        }
    }

    public SubstringSearchKMP(String pat, int R) {
        this.pat = pat;
        int M = pat.length();
        dfa = new int[R][M];
        dfa[pat.charAt(0)][0] = 1;
        for (int backToState = 0, state = 1; state < M; state++) {
            for (int c = 0; c < R; c++)
                dfa[c][state] = dfa[c][backToState];
            dfa[pat.charAt(state)][state] = state + 1;
            backToState = dfa[pat.charAt(state)][backToState];
        }
    }

    public int search(String txt) {
        int M = pat.length(), N = txt.length(), i, j;
        for (i = 0, j = 0; i < N && j < M; i++)
            j = dfa[txt.charAt(i)][j];
        if (j == M) return i - M;
        else return N;
    }

    public int search(char[] txt) {
        int M = pat.length(), N = txt.length, i, j;
        for (i = 0, j = 0; i < N && j < M; i++)
            j = dfa[txt[i]][j];
        if (j == M) return i - M;
        else return N;
    }

    public static void main(String[] args) {
        String text = "abacadabrabracabracadabrabrabracad";
        String[] patterns = {"abracadabra", "rab", "bcara", "rabrabracad", "abacad"};
        for (String pat : patterns) {
            SubstringSearchKMP KMP = new SubstringSearchKMP(pat);
            int offset = KMP.search(text);
            StdOut.println("txt: " + text);
            StdOut.print("pat: ");
            for (int i = 0; i < offset; i++)
                StdOut.print(" ");
            StdOut.println(pat);
        }
    }
}
