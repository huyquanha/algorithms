package chapter4.part1;

public class TwoColor {
    private boolean[] marked;
    private boolean[] color;
    private boolean twoColorable;

    public TwoColor(Graph g) {
        marked = new boolean[g.v()];
        color = new boolean[g.v()];
        twoColorable = true;
        for (int v = 0; v < g.v(); v++) {
            if (!marked[v]) {
                dfs(g, v);
            }
        }
    }

    private void dfs(Graph g, int v) {
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                color[w] = !color[v];
                dfs(g, w);
            } else if (color[w] == color[v]) {
                twoColorable = false;
            }
        }
    }

    public boolean isBipartite() {
        return twoColorable;
    }
}
