package Context;

import Strings.ThreeWayQuick;
import libraries.StdIn;
import libraries.StdOut;

public class SuffixArray {
    private final String[] suffixes;
    private final int N;

    public SuffixArray(String text) {
        N = text.length();
        suffixes = new String[N];
        for (int i = 0; i < N; i++)
            suffixes[i] = text.substring(i);
        ThreeWayQuick.sort(suffixes);
    }

    public int length() {
        return N;
    }

    // returns the ith smallest suffix as a string
    public String select(int i) {
        if (i < 0 || i >= N) throw new IllegalArgumentException();
        return suffixes[i];
    }

    // returns the original index of the ith smallest suffix.
    public int index(int i) {
        if (i < 0 || i >= N) throw new IllegalArgumentException();
        return N - suffixes[i].length();
    }

    // returns the number of suffixes strictly less than the key
    public int rank(String key) {
        int lo = 0, hi = N - 1;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            int cmp = key.compareTo(suffixes[mid]);
            if (cmp < 0) hi = mid - 1;
            else if (cmp > 0) lo = mid + 1;
            else return mid;
        }
        return lo;
    }

    public int suffixLCP(int i) {
        if (i < 1 || i >= N) throw new IllegalArgumentException();
        return LCP(suffixes[i], suffixes[i - 1]);
    }

    // returns the length of the longest common prefix
    public static int LCP(String s, String t) {
        int min = Math.min(s.length(), t.length());
        int i = 0;
        while (i < min && s.charAt(i) == t.charAt(i)) i++;
        return i;
    }

    public static void main(String[] args) {
        String text = StdIn.readAll().replaceAll("\\s+", " ").trim();
        SuffixArray sa = new SuffixArray(text);

        StdOut.println("  i ind lcp rnk select");
        StdOut.println("---------------------------");

        for (int i = 0; i < sa.length(); i++) {
            int index = sa.index(i);
            String ith = "\"" + text.substring(index, Math.min(index + 50, text.length())) + "\"";
            assert text.substring(index).equals(sa.select(i));
            int rank = sa.rank(text.substring(index));
            if (i == 0) {
                StdOut.printf("%3d %3d %3s %3d %s\n", i, index, "-", rank, ith);
            } else {
                StdOut.printf("%3d %3d %3d %3d %s\n", i, index, sa.suffixLCP(i), rank, ith);
            }
        }
    }
}
