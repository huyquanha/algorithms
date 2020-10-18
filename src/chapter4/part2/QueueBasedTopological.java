package chapter4.part2;

import chapter1.part3.Queue;
import chapter4.part2.exercises.Degrees;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class QueueBasedTopological {
    private Queue<Integer> order;

    public QueueBasedTopological(Digraph g) {
        DirectedCycle cycleFinder = new DirectedCycle(g);
        if (!cycleFinder.hasCycle()) {
            order = new Queue<>();
            Degrees degrees = new Degrees(g);
            int[] inDegrees = new int[g.v()];
            Queue<Integer> sources = new Queue<>();
            for (int v = 0; v < g.v(); v++) {
                inDegrees[v] = degrees.indegree(v);
                if (inDegrees[v] == 0) {
                    sources.enqueue(v);
                }
            }
            while (!sources.isEmpty()) {
                int v = sources.dequeue();
                order.enqueue(v);
                for (int w : g.adj(v)) {
                    inDegrees[w]--; // this is equivalent to removing edge v->w
                    if (inDegrees[w] == 0) {
                        // no more edges coming into it => it can be added to the queue
                        sources.enqueue(w);
                    }
                }
            }
        }
    }

    public Iterable<Integer> order() {
        return order;
    }

    public boolean isDAG() {
        return order != null;
    }

    public static void main(String[] args) {
        Digraph g = new Digraph(new In(args[0]));
        QueueBasedTopological topological = new QueueBasedTopological(g);
        if (topological.isDAG()) {
            for (int v : topological.order()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
    }
}
