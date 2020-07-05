package chapter3.part3;

import chapter1.part3.Queue;
import chapter3.OrderedST;

public class RedBlackBST<Key extends Comparable<Key>, Value> implements OrderedST<Key, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        Key k;
        Value v;
        Node left, right;
        int n; // size of the subtree rooted at this Node
        boolean color; // the color of the link from this node to its parent

        public Node(Key k, Value v, int n, boolean color) {
            this.k = k;
            this.v = v;
            this.n = n;
            this.color = color;
        }
    }

    private Node root;

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.n;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public Value get(Key k) {
        return get(root, k);
    }

    private Value get(Node x, Key k) {
        if (x == null) return null;
        int cmp = k.compareTo(x.k);
        if (cmp == 0) {
            return x.v;
        } else if (cmp < 0) {
            return get(x.left, k);
        } else {
            return get(x.right, k);
        }
    }

    public void put(Key k, Value v) {
        root = put(root, k, v);
        root.color = BLACK;
    }

    private Node put(Node h, Key k, Value v) {
        if (h == null) return new Node(k, v, 1, RED);
        int cmp = k.compareTo(h.k);
        if (cmp == 0) h.v = v;
        else if (cmp < 0) h.left = put(h.left, k, v);
        else h.right = put(h.right, k, v);
        // tree transformation to maintain perfect black balance
        // and no 2 successive red links connected to any node
        return balance(h);
    }

    public Key min() {
        Node min = min(root);
        if (min == null) return null;
        return min.k;
    }

    private Node min(Node x) {
        if (x == null) return null;
        if (x.left == null) return x;
        return min(x.left);
    }

    public Key max() {
        Node max = max(root);
        if (max == null) return null;
        return max.k;
    }

    private Node max(Node x) {
        if (x == null) return null;
        if (x.right == null) return x;
        return max(x.right);
    }

    public Key floor(Key k) {
        Node floor = floor(root, k);
        if (floor == null) return null;
        return floor.k;
    }

    private Node floor(Node x, Key k) {
        if (x == null) return null;
        int cmp = k.compareTo(x.k);
        if (cmp == 0) return x;
        else if (cmp < 0) return floor(x.left, k);
        else {
            Node floor = floor(x.right, k);
            if (floor == null) return x;
            return floor;
        }
    }

    public Key ceiling(Key k) {
        Node ceil = ceiling(root, k);
        if (ceil == null) return null;
        return ceil.k;
    }

    private Node ceiling(Node x, Key k) {
        if (x == null) return null;
        int cmp = k.compareTo(x.k);
        if (cmp == 0) return x;
        else if (cmp > 0) return ceiling(x.right, k);
        else {
            Node ceil = ceiling(x.left, k);
            if (ceil == null) return x;
            return ceil;
        }
    }

    public int rank(Key k) {
        return rank(root, k);
    }

    private int rank(Node x, Key k) {
        if (x == null) return 0;
        int cmp = k.compareTo(x.k);
        if (cmp == 0) return size(x.left);
        else if (cmp < 0) return rank(x.left, k);
        else return size(x.left) + 1 + rank(x.right, k);
    }

    public Key select(int r) {
        Node select = select(root, r);
        if (select == null) return null;
        return select.k;
    }

    private Node select(Node x, int r) {
        if (x == null) return null;
        int t = size(x.left);
        if (r == t) return x;
        else if (r < t) return select(x.left, r);
        else return select(x.right, r - t - 1);
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> q = new Queue<>();
        if (lo.compareTo(hi) > 0) return q;
        keys(q, root, lo, hi);
        return q;
    }

    private void keys(Queue<Key> q, Node x, Key lo, Key hi) {
        if (x == null) return;
        int cmpLo = lo.compareTo(x.k);
        int cmpHi = hi.compareTo(x.k);
        if (cmpLo < 0) keys(q, x.left, lo, hi);
        if (cmpLo <= 0 && cmpHi >= 0) q.enqueue(x.k);
        if (cmpHi > 0) keys(q, x.right, lo, hi);
    }

    public int size(Key lo, Key hi) {
        if (lo.compareTo(hi) > 0) return 0;
        return size(root, lo, hi);
    }

    private int size(Node x, Key lo, Key hi) {
        int sz = 0;
        if (x == null) return sz;
        int cmpLo = lo.compareTo(x.k);
        int cmpHi = hi.compareTo(x.k);
        if (cmpLo < 0) sz += size(x.left, lo, hi);
        if (cmpLo <= 0 && cmpHi >= 0) sz++;
        if (cmpHi > 0) sz += size(x.right, lo, hi);
        return sz;
    }

    public void deleteMin() {
        if (isEmpty()) throw new UnsupportedOperationException();
        if (!isRed(root.left) && !isRed(root.right)) {
            // this means root is a 2-node. We change root.color to RED, so we can use flipColors on it to turn it back to BLACK
            root.color = RED;
        }
        root = deleteMin(root);
        // at the end, always turn root's color back to BLACK
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMin(Node h) {
        if (h.left == null) {
            // this means h is the smallest node in the tree already
            // since the tree is black-balanced and no right-leaning red links => h.left == null means h.right == null => return null
            return null;
        }
        // if we come here then h.left != null => don't need to worry about null pointer exception
        if (!isRed(h.left) && !isRed(h.left.left)) {
            // if isRed(h.left), that means h and h.left forms a 3-node => that's okay
            // if isRed(h.left) is not true, but isRed(h.left.left), that means h.left.left is present,
            // => there's a node smaller than h, and h.left.left and h.left also forms a 3-node => that's okay too
            // therefore, if both of these conditions fail, we need to make some changes
            h = moveRedLeft(h);
        }
        h.left = deleteMin(h.left);
        return balance(h);
    }

    private Node moveRedLeft(Node h) {
        // assume that h is RED and both h.left and h.left.left are BLACk, make h.left or
        // one of its children red
        flipColors(h); // turn h into BLACK and h.left and h.right into RED
        if (isRed(h.right.left)) {
            // if the sibling is not a 2-node, make h.left.left RED
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            // this extra flipColors(h) is to bring h back to RED, and both h.left and h.right back to BLACK.
            // since we already have h.left.left being RED, we don't need h.left to be RED anymore
            flipColors(h);
        }
        return h;
    }

    public void deleteMax() {
        if (isEmpty()) throw new UnsupportedOperationException();
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMax(Node h) {
        // we first check that if h.left is a red node, we can rotate it over to make
        // the next node on the right red, so it becomes part of 3 node
        if (isRed(h.left)) h = rotateRight(h);
        if (h.right == null) return null;
        if (!isRed(h.right) && !isRed(h.right.left)) {
            // if h.right is RED (mostly because of h.left being rotated over by the previous call) => h.right and h forms a 3 node => all good
            // if h.right.left is RED, h.right.left and h.right forms a 3-node, and because h.right.left can be rotated over on the next recursion => all good too
            // if both of these conditions fail, we need to make some changes
            h = moveRedRight(h);
        }
        h.right = deleteMax(h.right);
        return balance(h);
    }

    private Node moveRedRight(Node h) {
        // assume h is RED and both h.right and h.right.left are BLACK, make h.right or one of its children RED
        flipColors(h); //make h BLACK and h.left and h.right RED
        if (isRed(h.left.left)) {
            // if the sibling is not a 2-node, make h.right.right RED
            h = rotateRight(h);
            // this extra flipColors(h) brings h back to RED, and h.left and h.right back to BLACK
            // since we already have h.right.right RED, we don't need h.right being RED any more
            flipColors(h);
        }
        return h;
    }

    public void delete(Key k) {
        if (isEmpty()) throw new UnsupportedOperationException();
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = delete(root, k);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node delete(Node h, Key k) {
        if (k.compareTo(h.k) < 0) {
            if (!isRed(h.left) && !isRed(h.left.left)) {
                h = moveRedLeft(h);
            }
            h.left = delete(h.left, k);
        } else {
            // because all red links lean left, to make it appear on the right side,
            // we have to call rotateRight as the first thing if h.left is RED
            if (isRed(h.left)) {
                h = rotateRight(h);
            }
            // we need to call compareTo() again because the previous call might alter which node h links to
            // so we can't use cmp
            if (k.compareTo(h.k) == 0 && (h.right == null)) {
                // this means h is at the bottom of the tree
                return null;
            }
            if (!isRed(h.right) && !isRed(h.right.left)) {
                h = moveRedRight(h);
            }
            // same reason we need to call compareTo() again here as well
            if (k.compareTo(h.k) == 0) {
                // h is somewhere in the middle of the tree. We exchange it with its successor x= min(h.right)
                // by setting h.k and h.v to be k and v of x, and then deleteMin(h.right)
                Node x = min(h.right); // x is the successor of h
                h.k = x.k;
                h.v = x.v;
                h.right = deleteMin(h.right);
            } else {
                h.right = delete(h.right, k);
            }
        }
        return balance(h);
    }

    /**
     * Ex3.3.33 Certification
     * @param
     * @return
     */
    public boolean is23() {
        return is23(root);
    }

    private boolean is23(Node x) {
        if (x == null) return true;
        int xIsRed = isRed(x) ? 1 : 0;
        int leftIsRed = isRed(x.left) ? 1 : 0;
        int rightIsRed = isRed(x.right) ? 1 : 0;
        if (xIsRed + leftIsRed + rightIsRed > 1 || rightIsRed == 1) return false;
        return is23(x.left) && is23(x.right);
    }

    public boolean isBalanced() {
        Queue<Integer> countQueue = new Queue<>();
        traverse(root, countQueue, 0);
        int firstCount = countQueue.dequeue();
        for (int count : countQueue) {
            if (count != firstCount) {
                return false;
            }
        }
        return true;
    }

    private void traverse(Node x, Queue<Integer> countQueue, int blackCount) {
        if (x == null) {
            countQueue.enqueue(blackCount);
            return;
        }
        if (isRed(x.left)) traverse(x.left, countQueue, blackCount);
        else traverse(x.left, countQueue, blackCount + 1);
        if (isRed(x.right)) traverse(x.right, countQueue, blackCount);
        else traverse(x.right, countQueue, blackCount + 1);
    }

    private Node balance(Node h) {
        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);
        h.n = 1 + size(h.left) + size(h.right);
        return h;
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.n = h.n;
        h.n = 1 + size(h.left) + size(h.right);
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.n = h.n;
        h.n = 1 + size(h.left) + size(h.right);
        return x;
    }

    /**
     * flip colors implementation for insertion
     * @param h
     */
    private void flipColors(Node h) {
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        RedBlackBST<Integer, Integer> bst = new RedBlackBST<>();
        for (int i = 0; i < N; i++) {
            int k = (int) (Math.random() * N);
            bst.put(k, -k);
        }
        System.out.println(bst.is23());
        System.out.println(bst.isBalanced());
    }
}
