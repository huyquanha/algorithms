package chapter3.part4;

import chapter1.part3.Queue;

public class DoubleHashingLinearProbingHashST<Value> {
    private Character[] keys;
    private Value[] vals;
    private int N, M;

    public DoubleHashingLinearProbingHashST(int M) {
        keys = new Character[M];
        vals = (Value[]) new Object[M];
        this.M = M;
    }

    public Value get(Character k) {
        for (int i = hash1(k); keys[i] != null; i = (i + hash2(k)) % M) {
            if (keys[i].equals(k)) {
                return vals[i];
            }
        }
        return null;
    }

    public boolean contains(Character k) {
        return get(k) != null;
    }

    public void put(Character k, Value v) {
        if (N >= M / 2) resize(2 * M);
        int i;
        for (i = hash1(k); keys[i] != null; i = (i + hash2(k)) % M) {
            if (keys[i].equals(k)) {
                vals[i] = v;
                return;
            }
        }
        keys[i] = k;
        vals[i] = v;
        N++;
    }

    /**
     * Ex3.4.29
     * @return
     */
    public void delete(Character k) {
        if (!contains(k)) return;
        int i;
        for (i = hash1(k); keys[i] != null; i = (i + hash2(k)) % M) {
            if (keys[i].equals(k)) {
                keys[i] = null;
                vals[i] = null;
                N--;
                break;
            }
        }
        i = (i + hash2(k)) % M;
        while (keys[i] != null) {
            Character keyToRedo = keys[i];
            Value valToRedo = vals[i];
            keys[i] = null;
            vals[i] = null;
            N--;
            put(keyToRedo, valToRedo);
            i = (i + hash2(k)) % M;
        }
        if (N > 0 && N <= M / 8) resize(M / 2);
    }

    public Iterable<Character> keys() {
        Queue<Character> q = new Queue<>();
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) q.enqueue(keys[i]);
        }
        return q;
    }

    public double avgSearchHitCost() {
        double total = 0;
        for (Character k : keys()) {
            int probes = 0;
            for (int i = hash1(k); keys[i] != null; i = (i + hash2(k)) % M) {
                probes++;
                if (keys[i].equals(k)) {
                    total += probes;
                    break;
                }
            }
        }
        return total / N;
    }

    // TODO: figuring out how to do this
    public double avgSearchMissCost() {
        // we know that the keys are letters in the alphabet
        return 0;
    }

    /**
     * Use PrimeList.PRIMES to replace the new capacity with the a prime number larger than it
     * so we make sure M is always a prime
     * @param cap
     */
    private void resize(int cap) {
        int binLg = (int) (Math.log(cap) / Math.log(2));
        cap = PrimeList.PRIMES[binLg + 1];
        DoubleHashingLinearProbingHashST<Value> tmp = new DoubleHashingLinearProbingHashST<>(cap);
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                tmp.put(keys[i], vals[i]);
            }
        }
        keys = tmp.keys;
        vals = tmp.vals;
        M = tmp.M;
    }

    private int hash1(Character k) {
        return (11 * (Character.toLowerCase(k) - 'a' + 1)) % M;
    }

    // this cannot hash to 0, otherwise we have an infinite loop
    // the hash should also be relatively prime to M (can be met by making sure M is prime)
    private int hash2(Character k) {
        int h = (17 * (Character.toLowerCase(k) - 'a' + 1)) % M;
        if (h == 0) h++;
        return h;
    }

    public static void main(String[] args) {
        String input = "EASYQUTION";
        DoubleHashingLinearProbingHashST<Integer> st = new DoubleHashingLinearProbingHashST<>(11);
        for (int i = 0; i < input.length(); i++) {
            st.put(input.charAt(i), i);
        }
        System.out.println("Average search hit cost: " + st.avgSearchHitCost());
    }
}
