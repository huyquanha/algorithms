package chapter3.part3;

import chapter1.part3.Queue;
import chapter3.OrderedST;

import java.util.NoSuchElementException;

/**
 * Sedgewick's Left-Leaning Red-Black BST, which is to be distinguished from conventional Red-Black BST
 * This implementation is supposed to be easier, less lines of code due to stricter invariant (red links can only lean left)
 * However, this does lead to asymmetry of code to handle the left and right subtree. Particularly the delete implementation
 * is a bit tricky (See javadoc for each method for implementation notes)
 * @param <Key>
 * @param <Value>
 */
public class LLRedBlackBST<Key extends Comparable<Key>, Value> implements OrderedST<Key, Value> {
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

    /**
     * INVARIANT APPLIED TO ALL DELETE METHODS BELOW:
     * The current node OR
     * - Its left child (if deleteMin()) cannot be a 2-node
     * - Its right child (if deleteMax()) cannot be a 2-node
     * - For delete(), it depends on which subtree you go down from the root to apply either deleteMin() or deleteMax() rule
     *
     * The reason for this invariant is to ensure the node we aim to delete do not end up to be a 2-node, because
     * its deletion will cause the tree to become im-balanced. If the node is part of a 3-node or 4-node, deleting it is
     * easy because the black length doesn't change.
     */

