package Strings.Compression;

import Sorting.MinPQ;
import libraries.BinaryStdIn;
import libraries.BinaryStdOut;

// Providing static methods for compressing and expanding a binary input using Huffman codes over the 8-bit extended ASCII alphabet.
public class Huffman {
    private static int R = 256;

    // Do not instantiate.
    private Huffman() {
    }

    // Huffman trie node
    private static class Node implements Comparable<Node> {
        private final char ch;
        private final int freq;
        private final Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        private boolean isLeaf() {
            return (left == null) && (right == null);
        }

        // compare, based on frequency
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    public static void compress() {
        char[] chars = (BinaryStdIn.readString()).toCharArray();
        int[] freq = new int[R];
        for (int i = 0; i < chars.length; i++)
            freq[chars[i]]++;

        // build Huffman trie
        Node root = buildTrie(freq);
        // build code table
        String[] st = buildCode(root);
        // Print trie for decoder (recursive).
        writeTrie(root);
        // Print number of chars.
        BinaryStdOut.write(chars.length);
        for (char ch : chars) {
            String code = st[ch];
            for (int i = 0; i < code.length(); i++) {
                if (code.charAt(i) == '0')
                    BinaryStdOut.write(false);
                else BinaryStdOut.write(true);
            }
        }
        BinaryStdOut.close();
    }

    // build the Huffman trie given frequencies
    private static Node buildTrie(int[] freq) {
        // initialize priority queue with singleton trees
        MinPQ<Node> pq = new MinPQ<>();
        for (char c = 0; c < R; c++) {
            if (freq[c] > 0)
                pq.insert(new Node(c, freq[c], null, null));
        }
        // merge two smallest trees
        while (pq.size() > 1) {
            Node left = pq.delMin();
            Node right = pq.delMin();
            pq.insert(new Node('\0', left.freq + right.freq, left, right));
        }
        return pq.delMin();
    }

    private static String[] buildCode(Node root) {
        String[] st = new String[R];
        buildCode(st, root, "");
        return st;
    }

    private static void buildCode(String[] st, Node trie, String code) {
        if (trie.isLeaf()) st[trie.ch] = code;
        else {
            buildCode(st, trie.left, code + '0');
            buildCode(st, trie.right, code + '1');
        }
    }

    private static void writeTrie(Node trie) {
        if (trie.isLeaf()) {
            BinaryStdOut.write(true);
            BinaryStdOut.write(trie.ch);
            return;
        }
        BinaryStdOut.write(false);
        writeTrie(trie.left);
        writeTrie(trie.right);
    }

    private static void expand() {
        Node root = readTrie();
        int len = BinaryStdIn.readInt();
        for (int i = 0; i < len; i++) {
            Node trie = root;
            while (!trie.isLeaf()) {
                if (BinaryStdIn.readBoolean())
                    trie = trie.right;
                else trie = trie.left;
            }
            BinaryStdOut.write(trie.ch);
        }
        BinaryStdOut.close();
    }

    private static Node readTrie() {
        if (BinaryStdIn.readBoolean())
            return new Node(BinaryStdIn.readChar(), 0, null, null);
        return new Node('\0', 0, readTrie(), readTrie());
    }

    public static void main(String[] args) {
        if (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
    }
}
