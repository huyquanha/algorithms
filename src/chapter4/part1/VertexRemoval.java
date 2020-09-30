package chapter4.part1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Ex4.1.10
 */
public class VertexRemoval {
    /**
     * find a vertex to remove in a connected graph g
     * such that the graph is still connected after removal
     * @param g
     * @return
     */
    public static int findVertexToRemove(Graph g) {
        // we assume the graph is connected => we can start dfs at any vertex
        int s = 0;
        boolean[] marked = new boolean[g.v()];
        return dfs(g, s, marked);
    }

    private static int dfs(Graph g, int v, boolean[] marked) {
        marked[v] = true;
        boolean allAdjacentMarked = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                allAdjacentMarked = false;
                int r = dfs(g, w, marked);
                if (r != -1) {
                    return r;
                }
            }
        }
        // if all adjacent vertices are marked, that means there are ways to get to them
        // without going through v => v is the vertex that can be removed
        if (allAdjacentMarked) {
            return v;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph(new In(args[0]));
        StdOut.println(findVertexToRemove(g));
    }
}
