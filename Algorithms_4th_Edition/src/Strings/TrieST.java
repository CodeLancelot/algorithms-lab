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

    private Node get(Node trie, String key, int d) {
        if (trie == null) return null;
        if (d == key.length()) return trie;
        char c = key.charAt(d);
        return get(trie.next[c], key, d + 1);
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

    private Node put(Node trie, String key, Value val, int d) {
        if (trie == null) trie = new Node();
        if (d == key.length()) {
            trie.val = val;
            n++;
        } else {
            char c = key.charAt(d);
            trie.next[c] = put(trie.next[c], key, val, d + 1);
        }
        return trie;
    }

    public void delete(String key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        root = delete(root, key, 0);
    }

    private Node delete(Node trie, String key, int d) {
        if (trie == null) return null;
        if (d == key.length()) {
            trie.val = null;
            n--;
        } else {
            char c = key.charAt(d);
            trie.next[c] = delete(trie.next[c], key, d + 1);
        }
        if (trie.val != null) return trie;
        for (int c = 0; c < R; c++) {
            if (trie.next[c] != null) return trie;
        }
        return null;
    }

    private void collect(Node trie, String prefix, Queue<String> keys) {
        if (trie == null) return;
        if (trie.val != null)
            keys.enqueue(prefix);
        for (char c = 0; c < R; c++)
            collect(trie.next[c], prefix + c, keys);
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

    private void collect(Node trie, String prefix, String pattern, Queue<String> keys) {
        if (trie == null) return;
        int d = prefix.length();
        if (d == pattern.length() && trie.val != null) keys.enqueue(prefix);
        else if (d < pattern.length()) {
            char ch = pattern.charAt(d);
            if (ch == '.') {
                for (char c = 0; c < R; c++)
                    collect(trie.next[c], prefix + c, pattern, keys);
            } else collect(trie.next[ch], prefix + ch, pattern, keys);
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
    private int longestPrefixOf(Node trie, String query, int d, int length) {
        if (trie == null) return length;
        if (trie.val != null) length = d;
        if (d == query.length()) return length;
        char c = query.charAt(d);
        return longestPrefixOf(trie.next[c], query, d + 1, length);
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

            StdOut.println("longestPrefixOf(\"shake\"):");
            StdOut.println(st.longestPrefixOf("shake"));
            StdOut.println();

            StdOut.println("keysWithPrefix(\"sh\"):");
            for (String s : st.keysWithPrefix("sh"))
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
