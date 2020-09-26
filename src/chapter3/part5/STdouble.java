package chapter3.part5;

import java.util.NoSuchElementException;

/**
 * Ex3.5.5
 * A ST with keys as primitive doubles and generic Value
 * Implementation based on AVL Tree
 */
public class STdouble<Value> {
    private static class Node<V> {
        double key;
        V val;
        Node<V> left, right;
        int n, h;

        public Node(double key, V val, int n, int h) {
            this.key = key;
            this.val = val;
            this.n = n;
            this.h = h;
        }
    }

    private Node<Value> root;

    public int size() {
        return size(root);
    }

    private int size(Node<Value> x) {
        if (x == null) return 0;
        return x.n;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int height() {
        return height(root);
    }

    private int height(Node<Value> x) {
        if (x == null) return -1;
        return x.h;
    }

    public Value get(double key) {
        return get(root, key);
    }

    public Value get(Node<Value> x, double key) {
        if (x == null) return null;
        if (key == x.key) return x.val;
        else if (key < x.key) return get(x.left, key);
        else return get(x.right, key);
    }

    public boolean contains(double key) {
        return get(key) != null;
    }

    public void put(double key, Value val) {
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
    }

    public double min() {
        if (isEmpty()) throw new NoSuchElementException();
        return min(root).key;
    }

    private Node<Value> min(Node<Value> x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    public double max() {
        if (isEmpty()) throw new NoSuchElementException();
        return max(root).key;
    }

    private Node<Value> max(Node<Value> x) {
        if (x.right == null) return x;
        return max(x.right);
    }

    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");
        root = deleteMin(root);
    }

    private Node<Value> deleteMin(Node<Value> x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        return balance(x);
    }

    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");
        root = deleteMax(root);
    }

    private Node<Value> deleteMax(Node<Value> x) {
        if (x.right == null) return x.left;
        x.right = deleteMax(x.right);
        return balance(x);
    }

    public void delete(double key) {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");
        if (!contains(key)) return;
        root = delete(root, key);
    }

    private Node<Value> delete(Node<Value> x, double key) {
        if (key < x.key) x.left = delete(x.left, key);
        else if (key > x.key) x.right = delete(x.right, key);
        else {
            if (x.left == null) return x.right;
            else if (x.right == null) return x.left;
            else {
                Node<Value> t = x;
                x = min(t.right);
                x.left = t.left;
                x.right = deleteMin(t.right);
            }
        }
        return balance(x);
    }

    private Node<Value> put(Node<Value> x, double key, Value val) {
        if (x == null) return new Node(key, val, 1, 0);
        if (key == x.key) {
            x.val = val;
            return x;
        } else if (key < x.key) {
            x.left = put(x.left, key, val);
        } else {
            x.right = put(x.right, key, val);
        }
        return balance(x);
    }

    private Node<Value> balance(Node<Value> x) {
        int bal = balanceFactor(x);
        if (bal < -1) {
            // skew to the right
            if (balanceFactor(x.right) > 0) {
                x.right = rotateRight(x.right);
            }
            x = rotateLeft(x);
        } else if (bal > 1) {
            // skew to the left
            if (balanceFactor(x.left) < 0) {
                x.left = rotateLeft(x.left);
            }
            x = rotateRight(x);
        }
        x.n = 1 + size(x.left) + size(x.right);
        x.h = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    private Node<Value> rotateLeft(Node<Value> x) {
        Node tmp = x.right;
        x.right = tmp.left;
        tmp.left = x;
        tmp.n = x.n;
        x.n = 1 + size(x.left) + size(x.right);
        x.h = Math.max(height(x.left), height(x.right)) + 1;
        tmp.h = Math.max(height(tmp.left), height(tmp.right)) + 1;
        return tmp;
    }

    private Node<Value> rotateRight(Node<Value> x) {
        Node tmp = x.left;
        x.left = tmp.right;
        tmp.right = x;
        tmp.n = x.n;
        x.n = 1 + size(x.left) + size(x.right);
        x.h = Math.max(height(x.left), height(x.right)) + 1;
        tmp.h = Math.max(height(tmp.left), height(tmp.right)) + 1;
        return tmp;
    }

    private int balanceFactor(Node<Value> x) {
        return height(x.left) - height(x.right);
    }
}
