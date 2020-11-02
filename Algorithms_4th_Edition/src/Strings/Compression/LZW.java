package Strings.Compression;

import Strings.TrieST;
import libraries.BinaryStdIn;
import libraries.BinaryStdOut;

// Providing static methods for compressing and expanding a binary input using LZW compression over the 8-bit extended ASCII alphabet with 12-bit codewords.
public class LZW {
    private static final int R = 256;
    private static final int CODE_WIDTH = 12;
    private static final int CODE_TOTAL = 4096; //2^CODE_WIDTh

    public static void compress() {
        String input = BinaryStdIn.readString();
        TrieST<Integer> st = new TrieST<>();

        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R + 1; // R is codeword for EOF.
        while (input.length() > 0) {
            String lpStr = st.longestPrefixOf(input);
            BinaryStdOut.write(st.get(lpStr), CODE_WIDTH);
            int len = lpStr.length();
            if (len < input.length() && code < CODE_TOTAL)
                st.put(input.substring(0, len + 1), code++);
            input = input.substring(len);
        }
        BinaryStdOut.write(R, CODE_WIDTH);
        BinaryStdOut.close();
    }

    public static void expand() {
        String[] st = new String[CODE_TOTAL];
        int i; // codeword needed to assign
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = ""; // (unused) lookahead for EOF
        int codeword = BinaryStdIn.readInt(CODE_WIDTH);
        if (codeword == R) return;

        String val = st[codeword];
        while (true) {
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(CODE_WIDTH);
            if (codeword == R) break;
            String nextVal = st[codeword];
            if (i == codeword)
                nextVal = val + val.charAt(0);
            if (i < CODE_TOTAL)
                st[i++] = val + nextVal.charAt(0);
            val = nextVal;
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
    }
}
