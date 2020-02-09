package chapter2.part4;

public class StackWithPQ<Item> {
    private class Node {
        Item item;
        int time;
    }

    private Node[] pq;
    private int N = 0;
    private int t = 0;

    public StackWithPQ(int maxN) {
        pq = (Node[]) new Object[maxN + 1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void push(Item item) {
       N++;
       Node node = new Node();
       node.item = item;
       node.time = t++;
       pq[N] = node;
       swim(N);
    }

    public Item pop() {
        exch(1, N--);
        Item item = pq[N+1].item;
        pq[N+1] = null;
        sink(1);
        return item;
    }

    private void sink(int k) {
        while (k * 2 <= N) {
            int j = k * 2;
            if (j < N && less(j, j + 1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    private void swim(int k) {
        while (k > 1 && less(k/2, k)) {
            exch(k/2, k);
            k = k/2;
        }
    }

    private boolean less(int i, int j) {
        return pq[i].time < pq[j].time;
    }

    private void exch(int i, int j) {
        Node tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
    }
}
