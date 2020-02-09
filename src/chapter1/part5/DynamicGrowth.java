package chapter1.part5;

public class DynamicGrowth {
    private int[] id;
    private int[] sz;
    private int count;
    private int cur;

    public DynamicGrowth() {
        id = new int[1];
        sz = new int[1];
        count = 0;
        cur = 0;
    }

    public int find(int p) {
        assert(p < id.length);
        while (p != id[p]) {
            p = id[p];
        }
        return p;
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        if (sz[rootP] < sz[rootQ]) {
            id[rootP] = rootQ;
            sz[rootQ] += sz[rootP];
        } else {
            id[rootQ] = rootP;
            sz[rootP] += sz[rootQ];
        }
        count--;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int newSite() {
        if (cur == id.length) {
            //need to resize
            doubleSize();
        }
        id[cur] = cur;
        sz[cur] = 1;
        count++;
        return cur++;
    }

    private void doubleSize() {
        int N = id.length;
        int[] tmp = new int[2*N];
        int[] tmpSz = new int[2*N];
        for (int i = 0; i < N; i++) {
            tmp[i] = id[i];
            tmpSz[i] = sz[i];
        }
        id = tmp;
        sz = tmpSz;
    }
}
