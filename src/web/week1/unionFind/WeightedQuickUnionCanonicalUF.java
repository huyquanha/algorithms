package web.week1.unionFind;

public class WeightedQuickUnionCanonicalUF {
    private int id[];
    private int sz[];
    private int max[];

    public WeightedQuickUnionCanonicalUF(int N) {
        id = new int[N];
        sz = new int[N];
        max = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
            max[i] = i;
        }
    }

    public int find(int p) {
        // find the maximum of the connected component containing p
        int rootP = findRoot(p);
        return max[rootP];
    }

    public int findRoot(int p) {
        int root = p;
        while (root != id[root]) {
            root = id[root];
        }
        // path compression
        while (id[p] != root) {
            int nextP = id[p];
            id[p] = root;
            p = nextP;
        }
        return root;
    }

    public boolean connected (int p, int q) {
        return findRoot(p) == findRoot(q);
    }

    public void union(int p, int q) {
        int rootP = findRoot(p);
        int rootQ = findRoot(q);
        if (rootP == rootQ) {
            return;
        }
        if (sz[rootP] < sz[rootQ]) {
            // rootP goes under rootQ
            id[rootP] = rootQ;
            sz[rootQ] += sz[rootP];
            max[rootQ] = Math.max(max[rootQ], max[rootP]);
        } else {
            // rootQ goes under rootP
            id[rootQ] = rootP;
            sz[rootP] += sz[rootQ];
            max[rootP] = Math.max(max[rootQ], max[rootP]);
        }
    }
}
