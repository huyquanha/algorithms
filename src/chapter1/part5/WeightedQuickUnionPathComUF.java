package chapter1.part5;

/*
    Ex1.5.13
    the height of the tree is 4 => the size of the tree is at least 16 (lg 16 = 4)
    0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
    (0,1), (2,3), (4,5), (6,7)
    0       2       4   6
    |       |       |   |
    1       3       5   7
    (0,2) since this will avoid the tree being flattened. similarly (4,6)
    0           4
    |\          |\
    1 2         5 6
      |           |
      3           7

    (0,4) will put subtree of 4 below 0, and create a tree of height 3.
    We can do similarly for 7-> 15. And then do (0,7)

 */
public class WeightedQuickUnionPathComUF {
    private int[] id;
    private int[] sz;
    private int count;

    public WeightedQuickUnionPathComUF(int N) {
        id = new int[N];
        sz = new int[N];
        count = N;
        for (int i = 0; i< N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    public int count() {
        return count;
    }

    public int find(int p) {
        int root = p;
        while (root != id[root]) {
            root = id[root];
        }
        while (id[p] != root) {
            int nextP = id[p];
            id[p] = root;
            p = nextP;
        }
        return root;
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) {
            return;
        }
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
}
