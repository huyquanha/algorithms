package chapter2.part4;

/**
 * Ex2.4.3
 * @param <Key>
 */
public class OrdArrayPQ<Key extends Comparable<Key>> {
    private Key[] a;
    private int N = 0;

    public OrdArrayPQ(int maxN) {
        a = (Key[]) new Comparable[maxN];
    }

    //~N in the worst case
    public void insert(Key k) {
        a[N++] = k;
        for (int i = N - 1; i > 0 && less(i, i - 1); i--) {
            exch(i, i - 1);
        }
    }

    //constant
    public Key delMax() {
        Key max = a[--N];
        a[N + 1] = null;
        return max;
    }

    private boolean less(int i, int j) {
        return a[i].compareTo(a[j]) < 0;
    }

    private void exch(int i, int j) {
        Key tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}

