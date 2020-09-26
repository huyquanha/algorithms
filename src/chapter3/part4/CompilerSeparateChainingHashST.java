package chapter3.part4;

/**
 * Ex3.4.3
 *
 * @param <Key>
 * @param <Value>
 */
public class CompilerSeparateChainingHashST<Key, Value> {
    private static class Node<K, V> {
        K k;
        V v;
        Node<K, V> next;
        int entryCount;

        public Node(K k, V v, int entryCount) {
            this.k = k;
            this.v = v;
            this.entryCount = entryCount;
        }
    }

    private Node<Key, Value>[] st;
    private int N = 0; // number of nodes in the table
    private int M; // size of the table

    public CompilerSeparateChainingHashST(int M) {
        this.M = M;
        st = (Node<Key, Value>[]) new Node[M];
    }

    public Value get(Key k) {
        int h = hash(k);
        Node<Key, Value> cur = st[h];
        while (cur != null) {
            if (cur.k.equals(k)) {
                return cur.v;
            }
            cur = cur.next;
        }
        return null;
    }

    public boolean contains(Key k) {
        return get(k) != null;
    }

    /**
     * The insertion of a new Node is done at the head of the list, not the tail,
     * so we save an additional "prev" reference
     *
     * @param k
     * @param v
     */
    public void put(Key k, Value v) {
        if (N >= 8 * M) resize(2 * M);
        int h = hash(k);
        Node<Key, Value> cur = st[h];
        while (cur != null) {
            if (cur.k.equals(k)) {
                cur.v = v;
                cur.entryCount = N;
                return;
            }
            cur = cur.next;
        }
        Node<Key, Value> oldFirst = st[h];
        st[h] = new Node(k, v, N);
        st[h].next = oldFirst;
        N++;
    }

    /**
     * For this delete implementation, we try not to use an additional "prev" reference
     * like with SequentialSearchST by overwriting the content of cur (the node to be deleted) by first,
     * and then point first to first.next, effectively "removing" cur from the list
     * <p>
     * Note that this implementation will relocate first to the position where cur previously is,
     * but with hash tables we don't really care about the ordering of elements so this should be fine
     *
     * @param k
     */
    public void delete(Key k) {
        if (!contains(k)) return;
        int h = hash(k);
        Node<Key, Value> cur = st[h];
        while (cur != null) {
            if (cur.k.equals(k)) {
                cur.k = st[h].k;
                cur.v = st[h].v;
                cur.entryCount = st[h].entryCount;
                st[h] = st[h].next;
                N--;
                if (N > 0 && N <= 2 * M) resize(M / 2);
                return;
            }
            cur = cur.next;
        }
    }

    /**
     * delete all Nodes in the table where the field entryCount > k
     *
     * @param k
     */
    public void deleteAll(int k) {
        for (int i = 0; i < M; i++) {
            Node<Key, Value> cur = st[i];
            while (cur != null) {
                if (cur.entryCount > k) {
                    cur.k = st[i].k;
                    cur.v = st[i].v;
                    cur.entryCount = st[i].entryCount;
                    st[i] = st[i].next;
                    N--;
                }
                cur = cur.next;
            }
        }
        if (N > 0 && N <= 2 * M) resize(M / 2);
    }

    private void resize(int cap) {
        CompilerSeparateChainingHashST<Key, Value> tmpSt = new CompilerSeparateChainingHashST<>(cap);
        for (int i = 0; i < M; i++) {
            Node<Key, Value> cur = st[i];
            while (cur != null) {
                tmpSt.put(cur.k, cur.v);
                cur = cur.next;
            }
        }
        this.st = tmpSt.st;
        this.M = tmpSt.M;
    }

    /**
     * Even though this function might not disperse the keys as evenly (see superHash())
     * it doesn't necessarily mean bad, because it depends on the keys not being random as well.
     * <p>
     * For example, if the keys are IP addresses, which are not randomly distributed for many historical reasons,
     * then superHash() should be used.
     * <p>
     * If the keys being inserted into the hash table are random (which they are in most practical
     * applications), often this simpler implementation is more favorable than superHash()
     *
     * @param k
     * @return the hash of the key, "hopefully" evenly distributed from 0 to M - 1
     */
    private int hash(Key k) {
        return (k.hashCode() & 0x7fffffff) % M;
    }

    /**
     * This hash function disperses the keys more evenly since M is resized by 2 (doubled/halved)
     * => M is a power of 2
     * => only the least significant bits of (k.hashCode() & 0x7fffffff) is used to compute the hash
     * => the previous hash function might provide an uneven distribution
     * <p>
     * With this implementation, we use a prime larger than M (5 is just an arbitrary choice) to compute
     * the intermediate value (distributed equally among the values less then prime),
     * and then % it again with M to put about 5 values in each bucket from 0 to M-1
     * <p>
     * Note1: if lgM >= 26 we don't have a prime for [lgM +5] in the prime list,
     * so we just fallback to the previous hash function by % M directly.
     * <p>
     * Note2: if we want to use this function instead of the default hash(), make lgM an
     * instance variable and only re-compute it whenever M is changed (resize()),
     * instead of re-computing lgM every time you hash a key.
     *
     * @param k
     * @return hash of the key, evenly distributed from 0 to M - 1
     */
    private int superHash(Key k) {
        int t = (k.hashCode() & 0x7fffffff);
        int lgM = (int) (Math.log(M) / Math.log(2));
        if (lgM < 26) t = t % PrimeList.PRIMES[lgM + 5];
        return t % M;
    }

    public static void main(String[] args) {
        CompilerSeparateChainingHashST<String, Integer> st = new CompilerSeparateChainingHashST<>(5);
        String test = "E A S Y Q U E S T I O N";
        String[] letters = test.split(" ");
        for (int i = 0; i < letters.length; i++) {
            st.put(letters[i], i);
            System.out.println("M after insertion: " + st.M);
        }
        System.out.println("Put completed");
        System.out.println("E is: " + st.get("E"));
        System.out.println("Z is: " + st.get("Z"));
        System.out.println("Query completed");
        st.deleteAll(4);
        System.out.println("Delete completed");
    }
}
