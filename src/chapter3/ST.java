package chapter3;

public interface ST<Key, Value> {
    void put(Key key, Value val);

    Value get(Key key);

    void delete(Key key);

    default boolean contains(Key key) {
        return get(key) != null;
    }

    boolean isEmpty();

    int size();

    Iterable<Key> keys();

    default void checkKey(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
    }
}
