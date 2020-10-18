package web.week6;

import chapter1.part3.Queue;
import chapter4.part1.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class GraphProperties {
    private int diameter, center;
    private int[] edgeTo;

    public GraphProperties(Graph g) {
        // check that g is connected and has no cycle
        NonRecursiveDFS dfs = new NonRecursiveDFS(g, 0);
        if (dfs.count() != g.v()) {
            throw new IllegalArgumentException("g is not connected");
        }
        UndirectedCycle cycleFinder = new UndirectedCycle(g);
        if (cycleFinder.hasCycle()) {
            throw new IllegalArgumentException("g has cycles");
        }
        edgeTo = new int[g.v()];
        int firstEnd = bfs(g, 0)[0];
        int[] result = bfs(g, firstEnd);
        int secondEnd = result[0];
        diameter = result[1];
        int[] longestPath = new int[diameter + 1];
        int i = 0;
        int currentVertex = secondEnd;
        while (i <= diameter) {
            longestPath[i] = currentVertex;
            currentVertex = edgeTo[currentVertex];
            i++;
        }
        center = longestPath[longestPath.length / 2];
    }

    // because the graph is connected, from s we could reach every other vertex
    private int[] bfs(Graph g, int s) {
        Queue<Integer> q = new Queue<>();
        boolean[] marked = new boolean[g.v()];
        int[] distTo = new int[g.v()];
        marked[s] = true;
        distTo[s] = 0;
        q.enqueue(s);
        int v = -1;
        while (!q.isEmpty()) {
            v = q.dequeue();
            for (int w : g.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    q.enqueue(w);
                }
            }
        }
        // when we come here, v is the last vertex that comes off the queue => should be the furthest from s
        return new int[]{v, distTo[v]};
    }

    public int getDiameter() {
        return diameter;
    }

    public int getCenter() {
        return center;
    }

    public static void main(String[] args) {
        Graph g = new Graph(new In(args[0]));
        GraphProperties graphProperties = new GraphProperties(g);
        StdOut.println(graphProperties.getDiameter());
        StdOut.println(graphProperties.getCenter());
    }
}
