package Strings;

import Fundamentals.Queue;
import libraries.In;
import libraries.StdOut;

import java.net.URL;

public class TernarySearchTries<Value> {
    private int n;
    private Node<Value> root;

    private static class Node<Value> {
        private char c;
        private Node<Value> left, mid, right;
        private Value val;
    }

    public TernarySearchTries() {
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Value get(String key) {
        if (key == null) throw new IllegalArgumentException("calls get() with null key");
        if (key.length() == 0) throw new IllegalArgumentException("key must have length >= 1");
        Node<Value> x = get(root, key, 0);
        if (x == null) return null;
        return x.val;
    }

    private Node<Value> get(Node<Value> trie, String key, int d) {
        if (trie == null) return null;
        if (key.length() == 0) return trie;
        char c = key.charAt(d);
        if (c < trie.c) return get(trie.left, key, d);
        else if (c > trie.c) return get(trie.right, key, d);
        else if (d < key.length() - 1) return get(trie.mid, key, d + 1);
        else return trie;
    }

    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("calls contains() with null key");
        return get(key) != null;
    }

    public void put(String key, Value val) {
        if (key == null) throw new IllegalArgumentException("calls put() with null key");
        if (val == null) throw new IllegalArgumentException("calls put() with null val");
        root = put(root, key, val, 0);
    }

    private Node<Value> put(Node<Value> trie, String key, Value val, int d) {
        char c = key.charAt(d);
        if (trie == null) {
            trie = new Node<>();
            trie.c = c;
        }
        if (c < trie.c) trie.left = put(trie.left, key, val, d);
        else if (c > trie.c) trie.right = put(trie.right, key, val, d);
        else if (d < key.length() - 1) trie.mid = put(trie.mid, key, val, d + 1);
        else {
            trie.val = val;
            n++;
        }
        return trie;
    }

    public void delete(String key) {
        if (key == null) throw new IllegalArgumentException("calls delete() with null key");
        if (!contains(key)) throw new IllegalArgumentException("calls delete() with nonexistent key ");
        root = delete(root, key, 0);
    }

    private Node<Value> delete(Node<Value> trie, String key, int d) {
        if (trie == null) return null;
        if (d == key.length() - 1) {
            trie.val = null;
            n--;
        } else {
            char c = key.charAt(d);
            if (c < trie.c) trie.left = delete(trie.left, key, d);
            else if (c > trie.c) trie.right = delete(trie.right, key, d);
            else trie.mid = delete(trie.mid, key, d + 1);
        }
        if (trie.left == null && trie.mid == null && trie.right == null) return null;
        return trie;
    }

    private void collect(Node<Value> trie, String prefix, Queue<String> keys) {
        if (trie == null) return;
        collect(trie.left, prefix, keys);
        if (trie.val != null) keys.enqueue(prefix + trie.c);
        collect(trie.mid, prefix + trie.c, keys);
        collect(trie.right, prefix, keys);
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        if (prefix == null) throw new IllegalArgumentException("calls keysWithPrefix() with null argument");
        Queue<String> keys = new Queue<>();
        // use get() to get the node corresponding to the last char of the prefix
        Node<Value> trie = get(root, prefix, 0);
        if (trie == null) return keys;
        if (trie.val != null) keys.enqueue(prefix);
        collect(trie.mid, prefix, keys);
        return keys;
    }

    public Iterable<String> keys() {
        Queue<String> keys = new Queue<>();
        collect(root, "", keys);
        return keys;
    }

    private void collect(Node<Value> trie, String prefix, String pattern, Queue<String> keys) {
        if (trie == null) return;
        int d = prefix.length();
        char c = pattern.charAt(d);
        if (c == '.' || c < trie.c) collect(trie.left, prefix, pattern, keys);
        if (c == '.' || c == trie.c) {
            if (d == pattern.length() - 1 && trie.val != null) {

                keys.enqueue(prefix + trie.c);
            } else if (d < pattern.length() - 1) collect(trie.mid, prefix + trie.c, pattern, keys);
        }
        if (c == '.' || c > trie.c) collect(trie.right, prefix, pattern, keys);
    }

    public Iterable<String> keysThatMatch(String pattern) {
        if (pattern == null) throw new IllegalArgumentException("calls keysThatMatch() with null argument");
        Queue<String> keys = new Queue<>();
        collect(root, "", pattern, keys);
        return keys;
    }

    public String longestPrefixOf(String query) {
        if (query == null) throw new IllegalArgumentException("calls longestPrefixOf() with null argument");
        int length = longestPrefixOf(root, query, 0, -1);
        if (length == -1) return null;
        else return query.substring(0, length);
    }

    private int longestPrefixOf(Node<Value> trie, String query, int d, int length) {
        if (trie == null) return length;
        char c = query.charAt(d);
        if (c < trie.c) return longestPrefixOf(trie.left, query, d, length);
        else if (c > trie.c) return longestPrefixOf(trie.right, query, d, length);
        else if (d < query.length() - 1) {
            if (trie.val != null) length = d + 1;
            return longestPrefixOf(trie.mid, query, d + 1, length);
        } else return query.length();
    }

    public static void main(String[] args) {
        try {
            URL words = new URL("https://algs4.cs.princeton.edu/52trie/shellsST.txt");
            In in = new In(words);
            String[] a = in.readAllStrings();
            TernarySearchTries<Integer> st = new TernarySearchTries<>();
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

            StdOut.println("keysThatMatch(\".he\"):");
            for (String s : st.keysThatMatch(".he"))
                StdOut.println(s);

            StdOut.println("delete(\"she\"):");
            st.delete("she");
            for (String key : st.keys())
                StdOut.println(key + " " + st.get(key));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
