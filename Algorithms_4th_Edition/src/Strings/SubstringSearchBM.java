package Strings;

import libraries.StdOut;

// Boyer-Moore algorithm for substring search
public class SubstringSearchBM {
    private int[] right;
    private String pat;

    public SubstringSearchBM(String pat) {
        this.pat = pat;
        int M = pat.length();
        int R = 256;
        right = new int[R];
        for (int c = 0; c < R; c++)
            right[c] = -1; // -1 for chars not in pattern
        for (int i = 0; i < M; i++)
            right[pat.charAt(i)] = i; // rightmost position for chars in pattern
    }

    public int search(String txt) { // Search for pattern in txt.
        int N = txt.length(), M = pat.length(), offset;
        for (int i = 0; i <= N - M; i += offset) {
            offset = 0;
            for (int j = M - 1; j >= 0; j--) {
                if (txt.charAt(i + j) != pat.charAt(j)) {
                    offset = j - right[txt.charAt(i + j)];
                    if (offset < 0) offset = 1;
                    break;
                }
            }
            if (offset == 0) return i;
        }
        return N;
    }

    public int search(char[] txt) {
        int N = txt.length, M = pat.length(), offset;
        for (int i = 0; i <= N - M; i += offset) {
            offset = 0;
            for (int j = M - 1; j >= 0; j--) {
                if (txt[i + j] != pat.charAt(j)) {
                    offset = j - right[txt[i + j]];
                    if (offset < 0) offset = 1;
                    break;
                }
            }
            if (offset == 0) return i;
        }
        return N;
    }

    public static void main(String[] args) {
        String text = "abacadabrabracabracadabrabrabracad";
        String[] patterns = {"abracadabra", "rab", "bcara", "rabrabracad", "abacad"};
        for (String pat : patterns) {
            SubstringSearchBM BM = new SubstringSearchBM(pat);
            int offset = BM.search(text);
            StdOut.println("txt: " + text);
            StdOut.print("pat: ");
            for (int i = 0; i < offset; i++)
                StdOut.print(" ");
            StdOut.println(pat);
        }
    }
}
