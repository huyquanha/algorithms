package chapter2.part2;

import chapter1.part3.Queue;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class MergeSortedQueue {
    public static Queue<Comparable> merge(Queue<Comparable> q1, Queue<Comparable> q2) {
        Queue<Comparable> result = new Queue<>();
        while (!q1.isEmpty() && !q2.isEmpty()) {
            Comparable v = q1.peek();
            Comparable w = q2.peek();
            if (less(w, v)) {
                result.enqueue(q2.dequeue());
            } else {
                result.enqueue(q1.dequeue());
            }
        }
        while (!q1.isEmpty()) {
            result.enqueue(q1.dequeue());
        }
        while (!q2.isEmpty()) {
            result.enqueue(q2.dequeue());
        }
        return result;
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Queue<Comparable> q1 = new Queue<>();
        Queue<Comparable> q2 = new Queue<>();
        int[] a1 = new int[N];
        int[] a2 = new int[N];
        for (int i = 0; i < N; i++) {
            a1[i] = StdRandom.uniform(N);
            a2[i] = StdRandom.uniform(N);
        }
        Arrays.sort(a1);
        Arrays.sort(a2);
        for (int i : a1) {
            q1.enqueue(i);
        }
        for (int i : a2) {
            q2.enqueue(i);
        }
        Queue<Comparable> result = merge(q1, q2);
        for (Comparable i : result) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
