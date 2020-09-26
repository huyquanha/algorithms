package web.week1.unionFind;

/**
 * Web Exercise
 */
public class SuccessorWithDelete {
    private class Node {
        int x;
        Node left, right;
    }

    private Node root;
    private int N;

    public SuccessorWithDelete(int N) {
        this.N = N;
        root = initNode(0, N - 1);
    }

    private Node initNode(int lo, int hi) {
        if (lo > hi) {
            return null;
        }
        int mid = lo + (hi - lo) /2;
        Node node = new Node();
        node.x = mid;
        node.left = initNode(lo, mid - 1);
        node.right = initNode(mid + 1, hi);
        return node;
    }

    public void remove(int x) {
        Node cur = root;
        Node parent = null;
        while (cur != null) {
            if (x == cur.x) {
                if (cur.left != null) {
                    // if cur has a left branch. We find the rightmost node of the left branch of cur to swap with cur
                    Node replacer = cur.left;
                    Node leftItr = replacer.right;
                    Node prev = null;
                    while (leftItr != null) {
                        prev = replacer;
                        replacer = leftItr;
                        leftItr = leftItr.right;
                    }
                    swap(cur, replacer);
                    if (prev != null) {
                        prev.right = replacer.left;
                    } else {
                        cur.left = replacer.left;
                    }
                } else if (cur.right != null) {
                    // if cur has a right branch. we find the leftmost node of the right branch of cur to swap with cur
                    Node replacer = cur.right;
                    Node rightItr = replacer.left;
                    Node prev = null;
                    while (rightItr != null) {
                        prev = replacer;
                        replacer = rightItr;
                        rightItr = rightItr.left;
                    }
                    swap(cur, replacer);
                    if (prev != null) {
                        prev.left = replacer.right;
                    } else {
                        cur.right = replacer.right;
                    }
                } else {
                    // cur doesn't have a left or right branch
                    if (parent != null) {
                        if (cur == parent.left) {
                            parent.left = null;
                        } else {
                            parent.right = null;
                        }
                    } else {
                        root = null;
                    }
                }
                N--;
                return;
            } else if (x < cur.x) {
                parent = cur;
                cur = cur.left;
            } else {
                parent = cur;
                cur = cur.right;
            }
        }
    }

    public int findSuccessor(int x) {
        Node cur = root;
        Node successor = null;
        while (cur != null) {
            if (x == cur.x) {
                return x;
            } else if (x < cur.x) {
                successor = cur;
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        if (successor != null) {
            return successor.x;
        } else {
            // no successor exists
            return -1;
        }
    }

    private void swap(Node a, Node b) {
        int tmp = a.x;
        a.x = b.x;
        b.x = tmp;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        SuccessorWithDelete swc = new SuccessorWithDelete(N);
        swc.remove(4);
        System.out.println(swc.findSuccessor(4));
        System.out.println(swc.findSuccessor(1000));
    }
}
