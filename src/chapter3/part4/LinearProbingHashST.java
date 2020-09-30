package chapter3.part4;

import chapter1.part3.Queue;

public class LinearProbingHashST<Key, Value> {
    private Key[] keys;
    private Value[] vals;
    private int N, M;

    public LinearProbingHashST() {
        this(4);
    }

    public LinearProbingHashST(int M) {
        keys = (Key[]) new Object[M];
        vals = (Value[]) new Object[M];
        N = 0;
        this.M = M;
    }

    public Value get(Key k) {
        if (k == null) throw new IllegalArgumentException();
        for (int i = hash(k); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(k)) {
                return vals[i];
            }
        }
        return null;
    }

    public boolean contains(Key k) {
        return get(k) != null;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void put(Key k, Value v) {
        if (N >= M / 2) resize(2 * M);
        int i = hash(k);
        while (keys[i] != null) {
            if (keys[i].equals(k)) {
                vals[i] = v;
                return;
            }
            i = (i + 1) % M;
        }
        keys[i] = k;
        vals[i] = v;
        N++;
    }

    public void delete(Key k) {
        if (!contains(k)) return;
        int i = hash(k);
        while (!k.equals(keys[i])) {
            i = (i + 1) % M;
        }
        keys[i] = null;
        vals[i] = null;
        i = (i + 1) % M;
        // re-insert all the elements after the deleted element to avoid null holes in the middle of cluster
        while (keys[i] != null) {
            Key keyToRedo = keys[i];
            Value valToRedo = vals[i];
            keys[i] = null;
            vals[i] = null;
            N--;
            put(keyToRedo, valToRedo);
            i = (i + 1) % M;
        }
        N--;
        if (N > 0 && N <= M / 8) resize(M / 2);
    }

    /**
     * Ex3.4.19
     *
     * @return
     */
    public Iterable<Key> keys() {
        Queue<Key> q = new Queue<>();
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                q.enqueue(keys[i]);
            }
        }
        return q;
    }

    /**
     * Ex3.4.20
     * compute the search hit cost by adding up the search hit
     * cost for each key in the table, then divide by N (number of keys)
     *
     * @return
     */
    public double avgSearchHitCost() {
        double total = 0;
        for (Key k : keys()) {
            int probes = 0;
            for (int i = hash(k); keys[i] != null; i = (i + 1) % M) {
                if (keys[i].equals(k)) {
                    total += probes;
                    break;
                }
                probes++;
            }
        }
        return total / N;
    }

    /**
     * Ex3.4.21
     * we compute the average search miss cost by computing the search miss
     * cost starting at each position in the table, and then divide by M
     * the formula is 1 + N/(2*M) + sum_of_square(cluster length)/(2*M)
     *
     * @return
     */
    public double avgSearchMissCost() {
        double squareSum = 0;
        int clusterStart = -1;
        boolean startAtZero = false;
        int firstLength = -1;
        for (int i = 0; i < M; i++) {
            if (keys[i] != null && clusterStart == -1) {
                clusterStart = i;
                if (i == 0) startAtZero = true;
            }
            if (keys[i] == null && clusterStart != -1) {
                if (startAtZero) firstLength = i;
                squareSum += Math.pow((i - clusterStart), 2);
                clusterStart = -1;
            }
        }
        // end of array, check if clusterStart != -1
        if (clusterStart != -1) {
            int clusterLength = M - clusterStart;
            if (startAtZero) {
                // this mean this cluster wraps around to the start of the array
                clusterLength += firstLength;
                // we subtract this because we will be including firstLength in clusterLength in the addition below
                squareSum -= Math.pow(firstLength, 2);
            }
            squareSum += Math.pow(clusterLength, 2);
        }
        return 1 + (squareSum + N) / (2 * M);
    }

    private void resize(int cap) {
        LinearProbingHashST<Key, Value> st = new LinearProbingHashST<>(cap);
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                st.put(keys[i], vals[i]);
            }
        }
        this.keys = st.keys;
        this.vals = st.vals;
        this.M = st.M;
    }

    private int hash(Key k) {
        return (k.hashCode() & 0x7fffffff) % M;
    }
}
