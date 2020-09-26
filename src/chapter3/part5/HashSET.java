package chapter3.part5;

import chapter3.part4.LinearProbingHashST;

/**
 * Ex3.5.1
 * @param <Key>
 */
public class HashSET<Key> {
    private LinearProbingHashST<Key, Integer> st;

    public HashSET() {
        st = new LinearProbingHashST<>(2);
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
}
