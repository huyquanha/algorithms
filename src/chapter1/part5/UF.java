package chapter1.part5;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/*
    THIS IS ONLY A TEMPLATE API
 */
public class UF {
    private int[] id; //each entry in this array represents a site, and the value of the entry represents the component id
    private int count; //the number of connected components

    public UF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i; //initially, each site is in its own component, so the value of the entry equals the index of entry
        }
        count = N;
    }

    public void union(int p, int q) {

    }

    public int find(int p) {
        return -1;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int count() {
        return count;
    }

    public static void main(String[] args) {
        int N = StdIn.readInt();
        UF uf = new UF(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.connected(p,q)) continue;
            uf.union(p,q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
    }
}
