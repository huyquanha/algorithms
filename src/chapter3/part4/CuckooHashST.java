package chapter3.part4;

public class CuckooHashST<Key, Value> {
    interface HashFunction<K> {
        int hash(K k);
    }

    private Key[][] keys = (Key[][]) new Object[2][];
    private Value[][] vals = (Value[][]) new Object[2][];
    private int[] n = new int[2]; // number of elements and table size respectively
    private int m;

    public CuckooHashST(int m) {
        this.m = m;
        keys[0] = (Key[]) new Object[m];
        keys[1] = (Key[]) new Object[m];
        vals[0] = (Value[]) new Object[m];
        vals[1] = (Value[]) new Object[m];
    }

    public Value get(Key k) {
        int h = hash1(k);
        if (keys[0][h] != null) {
            return vals[0][h];
        }
        h = hash2(k);
        if (keys[1][h] != null) {
            return vals[1][h];
        }
        return null;
    }

    public boolean contains(Key k) {
        return get(k) != null;
    }

    public void put(Key k, Value v) {
        if (n[0] >= m / 2 || n[1] >= m / 2) resize(2 * m);
        int tableIdx = 0;
        Object[] entry = put(k, v, tableIdx);
        while (entry != null) {
            tableIdx = 1 - tableIdx;
            entry = put((Key) entry[0], (Value) entry[1], tableIdx);
        }
    }

    public void delete(Key k) {
        if (!contains(k)) return;
        int h = hash1(k);
        if (keys[0][h] != null) {
            keys[0][h] = null;
            vals[0][h] = null;
            n[0]--;
        } else {
            h = hash2(k);
            if (keys[1][h] != null) {
                keys[1][h] = null;
                vals[1][h] = null;
                n[1]--;
            }
        }
        if (n[0] > 0 && n[1] > 0 && n[0] <= m / 8 && n[1] <= m / 8) resize(m / 2);
    }

    private Object[] put(Key k, Value v, int idx) {
        int h = idx == 0 ? hash1(k) : hash2(k);
        Object[] entryToRedo = null;
        if (keys[idx][h] != null) {
            entryToRedo = new Object[]{keys[idx][h], vals[idx][h]};
        } else {
            n[idx]++;
        }
        keys[idx][h] = k;
        vals[idx][h] = v;
        return entryToRedo;
    }

    private void resize(int cap) {
        CuckooHashST<Key, Value> tmp = new CuckooHashST<>(cap);
        for (int i = 0; i < m; i++) {
            if (keys[0][i] != null) {
                tmp.put(keys[0][i], vals[0][i]);
            }
            if (keys[1][i] != null) {
                tmp.put(keys[1][i], vals[1][i]);
            }
        }
        keys = tmp.keys;
        vals = tmp.vals;
        n = tmp.n;
        m = tmp.m;
    }

    private int hash1(Key k) {
        return (k.hashCode() & 0x7fffffff) % m;
    }

    private int hash2(Key k) {
        return ((k.hashCode() / m) & 0x7fffffff) % m;
    }
}
