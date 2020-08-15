package Strings;

import Fundamentals.Queue;
import libraries.In;
import libraries.StdOut;

import java.net.URL;

public class TrieST<Value> {
    private static final int R = 256; // extended ASCII

    private Node root;
    private int n; // number of keys in trie

    // R-way trie node
    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
    }

    public TrieST() {
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Value get(String key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }

    private Node get(Node treeRoot, String key, int d) {
        if (treeRoot == null) return null;
        if (d == key.length()) return treeRoot;
        char c = key.charAt(d);
        return get(treeRoot.next[c], key, d + 1);
    }

    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    public void put(String key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) throw new IllegalArgumentException("second argument to put() is null");
        root = put(root, key, val, 0);
    }

    private Node put(Node treeRoot, String key, Value val, int d) {
        if (treeRoot == null) treeRoot = new Node();
        if (d == key.length()) {
            treeRoot.val = val;
            n++;
        } else {
            char c = key.charAt(d);
            treeRoot.next[c] = put(treeRoot.next[c], key, val, d + 1);
        }
        return treeRoot;
    }

    public void delete(String key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        root = delete(root, key, 0);
    }

    private Node delete(Node treeRoot, String key, int d) {
        if (treeRoot == null) return null;
        if (d == key.length()) {
            treeRoot.val = null;
            n--;
        } else {
            char c = key.charAt(d);
            treeRoot.next[c] = delete(treeRoot.next[c], key, d + 1);
        }
        if (treeRoot.val != null) return treeRoot;
        for (int c = 0; c < R; c++) {
            if (treeRoot.next[c] != null) return treeRoot;
        }
        return null;
    }

    private void collect(Node treeRoot, String prefix, Queue<String> keys) {
        if (treeRoot == null) return;
        if (treeRoot.val != null)
            keys.enqueue(prefix);
        for (char c = 0; c < R; c++)
            collect(treeRoot.next[c], prefix + c, keys);
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> keys = new Queue<>();
        // use get() to get the node corresponding to the last char of the prefix
        collect(get(root, prefix, 0), prefix, keys);
        return keys;
    }

    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    private void collect(Node treeRoot, String prefix, String pattern, Queue<String> keys) {
        if (treeRoot == null) return;
        int d = prefix.length();
        if (d == pattern.length() && treeRoot.val != null) keys.enqueue(prefix);
        else if (d < pattern.length()) {
            char ch = pattern.charAt(d);
            if (ch == '.') {
                for (char c = 0; c < R; c++)
                    collect(treeRoot.next[c], prefix + c, pattern, keys);
            } else collect(treeRoot.next[ch], prefix + ch, pattern, keys);
        }
    }

    public Iterable<String> keysThatMatch(String pattern) {
        Queue<String> keys = new Queue<>();
        collect(root, "", pattern, keys);
        return keys;
    }

    public String longestPrefixOf(String query) {
        if (query == null) throw new IllegalArgumentException("argument to longestPrefixOf() is null");
        int length = longestPrefixOf(root, query, 0, -1);
        if (length == -1) return null;
        else return query.substring(0, length);
    }

    // assuming the first d character match and we have already found a prefix match of given length (-1 if no such match)
    private int longestPrefixOf(Node treeRoot, String query, int d, int length) {
        if (treeRoot == null) return length;
        if (treeRoot.val != null) length = d;
        if (d == query.length()) return length;
        char c = query.charAt(d);
        return longestPrefixOf(treeRoot.next[c], query, d + 1, length);
    }

    public static void main(String[] args) {
        try {
            URL words = new URL("https://algs4.cs.princeton.edu/52trie/shellsST.txt");
            In in = new In(words);
            String[] a = in.readAllStrings();
            TrieST<Integer> st = new TrieST<>();
            for (int i = 0; i < a.length; i++)
                st.put(a[i], i);

            StdOut.println("keys():");
            for (String key : st.keys())
                StdOut.println(key + " " + st.get(key));
            StdOut.println();

            StdOut.println("longestPrefixOf(\"shellsort\"):");
            StdOut.println(st.longestPrefixOf("shellsort"));
            StdOut.println();

            StdOut.println("longestPrefixOf(\"quicksort\"):");
            StdOut.println(st.longestPrefixOf("quicksort"));
            StdOut.println();

            StdOut.println("keysWithPrefix(\"shor\"):");
            for (String s : st.keysWithPrefix("shor"))
                StdOut.println(s);
            StdOut.println();

            StdOut.println("keysThatMatch(\".he.l.\"):");
            for (String s : st.keysThatMatch(".he.l."))
                StdOut.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
