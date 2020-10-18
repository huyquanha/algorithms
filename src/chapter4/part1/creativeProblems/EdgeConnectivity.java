package chapter4.part1.creativeProblems;

import chapter4.part1.Graph;
import edu.princeton.cs.algs4.In;

import java.util.HashSet;
import java.util.Set;

/**
 * Ex4.1.36
 */
public class EdgeConnectivity {
    private Graph g;
    private Set<Integer>[] visited;
    private boolean edgeConnected;

    public EdgeConnectivity(Graph g) {
        this.g = g;
        visited = (Set<Integer>[]) new HashSet[g.v()];
        edgeConnected = true;
        for (int v = 0; v < g.v() && edgeConnected; v++) {
            visited[v] = new HashSet<>();
            for (int w : g.adj(v)) {
                if (visited[w] != null && visited[w].contains(v)) {
                    continue;
                }
                boolean[] marked = new boolean[g.v()];
                if (!dfs(v, v, w, marked)) {
                    edgeConnected = false;
                    break;
                } else {
                    visited[v].add(w);
                }
            }
        }
    }

    public boolean isEdgeConnected() {
        return edgeConnected;
    }

    // return true if there's another way from v to w
    private boolean dfs(int v, int s, int w, boolean[] marked) {
        marked[v] = true;
        for (int t : g.adj(v)) {
            if (v == s) {
                // if v is the source node, we don't want to traverse the edge v-w
                if (t != w && !marked[t]) {
                    return dfs(t, s, w, marked);
                }
            } else if (!marked[t]) {
                if (t == w) {
                    return true;
                } else {
                    return dfs(t, s, w, marked);
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Graph g = new Graph(new In(args[0]));
        EdgeConnectivity ec = new EdgeConnectivity(g);
        System.out.println(ec.isEdgeConnected());
    }
}
