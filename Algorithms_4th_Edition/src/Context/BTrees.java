package Context;

import libraries.StdOut;

public class BTrees<Key extends Comparable<Key>, Value> {
    private static final int M = 6; // must be even and greater than 2
    private Node root;
    private int height;
    private int n;

    private static final class Node {
        private int m;  // number of children
        private Entry[] children = new Entry[M];

        // create a node with k children
        private Node(int k) {
            m = k;
        }
    }

    // internal nodes: only use key and next
    // external nodes: only use key and value
    private static class Entry {
        private Comparable key;
        private Object val;
        private Node next;     // helper field to iterate over array entries

        public Entry(Comparable key, Object val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    public BTrees() {
        root = new Node(0);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return n;
    }

    public int height() {
        return height;
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        return search(root, key, height);
    }

    private Value search(Node tree, Key key, int height) {
        Entry[] entries = tree.children;
        if (height == 0) {
            for (int i = 0; i < tree.m; i++) {
                if (eq(key, entries[i].key))
                    return (Value) entries[i].val;
            }
        } else {
            for (int i = 0; i < tree.m; i++) {
                if (i + 1 == tree.m || less(key, entries[i + 1].key))
                    return search(entries[i].next, key, height - 1);
            }
        }
        return null;
    }

    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("argument key to put() is null");
        Node newTreeSplit = insert(root, key, val, height);
        n++;

        if (newTreeSplit == null) return;

        Node newRoot = new Node(2);
        newRoot.children[0] = new Entry(root.children[0].key, null, root);
        newRoot.children[1] = new Entry(newTreeSplit.children[0].key, null, newTreeSplit);
        root = newRoot;
        height++;
    }

    private Node insert(Node tree, Key key, Value val, int height) {
        int index;
        Entry newEntry = new Entry(key, val, null);
        if (height == 0) {
            for (index = 0; index < tree.m; index++)
                if (less(key, tree.children[index].key)) break;
        } else {
            for (index = 0; index < tree.m; index++) {
                if (index + 1 == tree.m || less(key, tree.children[index + 1].key)) {
                    Node newTreeSplit = insert(tree.children[index].next, key, val, height - 1);
                    if (newTreeSplit == null) return null;
                    index++;
                    newEntry.key = newTreeSplit.children[0].key;
                    newEntry.val = null;
                    newEntry.next = newTreeSplit;
                    break;
                }
            }
        }

        for (int i = tree.m; i > index; i--)
            tree.children[i] = tree.children[i - 1];
        tree.children[index] = newEntry;
        tree.m++;
        if (tree.m < M) return null;
        else return split(tree);
    }

    private Node split(Node oldTree) {
        Node newTree = new Node(M / 2);
        oldTree.m = M / 2;
        for (int i = 0; i < M / 2; i++)
            newTree.children[i] = oldTree.children[i + M / 2];
        return newTree;
    }

    public String toString() {
        return toString(root, height, "") + "\n";
    }

    private String toString(Node h, int ht, String indent) {
        StringBuilder s = new StringBuilder();
        Entry[] children = h.children;

        if (ht == 0) {
            for (int j = 0; j < h.m; j++) {
                s.append(indent + children[j].key + " " + children[j].val + "\n");
            }
        } else {
            for (int j = 0; j < h.m; j++) {
                if (j > 0) s.append(indent + "(" + children[j].key + ")\n");
                s.append(toString(children[j].next, ht - 1, indent + "     "));
            }
        }
        return s.toString();
    }


    // comparison functions - make Comparable instead of Key to avoid casts
    private boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }

    private boolean eq(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }

    public static void main(String[] args) {
        BTrees<String, String> st = new BTrees<>();

        st.put("www.cs.princeton.edu", "128.112.136.12");
        st.put("www.cs.princeton.edu", "128.112.136.11");
        st.put("www.princeton.edu", "128.112.128.15");
        st.put("www.apple.com", "17.112.152.32");
        st.put("www.amazon.com", "207.171.182.16");
        st.put("www.ebay.com", "66.135.192.87");
        st.put("www.google.com", "216.239.41.99");
        st.put("www.microsoft.com", "207.126.99.140");
        st.put("www.dell.com", "143.166.224.230");
        st.put("www.espn.com", "199.181.135.201");
        st.put("www.weather.com", "63.111.66.11");
        st.put("www.yahoo.com", "216.109.118.65");

        StdOut.println("cs.princeton.edu:  " + st.get("www.cs.princeton.edu"));
        StdOut.println("apple.com:         " + st.get("www.apple.com"));
        StdOut.println("google.com:        " + st.get("www.google.com"));
        StdOut.println("microsoft.com:     " + st.get("www.microsoft.com"));
        StdOut.println("dell.com:          " + st.get("www.dell.com"));
        StdOut.println();
        StdOut.println("size:    " + st.size());
        StdOut.println("height:  " + st.height());
        StdOut.println(st);
    }
}
