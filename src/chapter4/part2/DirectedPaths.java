package chapter4.part2;

public abstract class DirectedPaths {
    public DirectedPaths(Digraph g, int s) {}

    public abstract boolean hasPathTo(int v);

    public abstract Iterable<Integer> pathTo(int v);

    public abstract int distTo(int v);
}
