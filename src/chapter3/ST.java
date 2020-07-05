package chapter3.part1;

public interface ST<Key, Value> {
    public void put(Key key, Value val);

    public Value get(Key key);

    public void delete(Key key);

    public boolean contains(Key key);

    public boolean isEmpty();

    public int size();

    public Iterable<Key> keys();
}
