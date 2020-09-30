package chapter4.part1;

public abstract class Search {
    public Search(Graph g, int s) {}

    public abstract boolean marked(int v); // is v connected to s?

    public abstract int count(); // how many vertices are connected to s (including s)?
}
