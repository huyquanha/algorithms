package chapter4.part1;

public class DepthFirstSearch extends Search {
    private boolean[] marked;
    private int count;

    public DepthFirstSearch(Graph g, int s) {
        super(g, s);
        marked = new boolean[g.v()];
        count = 0;
        dfs(g, s);
    }

    private void dfs(Graph g, int v) {
        marked[v] = true;
        count++;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                dfs(g, w);
            }
        }
    }

    public boolean marked(int v) {
        return marked[v];
    }

    public int count() {
        return count;
    }
}
