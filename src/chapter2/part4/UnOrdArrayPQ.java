package chapter2.part4;

/**
 * Ex2.4.3
 * @param <Key>
 */
public class UnOrdArrayPQ<Key extends Comparable<Key>> {
    private Key[] a;
    private int N = 0;

    public UnOrdArrayPQ(int maxN) {
        a = (Key[]) new Comparable[maxN];
    }

    //constant
    public void insert(Key k) {
        a[N++] = k;
    }

    //~N
    public Key delMax() {
        int maxIdx = 0;
        for (int i = 1; i < N; i++) {
            if (a[i].compareTo(a[maxIdx]) > 0) {
                maxIdx = i;
            }
        }
        exch(maxIdx, N - 1);
        N--;
        Key max = a[N];
        a[N] = null;
        return max;
    }

    private void exch(int i, int j) {
        Key tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
