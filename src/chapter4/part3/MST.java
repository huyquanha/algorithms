package chapter4.part3;

/**
 * Abstract class for Minimum Spanning Tree
 */
public abstract class MST {
    public MST(EdgeWeightedGraph g) {}

    public abstract Iterable<Edge> edges();

    public abstract double weight();
}
