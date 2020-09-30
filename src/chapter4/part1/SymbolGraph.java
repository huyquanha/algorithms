package chapter4.part1;

import chapter3.part4.LinearProbingHashST;
import edu.princeton.cs.algs4.In;

public class SymbolGraph {
    private Graph g;
    private LinearProbingHashST<String, Integer> st;
    private String[] keys;

    public SymbolGraph(String filename, String delim) {
        st = new LinearProbingHashST<>();
        In in = new In(filename);
        while (in.hasNextLine()) {
            String[] keys = in.readLine().split(delim);
            for (int i = 0; i < keys.length; i++) {
                if (!st.contains(keys[i])) {
                    st.put(keys[i], st.size());
                }
            }
        }
        keys = new String[st.size()];
        for (String k : st.keys()) {
            keys[st.get(k)] = k;
        }
        g = new Graph(st.size());
        in = new In(filename);
        while (in.hasNextLine()) {
            String[] keys = in.readLine().split(delim);
            int v = st.get(keys[0]);
            for (int i = 1; i < keys.length; i++) {
                g.addEdge(v, st.get(keys[i]));
            }
        }
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

    public Graph g() {
        return g;
    }
}
