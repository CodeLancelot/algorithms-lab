package Strings;

import libraries.StdOut;

import java.math.BigInteger;
import java.util.Random;

// Rabin-Karp fingerprint substring search
public class SubstringSearchRK {
    private String pat;
    private long patHash;
    private int M;   // pattern length
    private long Q;  // a large prime, small enough to avoid long overflow
    private int R;
    private long RM; // R^(M-1) % Q

    public SubstringSearchRK(String pat) {
        this(pat, 256);
    }

    public SubstringSearchRK(String pat, int R) {
        this.pat = pat;
        this.M = pat.length();
        this.Q = longRandomPrime();
        this.R = R;
        this.RM = 1;
        for (int i = 1; i < M; i++)
            RM = (RM * R) % Q;
        this.patHash = this.hash(pat, M);
    }

    // a random 31-bit prime
    private static long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

    // Compute hash for key[0..M-1]
    private long hash(String key, int M) {
        long h = 0;
        for (int i = 0; i < M; i++)
            h = (h * R + key.charAt(i)) % Q;
        return h;
    }

    private boolean check(int i) {
        return true; // Monte Carlo
        // For Las Vegas, check pat vs txt(i..i+M-1).
    }

    public int search(String txt) {
        int N = txt.length();
        long txtHash = hash(txt, M);
        if (txtHash == patHash) return 0;
        for (int i = M; i < N; i++) {
            txtHash = (txtHash + Q - RM * txt.charAt(i - M) % Q) % Q;
            txtHash = (txtHash * R + txt.charAt(i)) % Q;
            if (txtHash == patHash)
                if (check(i - M + 1)) return i - M + 1;
        }
        return N;
    }

    public static void main(String[] args) {
        String text = "abacadabrabracabracadabrabrabracad";
        String[] patterns = {"abracadabra", "rab", "bcara", "rabrabracad", "abacad"};
        for (String pat : patterns) {
            SubstringSearchRK RK = new SubstringSearchRK(pat);
            int offset = RK.search(text);
            StdOut.println("txt: " + text);
            StdOut.print("pat: ");
            for (int i = 0; i < offset; i++)
                StdOut.print(" ");
            StdOut.println(pat);
        }
    }
}
