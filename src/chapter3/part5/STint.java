package chapter3.part5;

import chapter1.part3.Queue;

import java.util.NoSuchElementException;

/**
 * Ex3.5.5
 * A ST with keys as primitive integers and generic Value
 * Implementation is based on left-leaning Red-Black BST
 * @param <Value>
 */
public class STint<Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private static class Node<V> {
        int key;
        V val;
        Node<V> left, right;
        boolean color;
        int n;

        public Node(int key, V val, boolean color, int n) {
            this.key = key;
            this.val = val;
            this.color = color;
            this.n = n;
        }
    }

    private Node<Value> root;

    public int size() {
        return size(root);
    }

    public int size(Node x) {
        if (x == null) return 0;
        return x.n;
    }

    public Value get(int key) {
        return get(root, key);
    }

    private Value get(Node<Value> x, int key) {
        if (x == null) return null;
        if (key < x.key) return get(x.left, key);
        else if (key > x.key) return get(x.right, key);
        return x.val;
    }

    public boolean contains(int key) {
        return get(key) != null;
    }

    public void put(int key, Value val) {
        if (val == null) delete(key);
        else {
            root = put(root, key, val);
            root.color = BLACK;
        }
    }

    private Node<Value> put(Node<Value> x, int key, Value val) {
        if (x == null) return new Node(key, val, RED, 1);
        if (key == x.key) x.val = val;
        else if (key < x.key) x.left = put(x.left, key, val);
        else x.right = put(x.right, key, val);
        return balance(x);
    }

    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node<Value> deleteMin(Node<Value> x) {
        if (x.left == null) return null;
        if (!isRed(x.left) && !isRed(x.left.left)) x = moveRedLeft(x);
        x.left = deleteMin(x.left);
        return balance(x);
    }

    private Node<Value> moveRedLeft(Node<Value> x) {
        // assumption: x is RED and x.left and x.left.left are both BLACK
        flipColors(x);
        if (isRed(x.right.left)) {
            x.right = rotateRight(x.right);
            x = rotateLeft(x);
            flipColors(x);
        }
        return x;
    }

    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node<Value> deleteMax(Node<Value> x) {
        if (isRed(x.left)) x = rotateRight(x);
        if (x.right == null) return null;
        if (!isRed(x.right) && !isRed(x.right.left)) x = moveRedRight(x);
        x.right = deleteMax(x.right);
        return balance(x);
    }

    private Node<Value> moveRedRight(Node<Value> x) {
        // assumption: x is RED and x.right and x.right.left are both BLACK
        flipColors(x);
        if (isRed(x.left.left)) {
            x = rotateRight(x);
            flipColors(x);
        }
        return x;
    }

    public void delete(int key) {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");
        if (!contains(key)) return;
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node<Value> delete(Node<Value> x, int key) {
        if (key < x.key) {
            if (!isRed(x.left) && !isRed(x.left.left)) x = moveRedLeft(x);
            x.left = delete(x.left, key);
        } else {
            if (isRed(x.left)) x = rotateRight(x);
            if (key == x.key && x.right == null) return null;
            if (!isRed(x.right) && !isRed(x.right.left)) x = moveRedRight(x);
            if (key == x.key) {
                Node<Value> succ = min(x.right);
                x.key = succ.key;
                x.val = succ.val;
                x.right = deleteMin(x.right);
            } else {
                x.right = delete(x.right, key);
            }
        }
        return balance(x);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public Integer min() {
        Node<Value> min = min(root);
        if (min == null) return null;
        return min.key;
    }

    private Node<Value> min(Node<Value> x) {
        if (x == null) return null;
        if (x.left == null) return x;
        return min(x.left);
    }

    public Integer max() {
        Node<Value> max = max(root);
        if (max == null) return null;
        return max.key;
    }

    private Node<Value> max(Node<Value> x) {
        if (x == null) return null;
        if (x.right == null) return x;
        return max(x.right);
    }

    public Integer floor(int key) {
        Node<Value> floor = floor(root, key);
        if (floor == null) return null;
        return floor.key;
    }

    private Node<Value> floor(Node<Value> x, int key) {
        if (x == null) return null;
        if (key == x.key) return x;
        else if (key < x.key) return floor(x.left, key);
        else {
            Node<Value> floor = floor(x.right, key);
            if (floor == null) return x;
            return floor;
        }
    }

    public Integer ceil(int key) {
        Node<Value> ceil = ceil(root, key);
        if (ceil == null) return null;
        return ceil.key;
    }

    private Node<Value> ceil(Node<Value> x, int key) {
        if (x == null) return null;
        if (key == x.key) return x;
        else if (key > x.key) return ceil(x.right, key);
        else {
            Node ceil = ceil(x.left, key);
            if (ceil == null) return x;
            return ceil;
        }
    }

    public int rank(int key) {
        return rank(root, key);
    }

    private int rank(Node<Value> x, int key) {
        if (x == null) return 0;
        if (key == x.key) return size(x.left);
        else if (key < x.key) return rank(x.left, key);
        else return 1 + size(x.left) + rank(x.right, key);
    }

    public Integer select(int rank) {
        Node<Value> select = select(root, rank);
        if (select == null) return null;
        return select.key;
    }

    private Node<Value> select(Node<Value> x, int rank) {
        if (x == null) return null;
        int rankX = size(x.left);
        if (rank == rankX) return x;
        else if (rank < rankX) return select(x.left, rank);
        else return select(x.right, rank - rankX - 1);
    }

    public Iterable<Integer> keys(int lo, int hi) {
        Queue<Integer> keys = new Queue<>();
        if (lo > hi) return keys;
        addKeys(keys, root, lo, hi);
        return keys;
    }

    private void addKeys(Queue<Integer> keys, Node<Value> x, int lo, int hi) {
        if (x == null) return;
        int key = x.key;
        if (key > lo) addKeys(keys, x.left, lo, hi);
        if (key >= lo && key <= hi) keys.enqueue(key);
        if (key < hi) addKeys(keys, x.right, lo, hi);
    }

    public int size(int lo, int hi) {
        if (lo > hi) return 0;
        return size(root, lo, hi);
    }

    private int size(Node<Value> x, int lo, int hi) {
        if (x == null) return 0;
        int key = x.key;
        int sz = 0;
        if (key > lo) sz += size(x.left, lo, hi);
        if (key < hi) sz += size(x.right, lo, hi);
        if (key >= lo && key <= hi) sz++;
        return sz;
    }


    private Node balance(Node<Value> x) {
        if (isRed(x.right) && !isRed(x.left)) x = rotateLeft(x);
        if (isRed(x.left) && isRed(x.left.left)) x = rotateRight(x);
        if (isRed(x.left) && isRed(x.right)) flipColors(x);
        x.n = 1 + size(x.left) + size(x.right);
        return x;
    }

    private boolean isRed(Node x) {
        return x != null && x.color == RED;
    }

    private Node rotateLeft(Node<Value> x) {
        Node<Value> tmp = x.right;
        x.right = tmp.left;
        tmp.left = x;
        tmp.n = x.n;
        x.n = 1 + size(x.left) + size(x.right);
        tmp.color = x.color;
        x.color = RED;
        return tmp;
    }

    private Node rotateRight(Node x) {
        Node tmp = x.left;
        x.left = tmp.right;
        tmp.right = x;
        tmp.n = x.n;
        x.n = 1 + size(x.left) + size(x.right);
        tmp.color = x.color;
        x.color = RED;
        return tmp;
    }

    private void flipColors(Node<Value> x) {
        x.color = !x.color;
        if (x.left != null) x.left.color = !x.left.color;
        if (x.right != null) x.right.color = !x.right.color;
    }
}