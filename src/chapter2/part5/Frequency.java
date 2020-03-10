package chapter2.part5;

import edu.princeton.cs.algs4.StdIn;

import java.util.NoSuchElementException;

/**
 * Ex2.5.8
 */
public class Frequency {
    private static class Node implements Comparable<Node> {
        String item;
        int count;

        public int compareTo(Node that) {
            return this.count - that.count;
        }
    }

    private static Node[] pq;
    private static int N = 0;

    public static void main(String[] args) {
        String[] words = StdIn.readAllLines();
        //do a quick 3 way on this
        int sz = words.length;
        sort(words, 0, sz -1);
        //insert unique strings into a maximum oriented priority queue together with its count
        pq = new Node[sz + 1];
        int lo = 0;
        for (int i = 1; i < sz; i++) {
            if (!words[i].equals(words[i - 1])) {
                insert(words[i - 1], i - lo);
                lo = i;
            }
        }
        //the last word won't have a termination, so we have to insert it as well
        insert(words[lo], sz - lo);
        while (N > 0) {
            Node maxNode = delMax();
            System.out.println(maxNode.item + ": " + maxNode.count);
        }
    }

    private static Node delMax() {
        if (N == 0) {
            throw new NoSuchElementException();
        }
        Node max = pq[1];
        exch(pq, 1, N--);
        pq[N + 1] = null;
        sink(1);
        return max;
    }

    private static void insert(String word, int count) {
        Node node = new Node();
        node.item = word;
        node.count = count;
        pq[++N] = node;
        swim(N);
    }

    private static void swim(int k) {
        while (k > 1 && less(pq, k/2, k)) {
            exch(pq, k/2, k);
            k = k/2;
        }
    }

    private static void sink(int k) {
        while (k * 2 <= N) {
            int j = k * 2;
            if (j < N && less(pq, j, j + 1)) j++;
            if (!less(pq, k, j)) break;
            exch(pq, k, j);
            k = j;
        }
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        Comparable v = a[lo];
        int lt = lo, gt = hi, i = lo + 1;
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if (cmp < 0) {
                exch(a, i++, lt++);
            } else if (cmp > 0) {
                exch(a, i, gt--);
            } else {
                i++;
            }
        }
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

    private static boolean less(Comparable[] a, int i, int j) {
        return a[i].compareTo(a[j]) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
