package chapter1.part5;

/*
    Ex1.5.14
    Prove by induction
    Base case: height = 0 when N =1 => correct since lg 1 = 0
    Say we have sub-tree with i sites and height(i) <= lg i, and another sub-tree with j sites, height(j) <= lg j
    Without loss of generalization, assume height(i) >= height(j) => lg i >= height(j)
    When unioning these 2 trees, j will go below i. The new max height will be max(height(i), height(j) + 1)
    The new size is i+j=k.
    If the new height is still height(i):
        - height(i) <= lg i < lg k since i < k
    Else if the new height is height(j) + 1:
        - height(j) + 1 <= lg j + 1 = lg 2j = lg (j + j)
    Also since we have lg i >= height(j), we also have this condition:
        - height(j) + 1 <= lg i + 1 = lg 2i = lg (i + i)
        If i >= j: lg (j + j) <= lg (i + j) = lg k
        Else if i < j: lg (i + i) < lg (i + j) = lg k

    As we can see, no matter if i>=j or i < j, the new height of the new tree with size k is at most lg k.

 */
public class WeightedQuickUnionHeightUF {
    private int[] id;
    private int[] h;
    private int count;

    public WeightedQuickUnionHeightUF(int N) {
        count = N;
        id = new int[N];
        h = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            h[i] = 0;
        }
    }

    public int count() {
        return count;
    }

    public int find(int p) {
        while (p != id[p]) {
            p = id[p];
        }
        return p;
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        if (h[rootP] < h[rootQ]) {
            id[rootP] = rootQ;
            h[rootQ] = Math.max(h[rootQ], h[rootP] +1);
        } else {
            id[rootQ] = rootP;
            h[rootP] = Math.max(h[rootP], h[rootQ] + 1);
        }
        count--;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }
}
