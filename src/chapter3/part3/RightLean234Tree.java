package chapter3.part3;

/**
 * Ex3.3.27
 * @param <Key>
 * @param <Value>
 */
public class RightLean234Tree<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        Key k;
        Value v;
        Node left, right;
        int n;
        boolean color;

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

    private Node put(Node x, Key k, Value v) {
        if (x == null) return new Node(k, v, 1, RED);
        if (isRed(x.left) && isRed(x.right)) flipColors(x);
        int cmp = k.compareTo(x.k);
        if (cmp == 0) {
            x.v = v;
        } else if (cmp < 0) {
            x.left = put(x.left, k, v);
        } else {
            x.right = put(x.right, k, v);
        }
        return balance(x);
    }

    private Node balance(Node x) {
        if (isRed(x.left)) {
            if (isRed(x.left.left)) {
                x = rotateRight(x);
            } else if (isRed(x.left.right)) {
                x.left = rotateLeft(x.left);
                x = rotateRight(x);
            }
        } else if (isRed(x.right)) {
            if (isRed(x.right.right)) {
                x = rotateLeft(x);
            } else if (isRed(x.right.left)) {
                x.right = rotateRight(x.right);
                x = rotateLeft(x);
            }
        }
        x.n = 1 + size(x.left) + size(x.right);
        return x;
    }

    private Node rotateRight(Node x) {
        Node h = x.left;
        x.left = h.right;
        h.right = x;
        h.color = x.color;
        x.color = RED;
        h.n = x.n;
        x.n = 1 + size(x.left) + size(x.right);
        return h;
    }

    private Node rotateLeft(Node x) {
        Node h = x.right;
        x.right = h.left;
        h.left = x;
        h.color = x.color;
        x.color = RED;
        h.n = x.n;
        x.n = 1 + size(x.left) + size(x.right);
        return h;
    }

    private boolean isRed(Node x) {
        return x != null && x.color == RED;
    }

    private void flipColors(Node x) {
        x.color = !x.color;
        x.left.color = !x.left.color;
        x.right.color = !x.right.color;
    }

    public static void main(String[] args) {
        RightLean234Tree<Integer, Integer> bst = new RightLean234Tree<>();
        int[] keys = {0, 5, 6, 4, 3, 5, 8, 6, 1, 2};
        for (int k: keys) {
            bst.put(k, -k);
        }
        System.out.println();
    }
}
