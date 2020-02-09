package chapter2.part2;

import chapter1.part3.Queue;

public class BottomUpQueueMerge {
    public static void sort(Comparable[] a) {
        int N = a.length;
        Queue<Queue<Comparable>> superQueue = new Queue<>();
        for (int i = 0; i < N; i++) {
            Queue<Comparable> q = new Queue<>();
            q.enqueue(a[i]);
            superQueue.enqueue(q);
        }
        while (superQueue.size() > 1) {
            Queue<Comparable> q1 = superQueue.dequeue();
            Queue<Comparable> q2 = superQueue.dequeue();
            Queue<Comparable> result = MergeSortedQueue.merge(q1, q2);
            superQueue.enqueue(result);
        }
    }
}
