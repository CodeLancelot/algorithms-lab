package Searching;

import java.util.NoSuchElementException;

import libraries.*;
import Fundamentals.Queue;

public class RedBlackBSTrees<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private boolean color;
        private int size;

        public Node(Key key, Value val, boolean color, int size) {
            this.key = key;
            this.val = val;
            this.color = color;
            this.size = size;
        }
    }

    public RedBlackBSTrees() {
    }

    private boolean isRed(Node treeRoot) {
        if (treeRoot == null) return false;
        return treeRoot.color == RED;
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
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
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
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
        root.color = BLACK;
    }

    private Node put(Node treeRoot, Key key, Value val) {
        if (treeRoot == null) return new Node(key, val, RED, 1);
        int cmp = key.compareTo(treeRoot.key);
        if (cmp < 0) treeRoot.left = put(treeRoot.left, key, val);
        else if (cmp > 0) treeRoot.right = put(treeRoot.right, key, val);
        else treeRoot.val = val;

        if (!isRed(treeRoot.left) && isRed(treeRoot.right)) treeRoot = rotateLeft(treeRoot);
        if (isRed(treeRoot.left) && isRed(treeRoot.left.left)) treeRoot = rotateRight(treeRoot);
        if (isRed(treeRoot.left) && isRed(treeRoot.right)) flipColors(treeRoot);
        treeRoot.size = size(treeRoot.left) + 1 + size(treeRoot.right);
        return treeRoot;
    }

    // make a right-leaning link lean to the left
    private Node rotateLeft(Node oldRoot) {
        // assert (oldRoot != null) && isRed(oldRoot.right);
        Node newRoot = oldRoot.right;
        oldRoot.right = newRoot.left;
        newRoot.left = oldRoot;
        newRoot.size = oldRoot.size;
        oldRoot.size = size(oldRoot.left) + 1 + size(oldRoot.right);
        newRoot.color = oldRoot.color;
        oldRoot.color = RED;
        return newRoot;
    }

    // make a left-leaning link lean to the right
    private Node rotateRight(Node oldRoot) {
        // assert (oldRoot != null) && isRed(oldRoot.left);
        Node newRoot = oldRoot.left;
        oldRoot.left = newRoot.right;
        newRoot.right = oldRoot;
        newRoot.size = oldRoot.size;
        oldRoot.size = size(oldRoot.left) + 1 + size(oldRoot.right);
        newRoot.color = oldRoot.color;
        oldRoot.color = RED;
        return newRoot;
    }

    private void flipColors(Node h) {
        // h must have opposite color of its two children
        // assert (h != null) && (h.left != null) && (h.right != null);
        // assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right)) || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    private Node moveRedLeft(Node treeRoot) {
        // assert (treeRoot != null);
        // assert isRed(treeRoot) && !isRed(treeRoot.left) && !isRed(treeRoot.left.left);
        flipColors(treeRoot);
        if (isRed(treeRoot.right.left)) {
            treeRoot.right = rotateRight(treeRoot.right);
            treeRoot = rotateLeft(treeRoot);
            flipColors(treeRoot);
        }
        return treeRoot;
    }

    private Node moveRedRight(Node treeRoot) {
        flipColors(treeRoot);
        if (isRed(treeRoot.left.left)) {
            treeRoot = rotateRight(treeRoot);
            flipColors(treeRoot);
        }
        return treeRoot;
    }

    private Node balance(Node treeRoot) {
        if (!isRed(treeRoot.left) && isRed(treeRoot.right)) treeRoot = rotateLeft(treeRoot);
//        if (isRed(treeRoot.left) && isRed(treeRoot.left.left)) treeRoot = rotateRight(treeRoot);
        if (isRed(treeRoot.left) && isRed((treeRoot.right))) flipColors(treeRoot);
        treeRoot.size = size(treeRoot.left) + 1 + size(treeRoot.right);
        return treeRoot;
    }

    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("RedBlack balance search trees underflow");
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMin(Node treeRoot) {
        if (treeRoot.left == null) return null;
        if (!isRed(treeRoot.left) && !isRed(treeRoot.left.left)) treeRoot = moveRedLeft(treeRoot);
        treeRoot.left = deleteMin(treeRoot.left);
        return balance(treeRoot);
    }

    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("RedBlack balance search trees underflow");
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMax(Node treeRoot) {
        if (isRed(treeRoot.left)) treeRoot = rotateRight(treeRoot);
        if (treeRoot.right == null) return null;
        if (!isRed(treeRoot.right) && !isRed(treeRoot.right.left)) treeRoot = moveRedRight(treeRoot);
        treeRoot.right = deleteMax(treeRoot.right);
        return balance(treeRoot);
    }

    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return;

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;

        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
    }

    public Node delete(Node treeRoot, Key key) {
        int cmp = key.compareTo(treeRoot.key);
        if (cmp < 0) {
            if (!isRed(treeRoot.left) && !isRed(treeRoot.left.left)) treeRoot = moveRedLeft(treeRoot);
            treeRoot.left = delete(treeRoot.left, key);
        } else if (cmp > 0) {
            if (isRed(treeRoot.left)) treeRoot = rotateRight(treeRoot);
            if (!isRed(treeRoot.right) && !isRed(treeRoot.right.left)) treeRoot = moveRedRight(treeRoot);
            treeRoot.right = delete(treeRoot.right, key);
        } else {
            if (isRed(treeRoot.left)) treeRoot = rotateRight(treeRoot);
            if (treeRoot.right == null) return null;
            if (key.compareTo(treeRoot.key) == 0) {
                Node successor = min(treeRoot.right);
                treeRoot.key = successor.key;
                treeRoot.val = successor.val;
                deleteMin(treeRoot.right);
            } else treeRoot.right = delete(treeRoot.right, key);
        }
        return balance(treeRoot);
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

    /***************************************************************************
     *  Check integrity of red-black tree data structure.
     ***************************************************************************/
    private boolean check() {
        if (!isBST()) StdOut.println("Not in symmetric order");
        if (!isSizeConsistent()) StdOut.println("Subtree counts not consistent");
        if (!isRankConsistent()) StdOut.println("Ranks not consistent");
        if (!is23()) StdOut.println("Not a 2-3 tree");
        if (!isBalanced()) StdOut.println("Not balanced");
        return isBST() && isSizeConsistent() && isRankConsistent() && is23() && isBalanced();
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return isBST(root, null, null);
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    private boolean isBST(Node x, Key min, Key max) {
        if (x == null) return true;
        if (min != null && x.key.compareTo(min) <= 0) return false;
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    }

    // are the size fields correct?
    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }

    private boolean isSizeConsistent(Node x) {
        if (x == null) return true;
        if (x.size != size(x.left) + size(x.right) + 1) return false;
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    }

    // check that ranks are consistent
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++)
            if (i != rank(select(i))) return false;
        for (Key key : keys())
            if (key.compareTo(select(rank(key))) != 0) return false;
        return true;
    }

    // Does the tree have no red right links, and at most one (left)
    // red links in a row on any path?
    private boolean is23() {
        return is23(root);
    }

    private boolean is23(Node x) {
        if (x == null) return true;
        if (isRed(x.right)) return false;
        if (x != root && isRed(x) && isRed(x.left))
            return false;
        return is23(x.left) && is23(x.right);
    }

    // do all paths from root to leaf have same number of black edges?
    private boolean isBalanced() {
        int black = 0;     // number of black links on path from root to min
        Node x = root;
        while (x != null) {
            if (!isRed(x)) black++;
            x = x.left;
        }
        return isBalanced(root, black);
    }

    // does every path from the root to a leaf have the given number of black links?
    private boolean isBalanced(Node x, int black) {
        if (x == null) return black == 0;
        if (!isRed(x)) black--;
        return isBalanced(x.left, black) && isBalanced(x.right, black);
    }

    // Returns the keys in the BST in level order (for debugging).
    public Iterable<Key> levelOrder() {
        Queue<Key> keys = new Queue<>();
        Queue<Node> queue = new Queue<>();
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

    public static void main(String[] args) {
        RedBlackBSTrees<String, Integer> st = new RedBlackBSTrees<>();
        String line = "S E A R C H X M P L";
        String[] arr = line.split("\\s+");
        for (int i = 0; i < arr.length; i++) {
            st.put(arr[i], i);
        }
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
        StdOut.println();
        st.delete("E");
        for (String s : st.levelOrder())
            StdOut.println(s + " " + st.get(s));
        StdOut.println();
    }
}
