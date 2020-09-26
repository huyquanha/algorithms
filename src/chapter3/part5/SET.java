package chapter3.part5;

import edu.princeton.cs.algs4.ST;

/**
 * Ex3.5.1
 * @param <Key>
 */
public class SET<Key extends Comparable<Key>> {
    private ST<Key, Integer> st;

    public SET() {
        st = new ST<Key, Integer>();
    }

    public void add(Key key) {
        st.put(key, 1);
    }

    public void delete(Key key) {
        st.delete(key);
    }

    public boolean contains(Key key) {
        return st.contains(key);
    }

    public boolean isEmpty() {
        return st.isEmpty();
    }

    public int size() {
        return st.size();
    }

    public String toString() {
        return st.toString();
    }
}
