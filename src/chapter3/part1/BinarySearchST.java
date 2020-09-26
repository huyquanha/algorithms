package chapter3.part1;

import chapter3.OrderedST;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchST<Key extends Comparable<Key>, Value> implements OrderedST<Key, Value> {
    // protected so InterpolationSearchST can reference them
    protected Key[] keys;
    protected Value[] vals;
    // add caching to most recent accessed index
    protected int N, compares, mostRecent;

    public BinarySearchST() {
        keys = (Key[]) new Comparable[2];
        vals = (Value[]) new Object[2];
        mostRecent = -1;
    }

    public int rank(Key key) {
        checkKey(key);
        if (mostRecent != -1 && keys[mostRecent].compareTo(key) == 0) {
            return mostRecent;
        }
        /**
         * Ex3.1.28
         */
        if (key.compareTo(keys[N - 1]) > 0) {
            // key is larger than all key in the table
            return N;
        }
        int lo = 0, hi = N - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo)/2;
            int cmp = key.compareTo(keys[mid]);
            compares++;
            if (cmp < 0) {
                hi = mid - 1;
            } else if (cmp > 0) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        // if key is not in the table, then lo is the highest key in the table smaller than key => we return lo
        // note that if key > all keys in table, rank = lo = hi + 1 = N
        // if key < all keys in table, rank = lo = 0
        return lo;
    }

    public Value get(Key key) {
        checkKey(key);
        if (isEmpty()) return null;
        int rank = rank(key);
        if (rank < N && keys[rank].compareTo(key) == 0) {
            // key is in the table
            return vals[rank];
        } else {
            // key is not in the table
            return null;
        }
    }

    public boolean contains(Key key) {
        return OrderedST.super.contains(key);
    }

    public void put(Key key, Value val) {
        int rank = rank(key);
        if (rank < N && keys[rank].compareTo(key) == 0) {
            // key is in the table => update
            if (val == null) {
                delete(key);
            } else {
                vals[rank] = val;
            }
        } else {
            // key is not in the table
            // we move everything from rank one step to the right to give place to key
            // if val is null, then we don't bother inserting the key
            if (val != null) {
                if (N == keys.length) {
                    resize(N * 2);
                }
                for (int i = N; i > rank; i--) {
                    keys[i] = keys[i-1];
                    vals[i] = vals[i-1];
                }
                keys[rank] = key;
                vals[rank] = val;
                N++;
            }
        }
        print();
        System.out.println(getCompares());
        compares++;
    }

    public void delete(Key key) {
        int rank = rank(key);
        if (rank < N && keys[rank].compareTo(key) == 0) {
            // key is in the table
            // we need to move all the entries above rank one step to the left, effectively overwriting rank
            for (int i = rank; i + 1 < N; i++) {
                keys[i] = keys[i+1];
                vals[i] = vals[i+1];
            }
            keys[N - 1] = null;
            vals[N -1] = null;
            N--;
            if (N > 0 && N == keys.length / 4) {
                resize(keys.length /2);
            }
        }
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public Key min() {
        if (isEmpty()) {
            return null;
        }
        return keys[0];
    }

    public Key max() {
        if (isEmpty()) {
            return null;
        }
        return keys[N - 1];
    }

    public Key floor(Key key) {
        // rank(key) is either pointing to the key's index (if it's in the table), or index of ceil(key)
        int rank = rank(key);
        if (rank < N && keys[rank].compareTo(key) == 0) {
            // key is in the table
            return keys[rank];
        } else {
            // key is not in the table
            if (rank == 0) {
                // key is smaller than all keys in the table => floor(key) doesn't exist
                return null;
            } else {
                // since key is between keys[rank-1] and keys[rank], floor(key) = keys[rank-1]
                return keys[rank -1];
            }
        }
    }

    public Key ceiling(Key key) {
        int rank = rank(key);
        if (rank < N ) {
            return keys[rank]; // because key <= keys[rank(key)]
        } else {
            // this is if rank == N, or key is larger than all keys in the table
            return null;
        }
    }

    public Key select(int k) {
        if (k < 0 || k >= N) {
            return null;
        }
        return keys[k];
    }

    public int size(Key lo, Key hi) {
        if (lo.compareTo(hi) > 0) {
            return 0;
        }
        // key <= keys[rank(key)] => lo <= keys[rank(lo)] and hi <= keys[rank(hi)]
        int r1 = rank(lo);
        int r2 = rank(hi);
        if (contains(hi)) {
            return r2 - r1 + 1;
        } else {
            return r2 - r1;
        }
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        int r1 = rank(lo);
        int r2 = rank(hi);
        if (!contains(hi)) {
            r2--;
        }
        List<Key> result = new ArrayList<>();
        for (int i = r1; i <= r2; i++) {
            result.add(keys[i]);
        }
        return result;
    }

    private void resize(int sz) {
        Key[] tmpKeys = (Key[]) new Comparable[sz];
        Value[] tmpVals = (Value[]) new Object[sz];
        for (int i = 0; i < N; i++) {
            tmpKeys[i] = keys[i];
            tmpVals[i] = vals[i];
        }
        keys = tmpKeys;
        vals = tmpVals;
    }

    private void print() {
        for (int i = 0; i < N; i++) {
            System.out.print(keys[i] + ";" + vals[i] + " ");
        }
        System.out.println();
    }

    public int getCompares() {
        return compares;
    }

    public static void main(String[] args) {
        char[] keys = {'E', 'A','S','Y','Q','U','E','S','T','I','O','N'};
        BinarySearchST<Character, Integer> st = new BinarySearchST<>();
        for (int i = 0; i < keys.length; i++) {
            st.put(keys[i],i);
        }
        System.out.println("Number of compares: " + st.getCompares());
    }
}
