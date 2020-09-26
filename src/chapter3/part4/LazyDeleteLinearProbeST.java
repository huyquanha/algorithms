package chapter3.part4;

/**
 * Ex3.4.26
 * @param <Key>
 * @param <Value>
 */
public class LazyDeleteLinearProbeST<Key, Value> {
    private Key[] keys;
    private Value[] vals;
    private int N, M, delCount;

    public LazyDeleteLinearProbeST(int M) {
        this.M = M;
        keys = (Key[]) new Object[M];
        vals = (Value[]) new Object[M];
    }

    public Value get(Key k) {
        for (int i = hash(k); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(k)) return vals[i];
        }
        return null;
    }

    public boolean contains(Key k) {
        // note that get(k) can return null in 2 situations now: either k is not in the table
        // or k is in the table, but is a "deleted" key (corresponding value is null)
        // Either way, it's still correct to use contains() as a first check in delete()
        return get(k) != null;
    }

    public void put(Key k, Value v) {
        if (N + delCount >= M /2) resize(2 * M);
        int i;
        for (i = hash(k); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(k)) {
                // this includes a dangling key (the corresponding value has been lazy-deleted)
                if (vals[i] == null) {
                    // this is a previously deleted key, we want to decrease delCount
                    delCount--;
                }
                vals[i] = v;
                return;
            }
        }
        keys[i] = k;
        vals[i] = v;
        N++;
    }

    /**
     * Implements lazy-delete: only set the value to null, but keep the key
     * => don't have to re-insert the keys after the deleted position
     * Also, a subsequent put() to the same key can just replace the null value with the new value
     * The "deleted" key, if corresponding value is null, will be cleared out in the next resize() operation
     * @param k
     */
    public void delete(Key k) {
        if (!contains(k)) return;
        int i = hash(k);
        while (!keys[i].equals(k)) {
            i = (i + 1) % M;
        }
        vals[i] = null;
        N--;
        delCount++;
        if (N > 0 && N <= M / 8) resize(M /2);
    }

    private void resize(int cap) {
        LazyDeleteLinearProbeST<Key, Value> st = new LazyDeleteLinearProbeST<>(cap);
        for (int i = 0; i < M; i++) {
            // we add a check for vals[i] to make sure we don't
            // copy deleted keys over
            if (keys[i] != null && vals[i] != null) {
                st.put(keys[i], vals[i]);
            }
        }
        keys = st.keys;
        vals = st.vals;
        delCount = 0;
        M = st.M;
    }

    private int hash(Key k) {
        return (k.hashCode() & 0x7fffffff) % M;
    }

    private int superHash(Key k) {
        int t = k.hashCode() & 0x7fffffff;
        int lgM = (int) (Math.log(M) / Math.log(2));
        if (lgM < 26) {
            t = t % PrimeList.PRIMES[lgM + 5];
        }
        return t % M;
    }
}
