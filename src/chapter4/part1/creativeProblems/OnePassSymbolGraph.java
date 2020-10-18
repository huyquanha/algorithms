package chapter4.part1.creativeProblems;

import chapter1.part3.Bag;
import chapter3.part3.LLRedBlackBST;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinearProbingHashST;

/**
 * Ex4.1.34
 */
public class OnePassSymbolGraph {
    private LLRedBlackBST<Integer, Bag<Integer>> adj;
    private LinearProbingHashST<String, Integer> st;
    private String[] keys;
    private int e;

    public OnePassSymbolGraph(String filename, String delim) {
        adj = new LLRedBlackBST<>();
        st = new LinearProbingHashST<>();
        In in = new In(filename);
        while (in.hasNextLine()) {
            String[] keys = in.readLine().split(delim);
            for (int i = 0; i < keys.length; i++) {
                if (!st.contains(keys[i])) {
                    st.put(keys[i], st.size());
                }
            }
            int v = st.get(keys[0]);
            for (int i = 1; i < keys.length; i++) {
                int w = st.get(keys[i]);
                addEdge(v, w);
            }
        }
        keys = new String[st.size()];
        for (String key : st.keys()) {
            keys[st.get(key)] = key;
        }
    }

    public void addEdge(int v, int w) {
        if (!adj.contains(v)) {
            adj.put(v, new Bag<>());
        }
        adj.get(v).add(w);
        if (!adj.contains(w)) {
            adj.put(w, new Bag<>());
        }
        adj.get(w).add(v);
        e++;
    }

    public boolean contains(String key) {
        return st.contains(key);
    }

    public int index(String key) {
        return st.get(key);
    }

    public String name(int v) {
        return keys[v];
    }

    public int v() {
        return st.size();
    }

    public int e() {
        return e;
    }

    public Iterable<Integer> adj(int v) {
        return adj.get(v);
    }
}
