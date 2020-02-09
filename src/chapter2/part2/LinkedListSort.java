package chapter2.part2;

import chapter1.part3.Queue;
import edu.princeton.cs.algs4.In;

/**
 * Ex 2.2.17
 */
public class LinkedListSort {
    private static class Node {
        Comparable item;
        Node next;
    }

    public static Node sort(Node first) {
        if (first == null) {
            return null;
        } else if (first.next == null) {
            return first;
        }
        Node result = null;
        Node before = null, lo = first, mid = null, hi = null;
        Node itr = first;
        boolean foundOne = false;
        Queue<Node> his = new Queue<>();
        while (itr.next != null) {
            if (less(itr.next, itr)) {
                if (!foundOne) {
                    mid = itr;
                    foundOne = true;
                    itr = itr.next;
                } else {
                    hi = itr;
                    Node[] tails = merge(lo, mid, hi);
                    Node left = tails[0];
                    Node right = tails[1];
                    if (result == null) {
                        result = left;
                    } else {
                        before.next = left;
                    }
                    his.enqueue(right);
                    before = right;
                    lo = right.next;
                    mid = null;
                    hi = null;
                    foundOne = false;
                    itr = lo;
                }
            } else {
                itr = itr.next;
            }
            if (itr.next == null) {
                hi = itr;
                if (foundOne) {
                    Node[] tails = merge(lo, mid, hi);
                    if (result == null) {
                        result = tails[0];
                    } else {
                        before.next = tails[0];
                    }
                    his.enqueue(tails[1]);
                } else {
                    his.enqueue(hi);
                }
                break;
            }
        }
        lo = result;
        before = null;
        Queue<Node> tmp = new Queue<>();
        while (his.size() > 1) {
            mid = his.dequeue();
            hi = his.dequeue();
            Node[] tails = merge(lo, mid, hi);
            Node left = tails[0];
            Node right = tails[1];
            if (before == null) {
                result = left;
            } else {
                before.next = left;
            }
            tmp.enqueue(right);
            before = right;
            lo = right.next;
            if (his.size() < 2) {
                if (his.size() == 1) {
                    tmp.enqueue(his.dequeue());
                }
                his = tmp;
                tmp = new Queue<>();
                before = null;
                lo = result;
            }
        }
        return result;
    }

    private static Node[] merge(Node lo, Node mid, Node hi) {
        Node result = null;
        Node i = lo, j = mid.next, k = null;
        Node afterFirst = mid.next;
        Node afterSec = hi.next;
        while (i != afterFirst && j != afterSec) {
            if (less(j,i)) {
                if (result == null) {
                    result = j;
                    k = result;
                } else {
                    k.next = j;
                    k = k.next;
                }
                j = j.next;
            } else {
                if (result == null) {
                    result = i;
                    k = result;
                } else {
                    k.next = i;
                    k = k.next;
                }
                i = i.next;
            }
        }
        while (i != afterFirst) {
            k.next = i;
            k = k.next;
            i = i.next;
        }
        while (j != afterSec) {
            k.next = j;
            k = k.next;
            j = j.next;
        }
        k.next = afterSec;
        return new Node[] {result, k};
    }

    private static boolean less(Node v, Node w) {
        return v.item.compareTo(w.item) < 0;
    }

    public static void main(String[] args) {
        In in = new In("testStrings1.txt");
        String[] words = in.readAllStrings();
        Node first = null, cur = null;
        for (String w : words) {
            Node tmp = new Node();
            tmp.item = w;
            tmp.next = null;
            if (first == null) {
                first = tmp;
                cur = first;
            } else {
                cur.next = tmp;
                cur = cur.next;
            }
        }
        Node result = sort(first);
        cur = result;
        while (cur != null) {
            System.out.println(cur.item);
            cur = cur.next;
        }
    }
}
