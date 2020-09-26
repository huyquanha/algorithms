package chapter3;

public interface OrderedST<Key extends Comparable<Key>, Value> extends ST<Key, Value> {
    Key min();

    Key max();

    /**
     *
     * @param key
     * @return largest key less than or equal to key
     */
    Key floor(Key key);

    /**
     *
     * @param key
     * @return smallest key larger than or equal to key
     */
    Key ceiling(Key key);

    /**
     *
     * @param key
     * @return number of keys less than key (equals index of key if key is in the table)
     */
    int rank(Key key);

    /**
     *
     * @param k
     * @return key of rank k
     */
    Key select(int k);

    default void deleteMin() {
        delete(min());
    }

    default void deleteMax() {
        delete(max());
    }

    /**
     *
     * @param lo
     * @param hi
     * @return number of keys that is larger than or equal to lo, and smaller than or equal to hi
     */
    default int size(Key lo, Key hi) {
        if (hi.compareTo(lo) < 0) {
            return 0;
        }
        if (contains(hi)) {
            // if hi is in the table => it is included
            return rank(hi) - rank(lo) + 1;
        } else {
            // if hi is not in the table, it's not included
            return rank(hi) - rank(lo);
        }
    }

    default Iterable<Key> keys() {
        return keys(min(), max());
    }

    /**
     *
     * @param lo
     * @param hi
     * @return a list of keys larger than or equal to lo, and smaller than or equal to hi
     */
    Iterable<Key> keys(Key lo, Key hi);
}
