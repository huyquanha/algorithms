package chapter3.part3;

/**
 * Ex3.3.26
 * @param <Key>
 * @param <Value>
 */
public class NonRec234Tree<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        Key k;
        Value v;
        Node left, right, parent;
        int n;
        boolean color;

        public Node(Key k, Value v, Node parent, int n, boolean color) {
            this.k = k;
            this.v = v;
            this.parent = parent;
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

    public void putIter(Key k, Value v) {
        Node prev = null;
        Node cur = root;
        boolean insertLeft = true;
        while (cur != null) {
            if (isRed(cur.left) && isRed(cur.right)) flipColors(cur);
            int cmp = k.compareTo(cur.k);
            if (cmp == 0) {
                cur.v = v;
                root.color = BLACK;
                return;
            } else if (cmp < 0) {
                insertLeft = true;
            } else {
                insertLeft = false;
            }
            prev = cur;
            cur = insertLeft ? prev.left : prev.right;
        }
        if (prev == null) {
            root = new Node(k, v, null, 1, BLACK);
        } else {
            if (insertLeft) {
                prev.left = new Node(k, v, prev,1, RED);
            } else {
                prev.right = new Node(k, v, prev, 1, RED);
                if (!isRed(prev.left)) {
                    Node parentPrev = prev.parent;
                    Node newPrev = rotateLeft(prev);
                    if (parentPrev == null) {
                        root = newPrev;
                        newPrev.parent = null;
                    } else {
                        if (parentPrev.left == prev) {
                            parentPrev.left = newPrev;
                        } else {
                            parentPrev.right = newPrev;
                        }
                        newPrev.parent = parentPrev;
                    }
                    prev = newPrev;
                }
            }
            Node parentPrev = prev.parent;
            if (isRed(prev) && parentPrev != null && parentPrev.left == prev) {
                Node grandParentPrev = parentPrev.parent;
                Node newParentPrev = rotateRight(parentPrev);
                if (grandParentPrev == null) {
                    root = newParentPrev;
                    newParentPrev.parent = null;
                } else {
                    if (grandParentPrev.left == parentPrev) {
                        grandParentPrev.left = newParentPrev;
                    } else {
                        grandParentPrev.right = newParentPrev;
                    }
                    newParentPrev.parent = grandParentPrev;
                }
            }
            root.color = BLACK;
        }
    }

    public void deleteMin() {
        if (isEmpty()) throw new UnsupportedOperationException();
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return null;
        if (!isRed(x.left) && !isRed(x.left.left)) {
            x = moveRedLeft(x);
        }
        x.left = deleteMin(x.left);
        x.n = 1 + size(x.left) + size(x.right);
        return balance(x);
    }

    private Node moveRedLeft(Node x) {
        // assume x is RED, and x.left and x.left.left are BLACK
        flipColors(x);
        if (isRed(x.right.left)) {
            // if the sibling is not a 2-node
            x.right = rotateRight(x.right);
            x = rotateLeft(x);
            flipColors(x);
        }
        return x;
    }

    public void deleteMax() {
        if (isEmpty()) throw new UnsupportedOperationException();
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node deleteMax(Node x) {
        if (isRed(x.left)) x = rotateRight(x);
        if (x.right == null) return null;
        if (!isRed(x.right) && !isRed(x.right.left)) {
            x = moveRedRight(x);
        }
        return balance(x);
    }

    private Node moveRedRight(Node x) {
        flipColors(x);
        if (isRed(x.left.left)) {
            x = rotateRight(x);
            flipColors(x);
        }
        return x;
    }

    public void delete(Key k) {
        if (isEmpty()) throw new UnsupportedOperationException();
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = delete(root, k);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node delete(Node x, Key k) {
        int cmp = k.compareTo(x.k);
        if (cmp < 0) {
            // k is to the left of x. We do the rotation first
            if (!isRed(x.left) && !isRed(x.left.left)) {
                x = moveRedLeft(x);
            }
            x.left = delete(x.left, k);
        } else {
            if (isRed(x.left)) x = rotateRight(x);
            if (k.compareTo(x.k) == 0 && (x.right == null)) {
                return null;
            }
            if (!isRed(x.right) && !isRed(x.right.left)) {
                x = moveRedRight(x);
            }
            if (k.compareTo(x.k) == 0) {
                Node h = min(x.right);
                x.k = h.k;
                x.v = h.v;
                x.right = deleteMin(x.right);
            } else {
                x.right = delete(x.right, k);
            }
        }
        return balance(x);
    }

    private Node min(Node x) {
        if (x == null) return null;
        if (x.left == null) return x;
        return min(x.left);
    }

    private Node rotateLeft(Node x) {
        Node h = x.right;
        x.right = h.left;
        if (h.left != null) {
            h.left.parent = x;
        }
        h.left = x;
        x.parent = h;
        h.color = x.color;
        x.color = RED;
        h.n = x.n;
        x.n = 1 + size(x.left) + size(x.right);
        return h;
    }

    private Node rotateRight(Node x) {
        Node h = x.left;
        x.left = h.right;
        if (h.right != null) {
            h.right.parent = x;
        }
        h.right = x;
        x.parent = h;
        h.color = x.color;
        x.color = RED;
        h.n = x.n;
        x.n = 1 + size(x.left) + size(x.right);
        return h;
    }

    private Node balance(Node x) {
        if (isRed(x.right) && !isRed(x.left)) x = rotateLeft(x);
        if (isRed(x.left) && isRed(x.left.left)) x = rotateRight(x);
        x.n = 1 + size(x.left) + size(x.right);
        return x;
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
        int N = Integer.parseInt(args[0]);
        NonRec234Tree<Integer, Integer> bst = new NonRec234Tree<>();
//        List<Integer> keys = new ArrayList<>();
//        for (int i = 0; i < N; i++) {
//            int key = (int) (Math.random() * N);
//            keys.add(key);
//            bst.putIter(key, -key);
//        }
        int[] keys = {0, 5, 6, 4, 3, 5, 7, 6, 2, 1};
        for (int k: keys) {
            bst.putIter(k, -k);
        }
        System.out.println();
    }
}
