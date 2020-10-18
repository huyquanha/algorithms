package chapter4.part2;

public abstract class SCC {
    public SCC(Digraph g) {}

    public abstract boolean stronglyConnected(int v, int w);

    public abstract int count();

    public abstract int id(int v);
}
