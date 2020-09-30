package chapter4.part1;

public class CC {
    private boolean[] marked;
    private int[] ids;
    private int count;

    public CC(Graph g) {
        marked = new boolean[g.v()];
        ids = new int[g.v()];
        count = 0;
        for (int v = 0; v < g.v(); v++) {
            if (!marked[v]) {
                dfs(g, v);
                count++;
            }
        }
    }

    private void dfs(Graph g, int v) {
        marked[v] = true;
        ids[v] = count;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                dfs(g, w);
            }
        }
    }

    // are v and w connected?
    public boolean connected(int v, int w) {
        return ids[v] == ids[w];
    }

    // number of connected components
    public int count() {
        return count;
    }

    // component identifier for v (between 0 and count() - 1)
    public int id(int v) {
        return ids[v];
    }
}