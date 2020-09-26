package chapter3.part3;

/**
 * Ex3.3.32
 * Implementation of AVL Tree
 * @param <Key>
 * @param <Value>
 */
public class AVLTree<Key extends Comparable<Key>, Value> {
    private class Node {
        Key k;
        Value v;
        Node left, right;
        int n, h;

        public Node(Key k, Value v, int n, int h) {
            this.k = k;
            this.v = v;
            this.n = n;
            this.h = h;
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

    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return x.h;
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
        if (cmp == 0) return x.v;
        else if (cmp < 0) return get(x.left, k);
        else return get(x.right, k);
    }

    public void put(Key k, Value v) {
        root = put(root, k, v);
    }

    private Node put(Node x, Key k, Value v) {
        if (x == null) return new Node(k, v, 1, 0);
        int cmp = k.compareTo(x.k);
        if (cmp == 0) x.v = v;
        else if (cmp < 0) {
            x.left = put(x.left, k, v);
        } else {
            x.right = put(x.right, k, v);
        }
        x.h = Math.max(height(x.left), height(x.right)) + 1;
        x.n = 1 + size(x.left) + size(x.right);
        return balance(x);
    }

    public void deleteMin() {
        if (isEmpty()) throw new UnsupportedOperationException();
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.n = 1 + size(x.left) + size(x.right);
        x.h = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);
    }

    public void deleteMax() {
        if (isEmpty()) throw new UnsupportedOperationException();
        root = deleteMax(root);
    }

    private Node deleteMax(Node x) {
        if (x.right == null) return x.left;
        x.right = deleteMin(x.right);
        x.n = 1 + size(x.left) + size(x.right);
        x.h = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);
    }

    /**
     * min() needs to return a Node instead of returning a key directly,
     * so it can be re-usable by delete()
     * for symmetry we make max() to return a node as well
     * @return
     */
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

    public void delete(Key k) {
        if (isEmpty()) throw new UnsupportedOperationException();
        root = delete(root, k);
    }

    private Node delete(Node x, Key k) {
        int cmp = k.compareTo(x.k);
        if (cmp < 0) {
            x.left = delete(x.left, k);
        } else if (cmp > 0) {
            x.right = delete(x.right, k);
        } else {
            if (x.right == null) return x.left;
            else if (x.left == null) return x.right;
            else {
                Node t = x;
                x = min(t.right);
                x.left = t.left;
                x.right = deleteMin(t.right);
            }
        }
        x.n = 1 + size(x.left) + size(x.right);
        x.h = 1 + Math.max(height(x.left), height(x.right));
        return balance(x);
    }

    /**
     * Implementation Note
     * - balanceFactor(x) < -1 means right subtree's height is more than 1 higher than left subtree's
     *      - As an instinct, rotating left would solve the im-balance
     *      - However we have to be careful because after the left rotation:
     *          - x.right is now the new root. x.right.right is still the right subtree and x is the new left subtree
     *          - x.right.left is mounted underneath x and height(x) >= height(x.right.left) + 1
     *          - If initially height(x.right.left) > height(x.right.right), that means height of the left subtree(x)
     *          is at least 2 units higher than height of the right subtree (x.right.right) => still im-balance at the new root
     *      - Therefore if balanceFactor(x.right) > 0 we need to first do a right rotation before performing the left
     *      rotation
     * - balanceFactor(x) > 1 means left subtree's height is more than 1 higher than right subtree's
     *      - A similar analysis can be developed as to why a left rotation is needed before the right rotation
     *      if balanceFactor(x.left) < 0
     */
    private Node balance(Node x) {
        if (balanceFactor(x) < -1) {
            // right - left > 1. We need to rotateLeft, but first we might need to rotateRight the right child
            if (balanceFactor(x.right) > 0) {
                x.right = rotateRight(x.right);
            }
            x = rotateLeft(x);
        } else if (balanceFactor(x) > 1) {
            // left - right > 1 => need to rotateRight, but first we might need to rotateLeft the left child
            if (balanceFactor(x.left) < 0) {
                x.left = rotateLeft(x.left);
            }
            x = rotateRight(x);
        }
        return x;
    }

    private int balanceFactor(Node x) {
        return height(x.left) - height(x.right);
    }

    private Node rotateRight(Node x) {
        Node t = x.left;
        x.left = t.right;
        t.right = x;
        t.n = x.n;
        x.n = 1 + size(x.left) + size(x.right);
        x.h = 1 + Math.max(height(x.left), height(x.right));
        t.h = 1 + Math.max(height(t.left), height(t.right));
        return t;
    }

    private Node rotateLeft(Node x) {
        Node t = x.right;
        x.right = t.left;
        t.left = x;
        t.n = x.n;
        x.n = 1 + size(x.left) + size(x.right);
        x.h = 1 + Math.max(height(x.left), height(x.right));
        t.h = 1 + Math.max(height(t.left), height(t.right));
        return t;
    }

    public static void main(String[] args) {
        AVLTree<Integer, Integer> bst = new AVLTree<>();
        int[] keys = {0, 5, 6, 4, 3, 5, 8, 6, 1, 2, 10, 9};
        for (int k: keys) {
            bst.put(k, -k);
        }
        System.out.println();
    }
}
