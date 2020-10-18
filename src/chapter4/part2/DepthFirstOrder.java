package chapter4.part2;

import chapter1.part3.Queue;
import chapter1.part3.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class DepthFirstOrder {
    private final boolean[] marked;
    private final Queue<Integer> pre;
    private final Queue<Integer> post;
    private final Stack<Integer> reversePost;

    public DepthFirstOrder(Digraph g) {
        marked = new boolean[g.v()];
        pre = new Queue<>();
        post = new Queue<>();
        reversePost = new Stack<>();
        for (int v = 0; v < g.v(); v++) {
            if (!marked[v]) dfs(g, v);
        }
    }

    public Iterable<Integer> pre() {
        return pre;
    }

    public Iterable<Integer> post() {
        return post;
    }

    public Iterable<Integer> reversePost() {
        return reversePost;
    }

    private void dfs(Digraph g, int v) {
        marked[v] = true;
        pre.enqueue(v);
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                dfs(g, w);
            }
        }
        post.enqueue(v);
        reversePost.push(v);
    }

    public static void main(String[] args) {
        Digraph g = new Digraph(new In(args[0]));
        DepthFirstOrder order = new DepthFirstOrder(g);
        for (int v : order.pre()) {
            StdOut.print(v + " ");
        }
        StdOut.println();
        for (int v : order.post()) {
            StdOut.print(v + " ");
        }
        StdOut.println();
        for (int v : order.reversePost()) {
            StdOut.print(v + " ");
        }
        StdOut.println();
    }
}
