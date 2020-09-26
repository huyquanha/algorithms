package web.week5;

import chapter1.part3.Queue;

import java.util.NoSuchElementException;

/**
 * assumption: no 2 intervals have the same left endpoint (lo)
 * @param <Key>
 * @param <Value>
 */
public class IntervalST<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private static class Node<K extends Comparable<K>, V> {
        K lo;
        K hi;
        V val;
        K max;
        Node<K,V> left, right;
        int n;
        boolean color;

        public Node(K lo, K hi, V val, int n, boolean color) {
            if (hi.compareTo(lo) < 0) {
                throw new IllegalArgumentException();
            }
            this.lo = lo;
            this.hi = hi;
            this.val = val;
            this.max = hi;
            this.n = n;
            this.color = color;
        }
    }

    private Node<Key, Value> root;

    public void put(Key lo, Key hi, Value val) {
        if (lo == null || hi == null) throw new IllegalArgumentException();
        if (val == null) delete(lo, hi);
        root = put(root, lo, hi, val);
    }

    private Node<Key, Value> put(Node<Key, Value> h, Key lo, Key hi, Value val) {
        if (h == null) return new Node<>(lo, hi, val, 1, RED);
        if (lo.compareTo(h.lo) < 0) {
            // go left
            h.left = put(h.left, lo, hi ,val);
        } else if (lo.compareTo(h.lo) > 0) {
            // go right
            h.right = put(h.right, lo, hi, val);
        } else {
            // update if hi == h.hi
            if (hi.compareTo(h.hi) == 0) {
                h.val = val;
            } else {
                throw new IllegalArgumentException("No 2 intervals can have the same left endpoint");
            }
        }
        return balance(h);
    }

    public Value get(Key lo, Key hi) {
        if (lo == null || hi == null) throw new IllegalArgumentException();
        return get(root, lo, hi);
    }

    public boolean contains(Key lo, Key hi) {
        return get(lo, hi) != null;
    }

    private Value get(Node<Key, Value> h, Key lo, Key hi) {
        if (h == null) return null;
        if (lo.compareTo(h.lo) < 0) {
            return get(h.left, lo, hi);
        } else if (lo.compareTo(h.lo) > 0) {
            return get(h.right, lo, hi);
        } else {
            if (hi.compareTo(h.hi) == 0) {
                return h.val;
            } else {
                return null;
            }
        }
    }

    public Key min() {
        if (isEmpty()) throw new NoSuchElementException();
        return min(root).lo;
    }

    private Node<Key, Value> min(Node<Key, Value> h) {
        if (h.left == null) return h;
        return min(h.left);
    }

    public Key max() {
        if (isEmpty()) throw new NoSuchElementException();
        return max(root).lo;
    }

    private Node<Key, Value> max(Node<Key, Value> h) {
        if (h.right == null) return h;
        return max(h.right);
    }

    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node<Key, Value> deleteMin(Node<Key, Value> h) {
        if (h.left == null) return null;
        if (!isRed(h.left) && !isRed(h.left.left)) h = moveRedLeft(h);
        h.left = deleteMin(h.left);
        return balance(h);
    }

    private Node<Key, Value> moveRedLeft(Node<Key, Value> h) {
        flipColors(h);
        if (isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node<Key, Value> deleteMax(Node<Key, Value> h) {
        if (isRed(h.left)) h = rotateRight(h);
        if (h.right == null) return null;
        if (!isRed(h.right) && !isRed(h.right.left)) h = moveRedRight(h);
        h.right = deleteMax(h.right);
        return balance(h);
    }

    private Node<Key, Value> moveRedRight(Node<Key, Value> h) {
        flipColors(h);
        if (isRed(h.left.left)) {
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    public void delete(Key lo, Key hi) {
        if (lo == null || hi == null) throw new IllegalArgumentException();
        if (isEmpty()) throw new NoSuchElementException("BST underflow");
        if (!contains(lo, hi)) throw new NoSuchElementException();
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = delete(root, lo, hi);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node<Key, Value> delete(Node<Key, Value> h, Key lo, Key hi) {
        if (lo.compareTo(h.lo) < 0) {
            if (!isRed(h.left) && !isRed(h.left.left)) h = moveRedLeft(h);
            h.left = delete(h.left, lo, hi);
        } else {
            if (isRed(h.left)) h = rotateRight(h);
            if (lo.compareTo(h.lo) == 0 && (h.right == null)) {
                return null;
            }
            if (!isRed(h.right) && !isRed(h.right.left)) h = moveRedRight(h);
            if (lo.compareTo(h.lo) == 0) {
                Node<Key, Value> minRight = min(h.right);
                h.lo = minRight.lo;
                h.hi = minRight.hi;
                h.val = minRight.val;
                h.right = deleteMin(h.right);
            } else {
                h.right = delete(h.right, lo, hi);
            }
        }
        return balance(h);
    }

    public Iterable<Value> intersects(Key lo, Key hi) {
        if(lo == null || hi == null || hi.compareTo(lo) < 0) throw new IllegalArgumentException();
        Queue<Value> q = new Queue<>();
        intersects(q, root, lo, hi);
        return q;
    }

    private void intersects(Queue<Value> q, Node<Key, Value> x, Key lo, Key hi) {
        if (x == null) return;
        if (intersects(x.lo, x.hi, lo, hi)) {
            q.enqueue(x.val);
        }
        if (x.left == null || x.left.max.compareTo(lo) < 0) {
            intersects(q, x.right, lo, hi);
        } else {
            intersects(q, x.left, lo, hi);
        }
    }

    private boolean intersects(Key lo1, Key hi1, Key lo2, Key hi2) {
        return lo2.compareTo(hi1) <= 0 && hi2.compareTo(lo1) >= 0;
    }

    public int size() {
        return size(root);
    }

    public boolean isEmpty() {
        return root == null;
    }

    private Node<Key, Value> balance(Node<Key, Value> h) {
        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);
        h.n = 1 + size(h.left) + size(h.right);
        h.max = computeMax(h);
        return h;
    }

    private boolean isRed(Node<Key, Value> h) {
        return h != null && h.color == RED;
    }

    private Node<Key, Value> rotateLeft(Node<Key, Value> h) {
        Node<Key, Value> x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.n = h.n;
        h.n = 1 + size(h.left) + size(h.right);
        x.max = h.max;
        h.max = computeMax(h);
        return x;
    }

    private Node<Key, Value> rotateRight(Node<Key, Value> h) {
        Node<Key, Value> x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.n = h.n;
        h.n = 1 + size(h.left) + size(h.right);
        x.max = h.max;
        h.max = computeMax(h);
        return x;
    }

    private Key computeMax(Node<Key, Value> h) {
        Key max = null;
        Key maxLeft = maximum(h.left);
        Key maxRight= maximum(h.right);
        if (maxLeft != null && maxRight != null) {
            max = maxLeft.compareTo(maxRight) > 0 ? maxLeft : maxRight;
        } else if (maxLeft != null) {
            max = maxLeft;
        } else if (maxRight != null) {
            max = maxRight;
        }
        return max;
    }

    private void flipColors(Node<Key, Value> h) {
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    private int size(Node<Key, Value> h) {
        if (h == null) return 0;
        return h.n;
    }

    private Key maximum(Node<Key, Value> h) {
        if (h == null) return null;
        return h.max;
    }
}
