package Strings.Compression;

import libraries.BinaryStdIn;
import libraries.BinaryStdOut;

// Compress or expand binary input from standard input using run-length encoding
public class RunLength {
    private static final int R = 256;
    private static final int LG_R = 8;

    public static void compress() {
        int len = 0;
        boolean oldBit = false;
        while (!BinaryStdIn.isEmpty()) {
            boolean bit = BinaryStdIn.readBoolean();
            if (bit != oldBit) {
                BinaryStdOut.write(len, LG_R);
                len = 1;
                oldBit = !oldBit;
            } else {
                if (len == R - 1) {
                    BinaryStdOut.write(len, LG_R);
                    len = 0;
                    BinaryStdOut.write(len, LG_R);
                }
                len++;
            }
        }
        BinaryStdOut.write(len, LG_R);
        BinaryStdOut.close();
    }

    public static void expand() {
        boolean bit = false;
        while (!BinaryStdIn.isEmpty()) {
            int len = BinaryStdIn.readInt(LG_R);
            if (len == 0) continue;
            for (int i = 0; i < len; i++) {
                BinaryStdOut.write(bit);
            }
            bit = !bit;
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
    }
}
