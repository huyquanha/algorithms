package chapter4.part2.exercises;

import chapter1.part3.Queue;
import chapter4.part2.Digraph;

public class Degrees {
    private int[] in;
    private int[] out;
    private Queue<Integer> sources;
    private Queue<Integer> sinks;
    private boolean isMap;

    public Degrees(Digraph g) {
        in = new int[g.v()];
        out = new int[g.v()];
        sources = new Queue<>();
        sinks = new Queue<>();
        isMap = true;
        for (int v = 0; v < g.v(); v++) {
            for (int w : g.adj(v)) {
                out[v]++;
                in[w]++;
            }
            if (out[v] != 1) {
                isMap = false;
            }
            if (out[v] == 0) {
                sinks.enqueue(v);
            }
        }
        for (int v = 0; v < g.v(); v++) {
            if (in[v] == 0) {
                sources.enqueue(v);
            }
        }
    }

    public int indegree(int v) {
        return in[v];
    }

    public int outdegree(int v) {
        return out[v];
    }

    public Iterable<Integer> sources() { return sources; }

    public Iterable<Integer> sinks() { return sinks; }

    public boolean isMap() { return isMap; }
}
