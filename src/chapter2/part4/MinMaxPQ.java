package chapter2.part4;

import java.util.NoSuchElementException;

/**
 * Ex2.4.29
 */
public class MinMaxPQ<Item extends Comparable<Item>> {
    private Item[] maxPQ, minPQ;
    private int N = 0;
    private int[] max, min;

    public MinMaxPQ(int maxN) {
        maxPQ = (Item[]) new Comparable[maxN + 1];
        minPQ = (Item[]) new Comparable[maxN + 1];
        //since the indices of maxPQ and minPQ runs from 1 to N, size of max and min are also N + 1 (0th slot is not used)
        max = new int[maxN + 1];
        min = new int[maxN + 1];
    }

    public void insert(Item item) {
        N++;
        maxPQ[N] = item;
        minPQ[N] = item;
        max[N] = N;
        min[N] = N;
        swimMax(N);
        swimMin(N);
    }

    public Item max() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return maxPQ[1];
    }

    public Item min() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return minPQ[1];
    }

    public Item delMax() {
        Item maxItem = maxPQ[1];
        exch(maxPQ, 1, N);
        int idxInMin = max[N];
        exch(minPQ, idxInMin, N);
        maxPQ[N] = null;
        minPQ[N] = null;
        N--;
        sinkMax(1);
        swimMin(idxInMin);
        sinkMin(idxInMin);
        return maxItem;
    }

    public Item delMin() {
        Item minItem = minPQ[1];
        exch(minPQ, 1, N);
        int idxInMax = min[N];
        exch(maxPQ, idxInMax, N);
        minPQ[N] = null;
        maxPQ[N] = null;
        N--;
        sinkMin(1);
        swimMax(idxInMax);
        sinkMax(idxInMax);
        return minItem;
    }

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    private void swimMax(int k) {
        while (k > 1 && less(maxPQ,k/2, k)) {
            exch(maxPQ, k, k/2);
            k = k/2;
        }

    }

    private void swimMin(int k) {
        while (k > 1 && less(minPQ, k, k/2)) {
            exch(minPQ, k, k/2);
            k = k/2;
        }
    }

    private void sinkMax(int k) {
        while (k * 2 <= N) {
            int j = k * 2;
            if (j < N && less(maxPQ, j,j + 1)) j++;
            if (!less(maxPQ, k, j)) break;
            exch(maxPQ, k, j);
            k = j;
        }
    }

    private void sinkMin(int k) {
        while (k * 2 <= N) {
            int j = k * 2;
            if (j < N && less(minPQ, j + 1, j)) j++;
            if (!less(minPQ, j, k)) break;
            exch(minPQ, j, k);
            k = j;
        }
    }

    private boolean less(Item[] a, int i, int j) {
        return a[i].compareTo(a[j]) < 0;
    }

    private void exch(Item[] a, int i, int j) {
        Item tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
        if (a == minPQ) {
            int iIdxMax = min[i];
            int jIdxMax = min[j];
            min[i] = jIdxMax;
            min[j] = iIdxMax;
            max[iIdxMax] = j;
            max[jIdxMax] = i;
        } else {
            int iIdxMin = max[i];
            int jIdxMin = max[j];
            max[i] = jIdxMin;
            max[j] = iIdxMin;
            min[iIdxMin] = j;
            min[jIdxMin] = i;
        }
    }

    public void showMaxPQ() {
        for (int i = 1; i <= N; i++) {
            System.out.print(maxPQ[i] + " ");
        }
        System.out.println();
    }

    public void showMinPQ() {
        for (int i = 1; i <= N; i++) {
            System.out.print(minPQ[i] + " ");
        }
        System.out.println();
    }

    public void showMax() {
        for (int i = 1; i <= N; i++) {
            System.out.print(max[i] + " ");
        }
        System.out.println();
    }

    public void showMin() {
        for (int i = 1; i <= N; i++) {
            System.out.print(min[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        MinMaxPQ<Integer> mmPQ = new MinMaxPQ<>(N);
        for (int i = 0; i < N; i++) {
            mmPQ.insert((int) (Math.random() * N));
        }
        mmPQ.showMaxPQ();
        mmPQ.showMinPQ();
        mmPQ.showMax();
        mmPQ.showMin();
        //correct until now
        System.out.println(mmPQ.delMax());
        System.out.println(mmPQ.delMin());
        mmPQ.showMaxPQ();
        mmPQ.showMinPQ();
        mmPQ.showMax();
        mmPQ.showMin();
        //correct until now
        System.out.println(mmPQ.delMax());
        System.out.println(mmPQ.delMin());
        mmPQ.showMaxPQ();
        mmPQ.showMinPQ();
        mmPQ.showMax();
        mmPQ.showMin();
        //all correct
    }
}
