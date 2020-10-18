package chapter4.part2;

/**
 * To see proof of correctness, see Proposition H on page 588
 */
public class KosarajuSCC extends SCC {
    private boolean[] marked;
    private int[] ids;
    private int count;

    public KosarajuSCC(Digraph g) {
        super(g);
        marked = new boolean[g.v()];
        ids = new int[g.v()];
        // get the reverse postorder of the reverse of graph g
        DepthFirstOrder dfo = new DepthFirstOrder(g.reverse());
        for (int s : dfo.reversePost()) {
            if (!marked[s]) {
                dfs(g, s);
                count++;
            }
        }
    }

    private void dfs(Digraph g, int v) {
        marked[v] = true;
        ids[v] = count;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                dfs(g, w);
            }
        }
    }

    public boolean stronglyConnected(int v, int w) { return ids[v] == ids[w]; }

    public int count() { return count; }

    public int id(int v) { return ids[v]; }
}
