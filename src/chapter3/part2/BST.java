package chapter3.part2;

import chapter1.part3.Queue;
import chapter1.part3.Stack;
import chapter3.OrderedST;

import java.util.NoSuchElementException;

public class BST<Key extends Comparable<Key>, Value> implements OrderedST<Key, Value> {
    private class Node {
        private Key key;
        private Value val;
        private Node left, right, pred, succ;
        private int n;
        // Ex3.2.6: use a variable for height
        private int h;
        // Ex3.2.7: use a variable for path length rooted at this node
        private int pl;
        // Ex 3.2.36: used to mark a Node as visited
        private boolean visited;

        public Node(Key key, Value val, int n, Node pred, Node succ) {
            this.key = key;
            this.val = val;
            this.n = n;
            h = 0;
            pl = 0;
            this.pred = pred;
            this.succ = succ;
            visited = false;
        }
    }

    private Node root, lastAccessed;

    public int size() {
        return size(root);
    }

    public boolean isEmpty() {
        return root == null; // or size(root) == 0
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.n;
    }

    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        if (lastAccessed != null && key.compareTo(lastAccessed.key) == 0) {
            return lastAccessed.val;
        }
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            lastAccessed = x;
            return x.val;
        } else if (cmp < 0) {
            return get(x.left, key);
        } else {
            return get(x.right, key);
        }
    }

    public void put(Key key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        if (lastAccessed != null && key.compareTo(lastAccessed.key) == 0) {
            lastAccessed.val = val;
            return;
        }
        root = put(root, key, val, root == null ? null : root.pred, root == null ? null : root.succ);
    }

    private Node put(Node x, Key key, Value val, Node pred, Node succ) {
        if (x == null) {
            Node newNode = new Node(key, val, 1, pred, succ);
            if (pred != null) {
                pred.succ = newNode;
            }
            if (succ != null) {
                succ.pred = newNode;
            }
            return newNode;
        }
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
            // search hit
            lastAccessed = x;
            x.val = val;
        } else if (cmp < 0) {
            // search miss, search the left
            // after the new node is inserted, its predecessor will be x.pred, and its successor will be x
            x.left = put(x.left, key, val, x.pred, x);
        } else {
            // search miss, search the right
            x.right = put(x.right, key, val, x, x.succ);
        }
        x.n = size(x.left) + size(x.right) + 1;
        // Ex3.2.6: maintaining height
        x.h = Math.max(height(x.left), height(x.right)) + 1;
        // Ex3.2.7: maintaining path length
        x.pl = pathLength(x.left) + pathLength(x.right) + size(x) - 1;
        return x;
    }

    /**
     * find minimum key
     *
     * @return
     */
    public Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException("tree is empty");
        }
        return min(root).key;
    }

    /**
     * find minimum key rooted at x
     *
     * @param x
     * @return
     */
    private Node min(Node x) {
        if (x == null) return null;
        if (x.left == null) return x;
        return min(x.left);
    }

    /**
     * find maximum key
     *
     * @return
     */
    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("tree is empty");
        }
        return max(root).key;
    }

    /**
     * find maximum key rooted at x
     *
     * @param x
     * @return
     */
    private Node max(Node x) {
        if (x == null) return null;
        if (x.right == null) return x;
        return max(x.right);
    }

    /**
     * get floor of key: largest key in BST that is <= key
     *
     * @param key
     * @return
     */
    public Key floor(Key key) {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node x = floor(root, key);
        if (x == null) return null;
        return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            // key < x.key => floor(key) has to be on the left subtree
            return floor(x.left, key);
        } else if (cmp > 0) {
            // key > x.key => floor(key) could be x.key, or could be another key on the right subtree
            Node t = floor(x.right, key);
            if (t != null) {
                return t;
            } else {
                return x;
            }
        } else {
            // key == x.key => return x immediately
            return x;
        }
    }

    /**
     * get ceiling of key: smallest key in BST that is >= key
     *
     * @param key
     * @return
     */
    public Key ceiling(Key key) {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node x = ceiling(root, key);
        if (x == null) return null;
        return x.key;
    }

    private Node ceiling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            // key < x.key. ceiling of key could be x or another node on the left subtree
            Node t = ceiling(x.left, key);
            if (t != null) {
                return null;
            } else {
                return x;
            }
        } else if (cmp > 0) {
            // key > x.key. ceiling of key definitely has to be on the right subtree
            return ceiling(x.right, key);
        } else {
            return x;
        }
    }

    /**
     * select key of rank k (has precisely k smaller keys in the tree)
     *
     * @param k
     * @return
     */
    public Key select(int k) {
        Node x = select(root, k);
        if (x == null) return null;
        return x.key;
    }

    private Node select(Node x, int k) {
        if (x == null) return null;
        int t = size(x.left);
        if (t > k) {
            // look in the left subtree
            return select(x.left, k);
        } else if (t < k) {
            // look in the right subtree
            return select(x.right, k - t - 1);
        } else {
            return x;
        }
    }

    /**
     * return rank of key, or the number of keys in BST that are smaller than key
     *
     * @param key
     * @return
     */
    public int rank(Key key) {
        return rank(root, key);
    }

    private int rank(Node x, Key key) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        int t = size(x.left);
        if (cmp == 0) {
            return t;
        } else if (cmp < 0) {
            return rank(x.left, key);
        } else {
            return t + 1 + rank(x.right, key);
        }
    }

    public void deleteMin() {
        if (isEmpty()) {
            throw new UnsupportedOperationException();
        }
        root = deleteMin(root);
    }

    /**
     * @param x
     * @return
     */
    private Node deleteMin(Node x) {
        if (x.left == null) {
            Node minRight = min(x.right);
            if (minRight != null) {
                minRight.pred = x.pred;
            }
            if (x.pred != null) {
                x.pred.succ = minRight;
            }
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.n = size(x.left) + size(x.right) + 1;
        // Ex3.2.6
        x.h = Math.max(height(x.left), height(x.right)) + 1;
        // Ex3.2.7
        x.pl = pathLength(x.left) + pathLength(x.right) + size(x) - 1;
        return x;
    }

    public void deleteMax() {
        if (isEmpty()) {
            throw new UnsupportedOperationException();
        }
        root = deleteMax(root);
    }

    /**
     * @param x
     * @return
     */
    private Node deleteMax(Node x) {
        if (x.right == null) {
            Node maxLeft = max(x.left);
            if (maxLeft != null) {
                maxLeft.succ = x.succ;
            }
            if (x.succ != null) {
                x.succ.pred = maxLeft;
            }
            return x.left;
        }
        x.right = deleteMax(x.right);
        x.n = size(x.left) + size(x.right) + 1;
        // Ex3.2.6
        x.h = Math.max(height(x.left), height(x.right)) + 1;
        // Ex3.2.7
        x.pl = pathLength(x.left) + pathLength(x.right) + size(x) - 1;
        return x;
    }

    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
        if (isEmpty()) {
            throw new UnsupportedOperationException();
        }
        root = delete(root, key, null);
    }

    private Node delete(Node x, Key key, Node parent) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = delete(x.left, key, x);
        } else if (cmp > 0) {
            x.right = delete(x.right, key, x);
        } else {
            if (x.right == null) {
                Node maxLeft = max(x.left);
                if (maxLeft != null) {
                    maxLeft.succ = x.succ;
                }
                if (x.succ != null) {
                    x.succ.pred = maxLeft;
                }
                return x.left;
            } else if (x.left == null) {
                Node minRight = min(x.right);
                if (minRight != null) {
                    minRight.pred = x.pred;
                }
                if (x.pred != null) {
                    x.pred.succ = minRight;
                }
                return x.right;
            } else {
                // saves the node to be deleted (x) in t
                Node t = x;
                // point x to the smallest node on the right subtree (the successor of x)
                x = min(t.right);
                // x.right should point to the right subtree after deleting the min element
                x.right = deleteMin(t.right);
                Node newMinRight = min(x.right);
                if (newMinRight != null) {
                    newMinRight.pred = x;
                }
                x.succ = newMinRight;
                // x.left should point to the original left subtree (smaller than both x and the successor of x)
                x.left = t.left;
                Node newMaxLeft = max(x.left);
                if (newMaxLeft != null) {
                    newMaxLeft.succ = x;
                }
                x.pred = newMaxLeft;
            }
        }
        x.n = size(x.left) + size(x.right) + 1;
        // Ex3.2.6
        x.h = Math.max(height(x.left), height(x.right)) + 1;
        // Ex3.2.7
        x.pl = pathLength(x.left) + pathLength(x.right) + size(x) - 1;
        return x;
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> q = new Queue<>();
        keys(q, root, lo, hi);
        return q;
    }

    public int size(Key lo, Key hi) {
        return size(root, lo, hi);
    }

    private int size(Node x, Key lo, Key hi) {
        if (x == null) return 0;
        int size = 0;
        int cmpLo = lo.compareTo(x.key);
        int cmpHi = hi.compareTo(x.key);
        if (cmpLo < 0) {
            size += size(x.left, lo, hi);
        }
        if (cmpLo <= 0 && cmpHi >= 0) {
            size++;
        }
        if (cmpHi > 0) {
            size += size(x.right, lo, hi);
        }
        return size;
    }

    private void keys(Queue<Key> q, Node x, Key lo, Key hi) {
        if (hi.compareTo(lo) < 0) {
            return;
        }
        if (x == null) return;
        int cmpLo = lo.compareTo(x.key);
        int cmpHi = hi.compareTo(x.key);
        if (cmpLo < 0) {
            // if cmpLo >=0 or lo >= x.key, we don't bother enqueuing the left subtree of x because they are out of range
            keys(q, x.left, lo, hi);
        }
        if (cmpLo <= 0 && cmpHi >= 0) {
            // lo <= x.key <= hi, otherwise we won't bother enqueuing x.key
            q.enqueue(x.key);
        }
        if (cmpHi > 0) {
            // if cmpHi <= 0 or hi <= x.key, we don't bother enqueuing the right subtree of x because they are out of range
            keys(q, x.right, lo, hi);
        }
    }

    /**
     * Ex3.2.36: implement keys() non-recursively using a Stack with maximum size = tree height
     * We use visited to check whether cur.left has been visited or not to skip adding cur.left infinitely to stack
     *
     * @return
     */
    public Iterable<Key> iterKeys() {
        return iterKeys(min(), max());
    }

    private Iterable<Key> iterKeys(Key lo, Key hi) {
        Queue<Key> q = new Queue<>();
        if (hi.compareTo(lo) < 0 || isEmpty()) {
            return q;
        }
        Stack<Node> s = new Stack<>();
        s.push(root);
        while (!s.isEmpty()) {
            Node cur = s.peek();
            int cmpLo = lo.compareTo(cur.key);
            int cmpHi = hi.compareTo(cur.key);
            if (cur.left != null && !cur.left.visited && cmpLo < 0) {
                // if cmpLo >= 0 or lo >= cur.key, we don't bother visiting cur.left because they are out of range
                s.push(cur.left);
            } else {
                // cur.left == null or cur.left visited. cur is to be added to the queue
                cur = s.pop();
                cur.visited = true;
                if (cmpLo <= 0 && cmpHi >= 0) {
                    q.enqueue(cur.key);
                }
                if (cur.right != null && cmpHi > 0) {
                    // if cmpHi <=0 or hi <= cur.key, we don't bother visiting cur.right because they are out of range
                    s.push(cur.right);
                }
            }
        }
        return q;
    }

    /**
     * Ex3.2.37
     */
    public void printLevel() {
        printLevel(root);
    }

    public void printLevel(Node x) {
        if (x == null) {
            return;
        }
        Queue<Node> q = new Queue<>();
        q.enqueue(x);
        while (!q.isEmpty()) {
            Node cur = q.dequeue();
            System.out.println(cur.key);
            if (cur.left != null) {
                q.enqueue(cur.left);
            }
            if (cur.right != null) {
                q.enqueue(cur.right);
            }
        }
    }

    /**
     * Ex3.2.6 recursive implementation
     *
     * @param
     */
    public int recHeight() {
        return recHeight(root);
    }

    private int recHeight(Node x) {
        if (x == null) return -1;
        // the height of a node is the maximum of the height of its 2 children + 1
        // this ensures that a leaf node (x.left and x.right == null) has a height of 0
        return Math.max(recHeight(x.left), recHeight(x.right)) + 1;
    }

    /**
     * Ex3.2.6 implementation for extra variable h in Node
     *
     * @return
     */
    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return x.h;
    }

    /**
     * Ex3.2.7 recursive implementation
     *
     * @param
     */
    public double recAvgCompares() {
        return recAvgCompares(root);
    }

    private double recAvgCompares(Node x) {
        if (x == null) return 0;
        return recPathLength(x) / size(x) + 1;
    }

    private int recPathLength(Node x) {
        if (x == null) return 0;
        // recPathLength(x.left) or recPathLength(x.right) is the internal path length of the 2 sub-trees
        // size(x) - 1 accounts for the extra link from every other node except x to x
        return recPathLength(x.left) + recPathLength(x.right) + (size(x) - 1);
    }

    /**
     * Ex3.2.7 non-recursive implementation (use field pl in Node)
     *
     * @param
     */
    public double avgCompares() {
        return avgCompares(root);
    }

    private double avgCompares(Node x) {
        if (x == null) return 0;
        return pathLength(x) / size(x) + 1;
    }

    private int pathLength(Node x) {
        if (x == null) return 0;
        return x.pl;
    }

    /**
     * Ex3.2.13
     *
     * @param
     */
    public Value iterGet(Key key) {
        Node cur = root;
        while (cur != null) {
            int cmp = key.compareTo(cur.key);
            if (cmp == 0) {
                return cur.val;
            } else if (cmp < 0) {
                // key is to the left of cur
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        return null;
    }

    public void iterPut(Key key, Value val) {
        Node cur = root;
        Stack<Node> predecessors = new Stack<>();
        while (cur != null) {
            int cmp = key.compareTo(cur.key);
            if (cmp == 0) {
                cur.val = val;
                return;
            } else if (cmp < 0) {
                // key is to the left of cur
                predecessors.push(cur);
                cur = cur.left;
            } else {
                predecessors.push(cur);
                cur = cur.right;
            }
        }
        // when we come here, cur == null and hence an insert is required
        // if predecessors is empty, that means root == null
        if (predecessors.isEmpty()) {
            root = new Node(key, val, 1, null, null);
        } else {
            Node last = predecessors.peek();
            if (key.compareTo(last.key) < 0) {
                // insert to left
                last.left = new Node(key, val, 1, last.pred, last);
                Node pred = last.pred;
                if (pred != null) {
                    pred.succ = last.left;
                }
                last.pred = last.left;
            } else {
                // insert to right
                last.right = new Node(key, val, 1, last, last.succ);
                Node succ = last.succ;
                if (succ != null) {
                    succ.pred = last.right;
                }
                last.succ = last.right;
            }
            while (!predecessors.isEmpty()) {
                Node parent = predecessors.pop();
                parent.n = size(parent.left) + size(parent.right) + 1;
            }
        }
    }

    /**
     * Ex3.2.14
     *
     * @param
     */
    public Key iterMin() {
        if (root == null) return null;
        Node cur = root;
        while (cur.left != null) {
            cur = cur.left;
        }
        return cur.key;
    }

    public Key iterMax() {
        if (root == null) return null;
        Node cur = root;
        while (cur.right != null) {
            cur = cur.right;
        }
        return cur.key;
    }

    // largest key smaller than or equal to key
    public Key iterFloor(Key key) {
        Node cur = root;
        Stack<Node> candidates = new Stack<>();
        while (cur != null) {
            int cmp = key.compareTo(cur.key);
            if (cmp < 0) {
                // key < cur.key => has to search to the left
                cur = cur.left;
            } else if (cmp > 0) {
                // key > cur.key => floor could be cur or another node on cur.right. We add cur as candidate
                candidates.push(cur);
                cur = cur.right;
            } else {
                // key == cur.key
                return cur.key;
            }
        }
        if (candidates.isEmpty()) {
            return null;
        } else {
            // the top one will be the largest key that is <= key
            return candidates.pop().key;
        }
    }

    // smallest key larger than or equal to key
    public Key iterCeiling(Key key) {
        Node cur = root;
        Stack<Node> candidates = new Stack<>();
        while (cur != null) {
            int cmp = key.compareTo(cur.key);
            if (cmp > 0) {
                // key > cur.key => has to search to the right
                cur = cur.right;
            } else if (cmp < 0) {
                // key < cur.key => ceiling could be cur or another node on cur.left. We add cur as candidate
                candidates.push(cur);
                cur = cur.left;
            } else {
                // key == cur.key
                return cur.key;
            }
        }
        if (candidates.isEmpty()) {
            return null;
        } else {
            // the top one will be the smallest key that is <= key
            return candidates.pop().key;
        }
    }

    // find the number of keys in BST that are smaller than key
    public int iterRank(Key key) {
        Node cur = root;
        int rank = 0;
        while (cur != null) {
            int cmp = key.compareTo(cur.key);
            int t = size(cur.left);
            if (cmp == 0) {
                rank += t;
                return rank;
            } else if (cmp < 0) {
                // key < cur.key
                cur = cur.left;
            } else {
                // key > cur.key
                rank += t + 1;
                cur = cur.right;
            }
        }
        return rank;
    }

    public Key iterSelect(int k) {
        Node cur = root;
        while (cur != null) {
            int t = size(cur.left);
            if (t == k) {
                return cur.key;
            } else if (t > k) {
                // that means the key we are looking for is in the left subtree of cur
                cur = cur.left;
            } else {
                // that means the key we are looking for is in the right subtree of cur
                k = k - t - 1;
                cur = cur.right;
            }
        }
        return null;
    }

    /**
     * Ex3.2.21
     *
     * @param
     */
    public Key randomKey() {
        int rank = (int) (Math.random() * root.n);
        return select(rank);
    }

    /**
     * Ex3.2.29: binary tree check
     *
     * @param
     */
    public boolean isBinaryTree() {
        return isBinaryTree(root);
    }

    public boolean isBinaryTree(Node x) {
        if (x == null) {
            return true;
        }
        if (x.n != size(x.left) + size(x.right) + 1) {
            return false;
        }
        return isBinaryTree(x.left) && isBinaryTree(x.right);
    }

    /**
     * Ex3.2.30
     *
     * @param
     */
    public boolean isOrdered() {
        Key min = min();
        Key max = max();
        return isOrdered(root, min, max);
    }

    public boolean isOrdered(Node x, Key min, Key max) {
        if (x == null) return true;
        int cmpLo = min.compareTo(x.key);
        int cmpHi = max.compareTo(x.key);
        if (cmpLo > 0 || cmpHi < 0) {
            // meaning min > x.key || max < x.key
            return false;
        }
        return isOrdered(x.left, min, x.key) && isOrdered(x.right, x.key, max);
    }

    /**
     * Ex3.2.31
     *
     * @param
     */
    public boolean hasNoDuplicates() {
        return hasNoDuplicates(root);
    }

    public boolean hasNoDuplicates(Node x) {
        if (x == null) return true;
        // because the previous test has passed, bst ordering is hold, an in-order traversal
        // will list the elements in order
        Iterable<Key> keys = keys();
        Key prev = null;
        for (Key key : keys) {
            if (prev != null && key.compareTo(prev) == 0) {
                return false;
            }
            prev = key;
        }
        return true;
    }

    /**
     * Ex3.2.32
     *
     * @param
     */
    public boolean isBST() {
        return isBST(root);
    }

    public boolean isBST(Node x) {
        if (!isBinaryTree(x)) return false;
        if (!isOrdered(x, min(), max())) return false;
        if (!hasNoDuplicates(x)) return false;
        return true;
    }

    /**
     * Ex3.2.34
     *
     * @param
     */
    public Key next(Key key) {
        return null;
    }

    public static void main(String[] args) {
        BST<String, Integer> bst = new BST<>();
        String input = "EASYQUESTION";
        for (int i = 0; i < input.length(); i++) {
            bst.iterPut(input.substring(i, i + 1), i);
        }
        // Ex 3.2.36
        for (String key : bst.iterKeys("E", "P")) {
            System.out.print(key + " ");
        }
        System.out.println();
        bst.printLevel();
        bst.deleteMin();
        bst.deleteMax();
        bst.deleteMin();
        bst.deleteMax();
        System.out.println(bst.isBinaryTree());
        System.out.println(bst.isOrdered());
        System.out.println(bst.hasNoDuplicates());
        System.out.println(bst.isBST());
    }
}
