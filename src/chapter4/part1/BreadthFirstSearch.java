package chapter4.part1;

import chapter1.part3.Queue;

public class BreadthFirstSearch extends Search {
    private boolean[] marked;
    private int count;

    public BreadthFirstSearch(Graph g, int s) {
        super(g, s);
        marked = new boolean[g.v()];
        count = 0;
        bfs(g, s);
    }

    /**
     * A subtle difference between DFS and BFS is:
     * - DFS only marks a vertex as marked once it actually visited that vertex
     * - BFS will mark a vertex as marked as soon as it sees there's a path to that vertex, even though the vertex might be queued and not visited yet
     *  This will prevent the same vertex being added to the queue multiple times
     * @param g
     * @param s
     */
    private void bfs(Graph g, int s) {
        Queue<Integer> q = new Queue<>();
        marked[s] = true;
        q.enqueue(s);
        while (!q.isEmpty()) {
            int v = q.dequeue();
            count++;
            for (int w : g.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true; // we mark it right here because even though w is not yet visited, its path is known
                    q.enqueue(w);
                }
            }
        }
    }

    public boolean marked(int v) {
        return marked[v];
    }

    public int count() {
        return count;
    }
}
