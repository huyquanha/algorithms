package chapter3.part5;

import edu.princeton.cs.algs4.Queue;

import java.util.NoSuchElementException;

public class RedBlackBSSet<Key extends Comparable<Key>> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private static class Node<K> {
        K k;
        Node<K> left, right;
        boolean color;
        int sz;

        public Node(K k, boolean color, int sz) {
            this.k = k;
            this.color = color;
            this.sz = sz;
        }
    }

    private Node<Key> root;

    public void add(Key k) {
        root = add(root, k);
        if (isRed(root)) root.color = BLACK;
    }

    public void delete(Key k) {
        if (isEmpty()) throw new NoSuchElementException("Set underflow");
        if (k == null) throw new IllegalArgumentException();
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = delete(root, k);
        if (!isEmpty()) root.color = BLACK;
    }

    public boolean contains(Key k) {
        if (k == null) throw new IllegalArgumentException();
        Node<Key> cur = root;
        while (cur != null) {
            int cmp = k.compareTo(cur.k);
            if (cmp == 0) return true;
            else if (cmp < 0) cur = cur.left;
            else cur = cur.right;
        }
        return false;
    }

    public Iterable<Key> rangeSearch(Key lo, Key hi) {
        if (lo == null || hi == null) throw new IllegalArgumentException();
        if (lo.compareTo(hi) > 0) throw new IllegalArgumentException();
        Queue<Key> q = new Queue<>();
        rangeSearch(q, root, lo, hi);
        return q;
    }

    public int rangeCount(Key lo, Key hi) {
        if (lo == null || hi == null) throw new IllegalArgumentException();
        if (lo.compareTo(hi) > 0) throw new IllegalArgumentException();
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }

    public int rank(Key k) {
        if (k == null) throw new IllegalArgumentException();
        return rank(root, k);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size(root);
    }

    public Key min() {
        if (isEmpty()) throw new NoSuchElementException();
        return min(root).k;
    }

    public Key max() {
        if (isEmpty()) throw new NoSuchElementException();
        return max(root).k;
    }

    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException();
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException();
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node<Key> deleteMin(Node<Key> x) {
        if (x.left == null) return null;
        if (!isRed(x.left) && !isRed(x.left.left)) x = moveRedLeft(x);
        x.left = deleteMin(x.left);
        return balance(x);
    }

    private Node<Key> deleteMax(Node<Key> x) {
        if (isRed(x.left)) x = rotateRight(x);
        if (x.right == null) return null;
        if (!isRed(x.right) && !isRed(x.right.left)) x = moveRedRight(x);
        x.right = deleteMax(x.right);
        return balance(x);
    }

    private Node<Key> add(Node<Key> x, Key k) {
        if (k == null) throw new IllegalArgumentException();
        if (x == null) return new Node<>(k, RED, 0);
        int cmp = k.compareTo(x.k);
        if (cmp < 0) x.left = add(x.left, k);
        else if (cmp > 0) x.right = add(x.right, k);
        return balance(x);
    }

    private Node<Key> delete(Node<Key> x, Key k) {
        if (k.compareTo(x.k) < 0) {
            // goes down the left subtree
            if (!isRed(x.left) && !isRed(x.left.left)) x = moveRedLeft(x);
            x.left = delete(x.left, k);
        } else {
            if (isRed(x.left)) x = rotateRight(x);

            if (k.compareTo(x.k) == 0 && x.right == null) {
                return null;
            }
            if (!isRed(x.right) && !isRed(x.right.left)) x = moveRedRight(x);
            if (k.compareTo(x.k) == 0) {
                Node<Key> min = min(x.right);
                x.k = min.k;
                x.right = deleteMin(x.right);
            } else {
                x.right = delete(x.right, k);
            }
        }
        return balance(x);
    }

    private Node<Key> moveRedLeft(Node<Key> x) {
        flipColors(x);
        if (isRed(x.right.left)) {
            x.right = rotateRight(x.right);
            x = rotateLeft(x);
            flipColors(x);
        }
        return x;
    }

    private Node<Key> moveRedRight(Node<Key> x) {
        flipColors(x);
        if (isRed(x.left.left)) {
            x = rotateRight(x);
            flipColors(x);
        }
        return x;
    }

    private Node<Key> balance(Node<Key> x) {
        if (isRed(x.right) && !isRed(x.left)) x = rotateLeft(x);
        if (isRed(x.left) && isRed(x.left.left)) x = rotateRight(x);
        if (isRed(x.left) && isRed(x.right)) flipColors(x);
        x.sz = 1 + size(x.left) + size(x.right);
        return x;
    }

    private Node<Key> min(Node<Key> x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    private Node<Key> max(Node<Key> x) {
        if (x.right == null) return x;
        return max(x.right);
    }

    private Node<Key> rotateLeft(Node<Key> x) {
        Node<Key> h = x.right;
        x.right = h.left;
        h.left = x;
        h.color = x.color;
        x.color = RED;
        h.sz = x.sz;
        x.sz = 1 + size(x.left) + size(x.right);
        return h;
    }

    private Node<Key> rotateRight(Node<Key> x) {
        Node<Key> h = x.left;
        x.left = h.right;
        h.right = x;
        h.color = x.color;
        x.color = RED;
        h.sz = x.sz;
        x.sz = 1 + size(x.left) + size(x.right);
        return h;
    }

    private void flipColors(Node<Key> x) {
        x.color = !x.color;
        x.left.color = !x.left.color;
        x.right.color = !x.right.color;
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.sz;
    }

    private int rank(Node<Key> x, Key k) {
        if (x == null) return 0;
        int cmp = k.compareTo(x.k);
        if (cmp == 0) {
            return size(x.left);
        } else if (cmp > 0) {
            return size(x.left) + 1 + rank(x.right, k);
        } else {
            return rank(x.left, k);
        }
    }

    private boolean isRed(Node x) {
        return x != null && x.color == RED;
    }

    private void rangeSearch(Queue<Key> q, Node<Key> x, Key lo, Key hi) {
        if (x == null) return;
        int cmpLo = lo.compareTo(x.k);
        int cmpHi = hi.compareTo(x.k);
        if (cmpLo < 0) {
            // lo < x.k
            rangeSearch(q, x.left, lo, hi);
        }
        if (cmpLo <= 0 && cmpHi >= 0) {
            // lo <= x.k <= hi
            q.enqueue(x.k);
        }
        if (cmpHi > 0) {
            // x.k < hi
            rangeSearch(q, x.right, lo, hi);
        }
    }
}
