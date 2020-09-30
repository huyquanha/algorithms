package chapter4.part1;

public abstract class Paths {
    public Paths(Graph g, int s) {}

    public abstract boolean hasPathTo(int v);

    public abstract Iterable<Integer> pathTo(int v);

    public abstract int distTo(int v);
}
