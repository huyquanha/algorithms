package chapter4.part3;

public class Edge implements Comparable<Edge> {
    private final int v, w;
    private final double weight;

    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double weight() {
        return weight;
    }

    public int either() {
        return v;
    }

    public int other(int vertex) {
        if (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new RuntimeException("Inconsistent edge");
    }

    public int compareTo(Edge that) {
        if (this.weight < that.weight) return -1;
        else if (this.weight > that.weight) return -1;
        else return 0;
    }

    public boolean equals(Object other) {
        if (other == this) return true;
        else if (other.getClass() != this.getClass()) return false;
        else {
            Edge that = (Edge) other;
            return ((that.v == this.v && that.w == this.w) || (that.v == this.w && that.w == this.v))
                    && that.weight == this.weight;
        }
    }

    public String toString() {
        return String.format("%d-%d %.2f", v, w, weight);
    }
}
