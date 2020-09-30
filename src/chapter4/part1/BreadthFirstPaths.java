package chapter4.part1;

import chapter1.part3.Queue;
import chapter1.part3.Stack;

public class BreadthFirstPaths extends Paths {
    private boolean[] marked;
    private int[] edgeTo;
    private int s;
    private int[] distTo;

    public BreadthFirstPaths(Graph g, int s) {
        super(g, s);
        this.s = s;
        marked = new boolean[g.v()];
        edgeTo = new int[g.v()];
        distTo = new int[g.v()];
        bfs(g, s);
    }

    private void bfs(Graph g, int s) {
        Queue<Integer> q = new Queue<>();
        marked[s] = true;
        distTo[s] = 0;
        q.enqueue(s);
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : g.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    q.enqueue(w);
                }
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
     * Ex4.1.13
     * @param v
     * @return
     */
    public int distTo(int v) {
        if (!hasPathTo(v)) return -1;
        return distTo[v];
    }
}
