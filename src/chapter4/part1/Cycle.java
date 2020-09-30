package chapter4.part1;

import chapter1.part3.Queue;
import chapter1.part3.Stack;

// assume the graph has no self-loops or parallel edges
public class Cycle {
    private boolean[] marked;
    private int[] edgeTo;
    private boolean[] visited;
    private boolean hasCycle;
    private Queue<Stack<Integer>> cycles;

    public Cycle(Graph g) {
        marked = new boolean[g.v()];
        edgeTo = new int[g.v()];
        visited = new boolean[g.v()];
        hasCycle = false;
        cycles = new Queue<>();
        for (int v = 0; v < g.v(); v++) {
            if (!marked[v]) {
                dfs(g, v, v);
            }
        }
    }

    /**
     * NOTE: this method does not find every cycles in the graph, but from the ones it find, you can
     * combine them to get all the cycles.
     *
     * if w != u, we also need to check if w has been visited or not, because if it does,
     * then the edge from w to v has already been traversed and forms a cycle
     * @param g
     * @param v
     * @param u
     */
    private void dfs(Graph g, int v, int u) {
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(g, w, v);
            } else if (w != u && !visited[w]) {
                hasCycle = true;
                Stack<Integer> cycle = new Stack<>();
                cycle.push(w);
                for (int x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);
                cycles.enqueue(cycle);
            }
        }
        visited[v] = true;
    }

    public boolean hasCycle() {
        return hasCycle;
    }

    public Iterable<Iterable<Integer>> getCycles() {
        if (!hasCycle) return null;
        Queue<Iterable<Integer>> copy = new Queue<>();
        for (Stack<Integer> cycle : cycles) {
            Queue<Integer> copyCycle = new Queue<>();
            for (int v : cycle) {
                copyCycle.enqueue(v);
            }
            copy.enqueue(copyCycle);
        }
        return copy;
    }
}
