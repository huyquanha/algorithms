package chapter3.part4;

import chapter1.part3.Queue;

/**
 * Ex3.4.27
 *
 * @param <Value>
 */
public class DoubleProbeSeparateChainingHashST<Value> {
    private static class Node<V> {
        Character k;
        V v;
        Node<V> next;

        public Node(Character k, V v) {
            this.k = k;
            this.v = v;
        }
    }

    private Node<Value>[] st;
    private int[] sz;
    private int N, M;

    public DoubleProbeSeparateChainingHashST(int M) {
        st = (Node<Value>[]) new Node[M];
        sz = new int[M];
        this.M = M;
    }

    public Value get(Character k) {
        int h1 = hash1(k);
        int h2 = hash2(k);
        // we need to probe both 2 lists because we don't know which list k belongs to
        Node<Value> cur = st[h1];
        while (cur != null) {
            if (cur.k.equals(k)) {
                return cur.v;
            }
        }
        cur = st[h2];
        while (cur != null) {
            if (cur.k.equals(k)) {
                return cur.v;
            }
        }
        return null;
    }

    public boolean contains(Character k) {
        return get(k) != null;
    }

    public void put(Character k, Value v) {
        if (N >= 8 * M) resize(2 * M);
        int h1 = hash1(k);
        int h2 = hash2(k);
        // We only use sz[] to determine which list to insert the new Node,
        // but when searching we still have to search both lists.
        Node<Value> cur = st[h1];
        while (cur != null) {
            if (cur.k.equals(k)) {
                cur.v = v;
                return;
            }
            cur = cur.next;
        }
        cur = st[h2];
        while (cur != null) {
            if (cur.k.equals(k)) {
                cur.v = v;
                return;
            }
            cur = cur.next;
        }
        int h = sz[h1] < sz[h2] ? h1 : h2;
        Node<Value> oldFirst = st[h];
        st[h] = new Node(k, v);
        st[h].next = oldFirst;
        N++;
        sz[h]++;
    }

    /**
     * Ex3.4.29
     * @param k
     */
    public void delete(Character k) {
        if (!contains(k)) return;
        int h1 = hash1(k);
        int h2 = hash2(k);
        Node cur = st[h1];
        while (cur != null) {
            if (cur.k.equals(k)) {
                cur.k = st[h1].k;
                cur.v = st[h1].v;
                st[h1] = st[h1].next;
                N--;
                if (N > 0 && N <= 2 * M) resize(M / 2);
                return;
            }
            cur = cur.next;
        }
        cur = st[h2];
        while (cur != null) {
            if (cur.k.equals(k)) {
                cur.k = st[h2].k;
                cur.v = st[h2].v;
                st[h1] = st[h2].next;
                N--;
                if (N > 0 && N <= 2 * M) resize(M / 2);
                return;
            }
            cur = cur.next;
        }
    }

    public Iterable<Character> keys() {
        Queue<Character> q = new Queue<>();
        for (int i = 0; i < M; i++) {
            Node<Value> cur = st[i];
            while (cur != null) {
                q.enqueue(cur.k);
                cur = cur.next;
            }
        }
        return q;
    }

    public double avgSearchHitCost() {
        double total = 0;
        for (Character k : keys()) {
            int probes = 0;
            int h1 = hash1(k);
            int h2 = hash2(k);
            Node<Value> cur = st[h1];
            boolean found = false;
            while (cur != null && !found) {
                probes++;
                if (cur.k.equals(k)) {
                    total += probes;
                    found = true;
                } else {
                    cur = cur.next;
                }
            }
            cur = st[h2];
            while (cur != null && !found) {
                probes++;
                if (cur.k.equals(k)) {
                    total += probes;
                    found = true;
                } else {
                    cur = cur.next;
                }
            }
        }
        return total / N;
    }

    public double avgSearchMissCost() {
        // each search miss need to go through 2 buckets in st[]
        // assuming a random hash function, it's equally likely for each bucket to be searched by hash1
        // it's also equally likely for each bucket to be searched by hash2
        // average search miss cost is average search miss cost by hash1 + average search miss cost by hash2
        // => 2 * average length of the buckets => 2 * N/M
        return 2 * ((double) N / M);
    }

    private void resize(int cap) {
        DoubleProbeSeparateChainingHashST<Value> tmp = new DoubleProbeSeparateChainingHashST<>(cap);
        for (int i = 0; i < M; i++) {
            Node<Value> cur = st[i];
            while (cur != null) {
                tmp.put(cur.k, cur.v);
                cur = cur.next;
            }
        }
        st = tmp.st;
        sz = tmp.sz;
        M = tmp.M;
    }

    private int hash1(Character k) {
        return (11 * (Character.toLowerCase(k) - 'a' + 1)) % M;
    }

    private int hash2(Character k) {
        return (17 * (Character.toLowerCase(k) - 'a' + 1)) % M;
    }

    public static void main(String[] args) {
        String input = "EASYQUTION";
        DoubleProbeSeparateChainingHashST<Integer> st = new DoubleProbeSeparateChainingHashST<>(3);
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            st.put(c, i);
        }
        System.out.println("Average Search Hit cost: " + st.avgSearchHitCost());
        System.out.println("Average Search Miss cost: " + st.avgSearchMissCost());
    }
}
