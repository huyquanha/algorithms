package chapter3.part4;

import chapter1.part3.Queue;

/**
 * Ex3.4.2
 *
 * @param <Key>
 * @param <Value>
 */
public class SeparateChainingHashST<Key, Value> {
    /**
     * We need to add generic type <K,V> to Node so
     * we can create cast an Object[] to Node<Key,Value>[]
     * <p>
     * Note that K and V here are different parameterized types from Key and Value of SeparateChainingHashST,
     * however when we use Node<Key,Value> then that basically means Key will play the role of K,
     * and Value will play the role of V with respect to the Node class.
     * <p>
     * Since Node doesn't need to access the enclosing instance's non-public fields/methods,
     * Java recommendation is to declare it as a static nested class
     *
     * @param <K>
     * @param <V>
     */
    private static class Node<K, V> {
        K k;
        V v;
        Node<K, V> next;

        public Node(K k, V v) {
            this.k = k;
            this.v = v;
        }
    }

    private Node<Key, Value>[] st;
    // to keep track of the size of each linked list
    private int[] sz;
    private int N = 0; // number of nodes in the table
    private int M; // array length of st

    public SeparateChainingHashST(int M) {
        this.M = M;
        st = (Node<Key, Value>[]) new Node[M];
        sz = new int[M];
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

    /**
     * Ex3.4.18
     *
     * @param k
     * @param maxProbes
     * @return
     */
    public Value getWithLimit(Key k, int maxProbes) {
        if (N / M >= maxProbes) resize(2 * M);
        int h = superHash(k);
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
     * so we avoid introducing an additional "prev" reference
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
                return;
            }
            cur = cur.next;
        }
        Node<Key, Value> oldFirst = st[h];
        st[h] = new Node(k, v);
        st[h].next = oldFirst;
        sz[h]++;
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
                st[h] = st[h].next;
                N--;
                sz[h]--;
                if (N > 0 && N <= 2 * M) resize(M / 2);
                return;
            }
            cur = cur.next;
        }
        if (M >= 2 && N <= 2 * M) {
            resize(M / 2);
        }
    }

    /**
     * Ex3.4.19
     *
     * @return
     */
    public Iterable<Key> keys() {
        Queue<Key> q = new Queue<>();
        for (int i = 0; i < M; i++) {
            Node<Key, Value> cur = st[i];
            while (cur != null) {
                q.enqueue(cur.k);
            }
        }
        return q;
    }

    /**
     * Ex3.4.30
     *
     * @param
     */
    public double chiSquareStat() {
        double alpha = (double) N / M;
        double total = 0;
        for (int i = 0; i < M; i++) {
            total += Math.pow(sz[i] - alpha, 2);
        }
        return (1 / alpha) * total;
    }

    private void resize(int cap) {
        SeparateChainingHashST<Key, Value> tmpSt = new SeparateChainingHashST<>(cap);
        for (int i = 0; i < M; i++) {
            Node<Key, Value> cur = st[i];
            while (cur != null) {
                tmpSt.put(cur.k, cur.v);
                cur = cur.next;
            }
        }
        st = tmpSt.st;
        sz = tmpSt.sz;
        M = tmpSt.M;
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
        if (lgM <= 26) t = t % PrimeList.PRIMES[lgM + 5];
        return t % M;
    }

    public static void main(String[] args) {
        SeparateChainingHashST<Integer, Integer> st = new SeparateChainingHashST<>(2);
//        String test = "E A S Y Q U E S T I O N A K B T L U V S T G I H J K O P R";
//        String[] letters = test.split(" ");
        Integer[] letters = new Integer[1000];
        for (int i = 0; i < letters.length; i++) {
            letters[i] = (int) (Math.random() * 1000);
            st.put(letters[i], i);
        }
        System.out.println("Put completed");
        System.out.println("M is: " + st.M);
        System.out.println("N is: " + st.N);
        System.out.println("Chi-square statistics is: " + st.chiSquareStat());
//        System.out.println("E is: " + st.get('E'));
//        System.out.println("Z is: " + st.get('Z'));
//        System.out.println("Query completed");
//        st.delete('O');
//        st.delete('A');
//        System.out.println("Delete completed");
    }
}
