package chapter1.part5;

/*
    Ex1.5.12
    The sequence of input pairs is: (0,1), (0,2), (0,3), (0,4)
    The resulting tree is
    4
    |
    3
    |
    2
    |
    1
    |
    0
 */
public class QuickUnionPathCompUF {
    private int[] id;
    private int count;

    public QuickUnionPathCompUF(int N) {
        id = new int[N];
        count = N;
        for (int i = 0; i< N; i++) {
            id[i] = i;
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
        while (id[p] != root) { //flatten out the tree
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
        id[rootP] = rootQ;
        count--;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }
}