    /**
     * Implementation Note
     * - If the root is a 2-node (both the left and right child are BLACK), we need to turn its color to RED
     * so it is part of an "imaginary" RED node. This is to meet the invariant defined above, and to make sure our implementation
     * of flipColor() in moveRedLeft() behaves as expected at the root level
     * - Technically speaking, the check for !isRed(root.right) is redundant because red links can only lean left. However,
     * to make it really clear that root is a 2-node we add it in anyway
     */
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("BST underflow");
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = deleteMin(root);
        // at the end, if the root is still there after deletion, we turn its color back to BLACK
        if (!isEmpty()) root.color = BLACK;
    }

    /**
     * Implementation Note
     * - Recursively go down the left-subtree of the current node h UNLESS
     *      - h.left is null, in which case because of the invariant, we can be sure that h is part of a 3-node or 4-node and just return null
     *      to signify that we delete h
     *      - else (h.left is not null), and h.left is BLACK and h.left.left is also BLACK, we would violate the invariant if we keep going
     *      down to h.left. The reason is in the next recursion, the current node will become h.left, and its child will be h.left.left and they
     *      are both BLACK => violation
     *          - As a result, we need to use moveRedLeft to turn either h.left, or h.left.left to RED to meet the invariant
     */
    private Node deleteMin(Node h) {
        if (h.left == null) {
            return null;
        }
        if (!isRed(h.left) && !isRed(h.left.left)) {
            h = moveRedLeft(h);
        }
        h.left = deleteMin(h.left);
        return balance(h);
    }

    /**
     * Implementation Note
     * - There are 2 pre-requisites for this method to work correctly
     *      - the current node h must be RED (1)
     *      - Both h.left and h.left.left are BLACK (2)
     * - We can assume these 2 pre-requisites are both met because
     *      - (1) is enforced by the invariant that the current node must be RED
     *      - (2) is enforced by the if condition in deleteMin()
     *
     * Implementation Trace
     * - flipColors(h) to turn h to BLACK and h.left and h.right to RED
     * - If h.right.left is also RED, we have 2 successive red links on the right subtree (h.right and h.right.left)
     * - To minimize this successive red links, we do 2 more rotations and one more color flip
     *   to make h.left.left RED instead of h.left so the invariant is still met
     *
     */
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
        if (isEmpty()) throw new NoSuchElementException("BST underflow");
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    /**
     * Implementation Note
     * - Because red links always lean left, h.right will always be BLACK
     *      - To quickly meet the invariant, if h.left is RED, we can rotateRight() that red link to make h.right RED
     *
     * - Recursively go down the right-subtree of the current node h UNLESS
     *      - h.right is null, in which case because of the invariant, we can be sure that h is part of a 3-node or 4-node and just return null
     *      to signify that we delete h
     *      - else (h.right is not null), and h.right is BLACK and h.right.left is also BLACK (not h.right.right because red links lean left)
     *      we would violate the invariant if we keep going down to h.left. The reason is in the next recursion, the current node will become h.right (BLACK) and since h.right.left
     *      is BLACK, we cannot rotate it over to make h.right.right RED => h.right.right remains BLACK => violation
     *          - As a result, we need to use moveRedRight to turn either h.right, or h.right.right to RED to meet the invariant
     */
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

    /**
     * Implementation Note
     * - There are 2 pre-requisites for this method to work correctly
     *      - the current node h must be RED (1)
     *      - Both h.right and h.right.left are BLACK (2)
     * - We can assume these 2 pre-requisites are both met because
     *      - (1) is enforced by the invariant that the current node must be RED
     *      - (2) is enforced by the if condition in deleteMax()
     *
     * Implementation Trace
     * - flipColors(h) to turn h to BLACK and h.left and h.right to RED
     * - If h.left.left is also RED, we have 2 successive red links on the left subtree (h.left and h.left.left)
     * - To minimize this successive red links, we do 1 more rotations and one more color flip
     *   to make h.right.right RED instead of h.right so the invariant is still met
     */
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
        if (isEmpty()) throw new NoSuchElementException("BST underflow");
        // we first check that k is in the tree
        if (!contains(k)) return;
        if (!isRed(root.left) && !isRed(root.right)) {
            root.color = RED;
        }
        root = delete(root, k);
        if (!isEmpty()) root.color = BLACK;
    }

    /**
     * Implementation Note
     * - A normal approach would be to alternate the flow between 3 cases:
     *     - if k < h.k
     *     - if k > h.k
     *     - if k == h.k
     * - However, an implementation twist here is to group the 2 latter cases into one, because
     * they share some similar implementations
     *      - if k > h.k, we want to borrow a red link from h.left if possible to maintain the invariant that the
     *      current node is not a 2-node
     *      - if k == h.k, 2 things could happen:
     *          - h.right == null:
     *              - h is at the bottom of the tree. You would think that means h.left is also null and
     *              to delete h we can simply return null, but turns out this isn't always true.
     *              - h.left can still link to just one more RED node (with both children null)
     *              because red links don't count towards the black balance
     *              - Therefore, to confidently return null when h.right == null, we rotate h.left over if it is RED. This would
     *              make the target node h to be pushed to the right so we need one more recursive call, but at that time h.left
     *              is guaranteed to be null (because h.left is the old right child of the rotated RED node) => return null is correct
     *          - h.right != null:
     *              - h is not at the bottom of the tree. To delete h, we replace its content with min(h.right), and then
     *              call deleteMin(h.right)
     *              - Since deleteMin(h.right) also needs to maintain the invariant that the current node is not a 2-node,
     *              we should also rotate h.left over if it is RED with the aim of eventually turning h.right to a RED node
     *      - From the analysis, it seems [if (isRed(h.left)) h = rotateRight(h)] is an operation not only
     *      necessary for when k > h.k, but also when k == h.k. The operation replaces h with a smaller node (h.left)
     *      so if k >= h.k, k will also >= h.left.k and we continue traversing the correct subtree (right in this case)
     *      - Another point to note is a rotation changes the key value of h, so we should avoid storing k.compareTo(h.k)
     *      to a local variable because it might not be true after rotations. We should make a fresh compare every time
     *      - If [k.compareTo(h.k) == 0 && (h.right == null)] returns false, that means either k != h.k, or
     *      h.right != null.
     *          - If k != h.k, or more specifically, k > h.k, we need to do the moveRedRight() transformation
     *          if necessary to maintain the invariant as in deleteMax(). We don't need to check if h.right is null
     *          because contains(k) returns true, so the node with key value of k should be present in the tree =>
     *          h.right should not be null so we can continue our traversal
     *          - If k == h.k => h.right != null => the moveRedRight() transformation might also be necessary to
     *          make h.right RED so we can call deleteMin() on it
     *          - For these reasons, the conditional call to moveRedRight() is placed after
     *          [k.compareTo(h.k) == 0 && (h.right == null)] check because it is not necessary unless the check returns false
     */
    private Node delete(Node h, Key k) {
        if (k.compareTo(h.k) < 0) {
            if (!isRed(h.left) && !isRed(h.left.left)) {
                h = moveRedLeft(h);
            }
            h.left = delete(h.left, k);
        } else {
            if (isRed(h.left)) {
                h = rotateRight(h);
            }
            if (k.compareTo(h.k) == 0 && (h.right == null)) {
                return null;
            }
            if (!isRed(h.right) && !isRed(h.right.left)) {
                h = moveRedRight(h);
            }
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
        LLRedBlackBST<Integer, Integer> bst = new LLRedBlackBST<>();
        for (int i = 0; i < N; i++) {
            int k = (int) (Math.random() * N);
            bst.put(k, -k);
        }
        System.out.println(bst.is23());
        System.out.println(bst.isBalanced());
    }
}
