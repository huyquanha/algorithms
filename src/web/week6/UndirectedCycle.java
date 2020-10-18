package web.week6;

import chapter4.part1.Graph;

public class UndirectedCycle {
    private boolean[] marked;
    private boolean hasCycle;

    public UndirectedCycle(Graph g) {
        marked = new boolean[g.v()];
        for (int v = 0; v < g.v(); v++) {
            if (!marked[v]) dfs(g, v, v);
        }
    }

    private void dfs(Graph g, int v, int u) {
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (hasCycle) return;
            if (!marked[w]) dfs(g, w, v);
            else if (w != u) {
                hasCycle = true;
            }
        }
    }

    public boolean hasCycle() { return hasCycle; }
}
