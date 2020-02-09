package chapter2.part4;

public class MinPQCheck {
    public static boolean isMinHeap(Comparable[] pq) {
        int N = pq.length - 1;
        for (int i = 1; i <= N/2; i++) {
            int minChild = i*2;
            if (minChild < N && less(pq, minChild + 1, minChild)) minChild++;
            if (less(pq, minChild, i)) return false;
        }
        return true;
    }

    private static boolean less(Comparable[] pq, int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        MinPQ<Integer> minPQ = new MinPQ<>(N);
        for (int i = 0; i < N; i++) {
            minPQ.insert((int) (Math.random() * N));
        }
        Comparable[] pq = minPQ.getKeys();
        for (int i = 1; i <=N; i++) {
            System.out.print(pq[i] + " ");
        }
        System.out.println();
        System.out.println(isMinHeap(pq));
    }
}
