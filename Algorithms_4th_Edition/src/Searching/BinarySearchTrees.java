package Searching;

import java.util.NoSuchElementException;

import libraries.*;
import Fundamentals.Queue;

public class BinarySearchTrees<Key extends Comparable<Key>, Value> {
    private Node root;

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int size;

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    public BinarySearchTrees() {
    }

    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) return 0;
        else return node.size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        return get(root, key);
    }

    private Value get(Node treeRoot, Key key) {
        if (treeRoot == null) return null;
        int cmp = key.compareTo(treeRoot.key);
        if (cmp < 0) return get(treeRoot.left, key);
        else if (cmp > 0) return get(treeRoot.right, key);
        else return treeRoot.val;
    }

    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        if (val == null) delete(key);
        root = put(root, key, val);
    }

    private Node put(Node treeRoot, Key key, Value val) {
        if (treeRoot == null) return new Node(key, val, 1);
        int cmp = key.compareTo(treeRoot.key);
        if (cmp < 0) treeRoot.left = put(treeRoot.left, key, val);
        else if (cmp > 0) treeRoot.right = put(treeRoot.right, key, val);
        else treeRoot.val = val;
        treeRoot.size = size(treeRoot.left) + size(treeRoot.right) + 1;
        return treeRoot;
    }

    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("calls min() with empty symbol table");
        return min(root).key;
    }

    private Node min(Node treeRoot) {
        if (treeRoot.left == null) return treeRoot;
        else return min(treeRoot.left);
    }

    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("calls max() with empty symbol table");
        return max(root).key;
    }

    private Node max(Node treeRoot) {
        if (treeRoot.right == null) return treeRoot;
        else return max(treeRoot.right);
    }

    public Key floor(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        if (isEmpty()) throw new NoSuchElementException("calls floor() with empty symbol table");
        Node x = floor(root, key);
        if (x == null) throw new NoSuchElementException("argument to floor() is too small");
        else return x.key;
    }

    private Node floor(Node treeRoot, Key key) {
        if (treeRoot == null) return null;
        int cmp = key.compareTo(treeRoot.key);
        if (cmp < 0) return floor(treeRoot.left, key);
        else if (cmp > 0) {
            Node x = floor(treeRoot.right, key);
            if (x != null) return x;
            else return treeRoot;
        } else return treeRoot;
    }

    public Key ceiling(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to ceiling() is null");
        if (isEmpty()) throw new NoSuchElementException("calls ceiling() with empty symbol table");
        Node x = ceiling(root, key);
        if (x == null) throw new NoSuchElementException("argument to ceiling() is too large");
        else return x.key;
    }

    private Node ceiling(Node treeRoot, Key key) {
        if (treeRoot == null) return null;
        int cmp = key.compareTo(treeRoot.key);
        if (cmp < 0) {
            Node x = ceiling(treeRoot.left, key);
            if (x != null) return x;
            else return treeRoot;
        } else if (cmp > 0) return ceiling(treeRoot.right, key);
        else return treeRoot;
    }

    public Key select(int rank) {
        if (rank < 0 || rank >= size()) {
            throw new IllegalArgumentException("argument to select() is invalid: " + rank);
        }
        return select(root, rank);
    }

    private Key select(Node treeRoot, int rank) {
        if (treeRoot == null) return null;
        int leftSize = size(treeRoot.left);
        if (leftSize < rank) return select(treeRoot.right, rank - leftSize - 1);
        else if (leftSize > rank) return select(treeRoot.left, rank);
        else return treeRoot.key;
    }

    // Return the number of keys in the symbol table strictly less than {@code key}.
    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
        return rank(root, key);
    }

    private int rank(Node treeRoot, Key key) {
        if (treeRoot == null) return 0;
        int cmp = key.compareTo(treeRoot.key);
        if (cmp < 0) return rank(treeRoot.left, key);
        else if (cmp > 0) return size(treeRoot.left) + 1 + rank(treeRoot.right, key);
        else return size(treeRoot.left);
    }

    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
    }

    private Node deleteMin(Node treeRoot) {
        if (treeRoot.left == null) return treeRoot.right;
        treeRoot.left = deleteMin(treeRoot.left);
        treeRoot.size = size(treeRoot.left) + size(treeRoot.right) + 1;
        return treeRoot;
    }

    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMax(root);
    }

    private Node deleteMax(Node treeRoot) {
        if (treeRoot.right == null) return treeRoot.left;
        treeRoot.right = deleteMax(treeRoot.right);
        treeRoot.size = size(treeRoot.left) + size(treeRoot.right) + 1;
        return treeRoot;
    }

    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("calls delete() with a null key");
        root = delete(root, key);
    }

    private Node delete(Node treeRoot, Key key) {
        if (treeRoot == null) return null;
        int cmp = key.compareTo(treeRoot.key);
        if (cmp < 0) treeRoot.left = delete(treeRoot.left, key);
        else if (cmp > 0) treeRoot.right = delete(treeRoot.right, key);
        else {
            if (treeRoot.left == null) return treeRoot.right;
            if (treeRoot.right == null) return treeRoot.left;
            Node x = treeRoot;
            treeRoot = min(x.right); //replace with its successor node
            treeRoot.right = deleteMin(x.right);
            treeRoot.left = x.left;
        }
        treeRoot.size = size(treeRoot.left) + size(treeRoot.right) + 1;
        return treeRoot;
    }

    public Iterable<Key> keys() {
        if (isEmpty()) return new Queue<Key>();
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node treeRoot, Queue<Key> queue, Key lo, Key hi) {
        if (treeRoot == null) return;
        int loCmp = lo.compareTo(treeRoot.key);
        int hiCmp = hi.compareTo(treeRoot.key);
        if (loCmp < 0) keys(treeRoot.left, queue, lo, hi);
        if (loCmp <= 0 && hiCmp >= 0) queue.enqueue(treeRoot.key);
        if (hiCmp > 0) keys(treeRoot.right, queue, lo, hi);
    }

    public int size(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }

    public int height() {
        return height(root);
    }

    private int height(Node treeRoot) {
        if (treeRoot == null) return -1;
        return 1 + Math.max(height(treeRoot.left), height(treeRoot.right));
    }

    // Returns the keys in the BST in level order (for debugging).
    public Iterable<Key> levelOrder() {
        Queue<Key> keys = new Queue<Key>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) continue;
            keys.enqueue(x.key);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }
        return keys;
    }

    // are the size fields correct?
    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    private boolean isSizeConsistent(Node treeRoot) {
        if (treeRoot == null) return true;
        if (treeRoot.size != size(treeRoot.left) + size(treeRoot.right) + 1) return false;
        return isSizeConsistent(treeRoot.left) && isSizeConsistent(treeRoot.right);
    }

    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (Key key : keys())
            if (key.compareTo(select(rank(key))) != 0) return false;
        return true;
    }

    public static void main(String[] args) {
        BinarySearchTrees<String, Integer> st = new BinarySearchTrees<>();
        String line = "S E A R C H E X A M P L E";
        String[] arr = line.split("\\s+");
        for (int i = 0; i < arr.length; i++) {
            st.put(arr[i], i);
        }

        for (String s : st.levelOrder())
            StdOut.println(s + " " + st.get(s));

        StdOut.println();

        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}
