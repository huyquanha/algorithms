package chapter4.part2.creativeProblems;

import chapter1.part3.Queue;
import chapter4.part2.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class ShortestAncestralPath {
    public static int getAncestorForShortestPath(Digraph g, int v, int w) {
        Digraph reverse = g.reverse();
        int[] distToV = bfs(reverse, v);
        int[] distToW = bfs(reverse, w);
        int ancestor = -1;
        int minLength = Integer.MAX_VALUE;
        for (int i = 0; i < g.v(); i++) {
            if (distToV[i] > 0 && distToW[i] > 0) {
                int curLength = distToV[i] + distToW[i];
                if (curLength < minLength) {
                    minLength = curLength;
                    ancestor = i;
                }
            }
        }
        StdOut.println("Min length is: " + minLength);
        StdOut.println("Ancestor for min length is: " + ancestor);
        return ancestor;
    }

    private static int[] bfs(Digraph g, int s) {
        int[] distTo = new int[g.v()];
        Arrays.fill(distTo, -1);
        boolean[] marked = new boolean[g.v()];
        Queue<Integer> q = new Queue<>();
        marked[s] = true;
        distTo[s] = 0;
        q.enqueue(s);
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : g.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    distTo[w] = distTo[v] + 1;
                    q.enqueue(w);
                }
            }
        }
        return distTo;
    }

    public static void main(String[] args) {
        Digraph g = new Digraph(new In(args[0]));
        In in = new In();
        while (!in.isEmpty()) {
            getAncestorForShortestPath(g, in.readInt(), in.readInt());
        }
    }
}
