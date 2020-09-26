package chapter3.part5;

import chapter1.part3.Queue;
import chapter3.part4.PrimeList;

/**
 * Ex3.5.4
 * @param <Value>
 */
public class HashSTint<Value> {
    private int[] keys;
    private Value[] vals;
    private int N, M;

    public HashSTint(int M) {
        this.M = M;
        keys = new int[M];
        vals = (Value[]) new Object[M];
    }

    public Value get(int key) {
        /**
         * here instead of comparing keys[i] with null, which is impossible because keys[] are int[],
         * we use vals[i] != null because a hash table don't allow null values => equally effective to know
         * when to stop.
         * This way we can support all integer keys, even 0
         */
        for (int i = hash(key); vals[i] != null; i = (i + 1) % M) {
            if (key == keys[i]) return vals[i];
        }
        return null;
    }

    public boolean contains(int key) {
        return get(key) != null;
    }

    public void put (int key, Value val) {
        if (val == null) delete(key);
        else {
            if (N >= M /2) resize(2 * M);
            int i;
            for (i = hash(key); vals[i] != null; i = (i + 1) % M) {
                if (key == keys[i]) {
                    vals[i] = val;
                    return;
                }
            }
            keys[i] = key;
            vals[i] = val;
            N++;
        }
    }

    public void delete(int key) {
        if (!contains(key)) return;
        int i = hash(key);
        while (key != keys[i]) {
            i = (i + 1) % M;
        }
        keys[i] = 0;
        vals[i] = null;
        i = (i + 1) % M;
        // re-insert all subsequent keys in the cluster
        while (vals[i] != null) {
            int keyToRedo = keys[i];
            Value valToRedo = vals[i];
            keys[i] = 0;
            vals[i] = null;
            N--;
            put(keyToRedo, valToRedo);
            i = (i + 1) % M;
        }
        N--;
        if (N > 0 && N <= M / 8) resize(M / 2);
    }

    public Iterable<Integer> keys() {
        Queue<Integer> q = new Queue<>();
        for (int i = 0; i < M; i++) {
            if (vals[i] != null) q.enqueue(keys[i]);
        }
        return q;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    private void resize(int cap) {
        HashSTint<Value> tmp = new HashSTint<>(cap);
        for (int i = 0; i < M; i++) {
            if (vals[i] != null) {
                tmp.put(keys[i], vals[i]);
            }
        }
        keys = tmp.keys;
        vals= tmp.vals;
        M = tmp.M;
    }

    private int hash(int key) {
        return (((Integer)key).hashCode() & 0x7fffffff) % M;
    }

    private int superHash(int key) {
        int t = ((Integer)key).hashCode() & 0x7fffffff;
        int lgM = (int) (Math.log(M)/Math.log(2));
        if (lgM < 26) t = t % PrimeList.PRIMES[lgM + 5];
        return t % M;
    }
}
