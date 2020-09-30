package chapter4.part1;

public class UFSearch extends Search {
    private int[] ids;
    private int[] sz;
    private int s;

    public UFSearch(Graph g, int s) {
        super(g, s);
        ids = new int[g.v()];
        sz = new int[g.v()];
        this.s = s;
        for (int v = 0; v < g.v(); v++) {
            ids[v] = v;
            sz[v] = 1;
        }
        for (int v = 0; v < g.v(); v++) {
            for (int w : g.adj(v)) {
                union(v, w);
            }
        }
    }

    private int find(int v) {
        while (v != ids[v]) {
            v = ids[v];
            ids[v] = ids[ids[v]];
        }
        return v;
    }

    private void union(int v, int w) {
        int rootV = find(v);
        int rootW = find(w);
        if (rootV == rootW) return;
        if (sz[rootV] < sz[rootW]) {
            ids[rootV] = rootW;
            sz[rootW] += sz[rootV];
        } else {
            ids[rootW] = rootV;
            sz[rootV] += sz[rootW];
        }
    }

    private boolean connected(int v, int w) {
        return find(v) == find(w);
    }

    public boolean marked(int v) {
        return connected(s, v);
    }

    public int count() {
        return sz[s];
    }
}
