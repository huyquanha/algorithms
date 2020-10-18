package chapter4.part2;

/**
 * To solve all-pairs reachability
 */
public class TransitiveClosure {
    private DirectedDFS[] all;

    public TransitiveClosure(Digraph g) {
        all = new DirectedDFS[g.v()];
        for (int v = 0; v < g.v(); v++) {
            all[v] = new DirectedDFS(g, v);
        }
    }

    public boolean reachable(int v, int w) {
        return all[v].marked(w);
    }
}
