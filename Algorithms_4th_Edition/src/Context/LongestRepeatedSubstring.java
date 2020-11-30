package Context;

import libraries.StdIn;
import libraries.StdOut;

public class LongestRepeatedSubstring {
    private LongestRepeatedSubstring() {
    }

    public static String LRS(String text) {
        SuffixArray sa = new SuffixArray(text);
        int n = text.length();
        String str = "";
        for (int i = 1; i < n; i++) {
            int len = sa.suffixLCP(i);
            if (len > str.length()) {
                str = sa.select(i).substring(0, len);
            }
        }
        return str;
    }

    public static void main(String[] args) {
        String text = StdIn.readAll().replaceAll("\\s+", " ");
        StdOut.println("'" + LRS(text) + "'");
    }
}
