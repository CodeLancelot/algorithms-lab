package Context;

import libraries.StdOut;

public class LongestCommonSubstring {
    private LongestCommonSubstring() {
    }

    // return the longest common prefix of suffix s[p..] and suffix t[q..]
    private static String LCP(String s, int p, String t, int q) {
        int n = Math.min(s.length() - p, t.length() - q);
        for (int i = 0; i < n; i++) {
            if (s.charAt(p + i) != t.charAt(q + i))
                return s.substring(p, p + i);
        }
        return s.substring(p, p + n);
    }

    // compare suffix s[p..] and suffix t[q..]
    private static int compare(String s, int p, String t, int q) {
        int len1 = s.length() - p;
        int len2 = t.length() - q;
        int n = Math.min(len1, len2);
        for (int i = 0; i < n; i++) {
            if (s.charAt(p + i) != t.charAt(q + i))
                return s.charAt(p + i) - t.charAt(q + i);
        }
        return Integer.compare(len1, len2);
    }

    public static String LCS(String s, String t) {
        SuffixArray SA1 = new SuffixArray(s);
        SuffixArray SA2 = new SuffixArray(t);

        // find longest common substring by "merging" sorted suffixes
        String lcs = "";
        int i = 0, j = 0;
        while (i < s.length() && j < t.length()) {
            int p = SA1.index(i);
            int q = SA2.index(j);
            String str = LCP(s, p, t, q);
            if (str.length() > lcs.length()) lcs = str;
            if (compare(s, p, t, q) < 0) i++;
            else j++;
        }

        return lcs;
    }

    public static void main(String[] args) {
        String txt1 = "start with Longest Common Substring";
        String txt2 = "and end of Longest Common Prefix";
        StdOut.println("'" + LCS(txt1, txt2) + "'");
    }
}
