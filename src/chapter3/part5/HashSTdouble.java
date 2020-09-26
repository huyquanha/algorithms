package chapter3.part5;

import chapter1.part3.Queue;
import chapter3.part4.PrimeList;

/**
 * Ex3.5.4
 * @param <Value>
 */
public class HashSTdouble<Value> {
    private double[] keys;
    private Value[] vals;
    private int N, M;

    public HashSTdouble(int M) {
        this.M = M;
        keys = new double[M];
        vals = (Value[]) new Object[M];
    }

    public Value get(double key) {
        for (int i = hash(key); vals[i] != null; i = (i + 1) % M) {
            if (key == keys[i]) return vals[i];
        }
        return null;
    }

    public boolean contains(double key) {
        return get(key) != null;
    }

    public void put(double key, Value val) {
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

    public void delete(double key) {
        if (!contains(key)) return;
        int i = hash(key);
        while (key != keys[i]) i = (i + 1) % M;
        // eventually key will be == keys[i] because contains return true
        keys[i] = 0;
        vals[i] = null;
        i = (i + 1) % M;
        while (vals[i] != null) {
            //re-insert the subsequent keys in the cluster
            double keyToRedo = keys[i];
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

    public int size() {
        return N;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public Iterable<Double> keys() {
        Queue<Double> q = new Queue<>();
        for (int i = 0; i < M; i++) {
            if (vals[i] != null) q.enqueue(keys[i]);
        }
        return q;
    }

    private void resize(int cap) {
        HashSTdouble<Value> tmp = new HashSTdouble<>(cap);
        for (int i = 0; i < M; i++) {
            if (vals[i] != null) tmp.put(keys[i], vals[i]);
        }
        keys = tmp.keys;
        vals= tmp.vals;
        M = tmp.M;
    }

    private int hash(double key) {
        return (((Double) key).hashCode() & 0x7fffffff) & M;
    }

    private int superHash(double key) {
        int t = ((Double) key).hashCode() & 0x7fffffff;
        int lgM = (int) (Math.log(M)/Math.log(2));
        if (lgM < 26) t = t % PrimeList.PRIMES[lgM + 5];
        return t % M;
    }
}
