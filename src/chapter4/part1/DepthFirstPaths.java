package chapter4.part1;

import chapter1.part3.Stack;

public class DepthFirstPaths extends Paths {
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;
    private int s;

    public DepthFirstPaths(Graph g, int s) {
        super(g, s);
        marked = new boolean[g.v()];
        edgeTo = new int[g.v()];
        distTo = new int[g.v()];
        this.s = s;
        distTo[s] = 0;
        dfs(g, s);
    }

    private void dfs(Graph g, int v) {
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                distTo[w] = distTo[v] + 1;
                dfs(g, w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<>();
        for (int x = v; x != s; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(s);
        return path;
    }

    /**
     * Note that this is the length of the first path that is discovered
     * that visits v, not the shortest path from s to v
     * @param v
     * @return
     */
    public int distTo(int v) {
        if (!hasPathTo(v)) return -1;
        return distTo[v];
    }
}
