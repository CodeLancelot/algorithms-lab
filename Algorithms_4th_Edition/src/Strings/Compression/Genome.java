package Strings.Compression;

import Strings.Alphabet;
import libraries.BinaryIn;
import libraries.BinaryStdIn;
import libraries.BinaryStdOut;

import java.net.URL;

// Providing static methods for compressing and expanding a genomic sequence using a 2-bit code.
public class Genome {

    // Do not instantiate.
    private Genome() {
    }

    public static void compress(String originDNA) {
        Alphabet DNA = Alphabet.DNA;
        int len = originDNA.length();
        BinaryStdOut.write(len);
        for (int i = 0; i < len; i++) {
            char gene = originDNA.charAt(i);
            BinaryStdOut.write(DNA.toIndex(gene), DNA.lgR());
        }
        BinaryStdOut.close();
    }

    public static void expand() {
        Alphabet DNA = Alphabet.DNA;
        int len = BinaryStdIn.readInt();
        for (int i = 0; i < len; i++) {
            int index = BinaryStdIn.readInt(DNA.lgR());
            BinaryStdOut.write(DNA.toChar(index));
        }
        BinaryStdOut.close();
    }


    public static void main(String[] args) {
        try {
            if (args[0].equals("-")) {
                URL txtURL = new URL("https://algs4.cs.princeton.edu/55compression/genomeTiny.txt");
                BinaryIn binaryIn = new BinaryIn(txtURL);

                compress(binaryIn.readString());
            } else if (args[0].equals("+")) expand();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}