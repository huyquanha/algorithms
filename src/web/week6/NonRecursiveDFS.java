package web.week6;

import chapter1.part3.Stack;
import chapter4.part1.Graph;
import chapter4.part1.Search;

public class NonRecursiveDFS extends Search {
    private boolean[] marked;
    private Stack<Integer> stack;
    private int count;

    public NonRecursiveDFS(Graph g, int s) {
        super(g,s);
        stack = new Stack<>();
        marked = new boolean[g.v()];
        stack.push(s);
        marked[s] = true;
        while (!stack.isEmpty()) {
            int v = stack.pop();
            count++;
            for (int w : g.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    stack.push(w);
                }
            }
        }
    }

    public boolean marked(int v) { return marked[v]; }

    public int count() { return count; }

}
