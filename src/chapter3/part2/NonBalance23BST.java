package chapter3.part2;

import chapter3.ST;


/**
 * Ex3.3.23
 * @param <Key>
 * @param <Value>
 */
public class NonBalance23BST<Key extends Comparable<Key>, Value> implements ST<Key, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        Key k;
        Value v;
        Node left, right;
        int n, l; // l is the path length from this node to the root
        boolean color;

        public Node(Key k, Value v, int n, int l, boolean color) {
            this.k = k;
            this.v = v;
            this.n = n;
            this.l = l;
            this.color = color;
        }
    }

    private Node root;
    private int totalLength; // total path length of the tree

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return 1 + size(x.left) + size(x.right);
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
        root = put(null, root, k, v);
        root.color = BLACK;
    }

    private Node put(Node parent, Node x, Key k, Value v) {
        if (x == null) {
            Node newNode;
            int newLength = parent != null ? parent.l + 1 : 0;
            if (is3Node(parent)) {
                newNode = new Node(k, v, 1, newLength, BLACK);
            } else {
                newNode = new Node(k, v, 1, newLength, RED);
            }
            totalLength += newLength;
            return newNode;
        }
        int cmp = k.compareTo(x.k);
        if (cmp == 0) {
            x.v = v;
        } else if (cmp < 0) {
            x.left = put(x, x.left, k, v);
        } else {
            x.right = put(x, x.right, k, v);
        }
        x.n = 1 + size(x.left) + size(x.right);
        return x;
    }

    public int pathLength() {
        return totalLength;
    }

    public void delete(Key k) {
        // placeholder only
    }

    public Iterable<Key> keys() {
        // placeholder only
        return null;
    }

    private boolean is3Node(Node x) {
        if (x == null) return false;
        return isRed(x) || isRed(x.left) || isRed(x.right);
    }

    private boolean isRed(Node x) {
        return x != null && x.color == RED;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        NonBalance23BST<Integer, Integer> bst = new NonBalance23BST<>();
        for (int i = 0; i < N; i++) {
            int key = (int) (Math.random() * N);
            System.out.print(key + " ");
            bst.put(key, -key);
        }
        System.out.println();
        /**
         * Experiments shows that the average path length is about
         * if N = 16 average path length = 1
         * if N = 32 average path length = 2
         * if N = 64 average path length = 3
         * if N = 128 average path length = 4
         * => average path length is log(N) - 3
         */
        System.out.println(bst.pathLength() / N);
    }
}
