package chapter4.part1;

import chapter3.part5.RedBlackBSSet;
import edu.princeton.cs.algs4.LinearProbingHashST;
import edu.princeton.cs.algs4.StdOut;

/**
 * ex4.1.24
 */
public class MovieStats {
    public static void main(String[] args) {
        SymbolGraph sg = new SymbolGraph(args[0], args[1]);
        Graph g = sg.g();
        CC cc = new CC(g);
        int ccCount = cc.count(); // number of connected components
        StdOut.println("Number of connected components: " + ccCount);
        RedBlackBSSet<Integer>[] components = (RedBlackBSSet<Integer>[]) new RedBlackBSSet[ccCount];
        for (int i = 0; i < ccCount; i++) {
            components[i] = new RedBlackBSSet<>();
        }
        for (int v = 0; v < g.v(); v++) {
            int componentId = cc.id(v);
            components[componentId].add(v);
        }
        int sizeLessThan10 = 0;
        int largestIdx = -1;
        int largestSize = Integer.MIN_VALUE;
        for (int i = 0; i < ccCount; i++) {
            if (components[i].size() < 10) {
                sizeLessThan10++;
            }
            if (components[i].size() > largestSize) {
                largestSize = components[i].size();
                largestIdx = i;
            }
        }
        StdOut.println("Largest component size: " + largestSize);
        StdOut.println("Number of components with size less than 10: " + sizeLessThan10);
        RedBlackBSSet<Integer> largestComponent = components[largestIdx];
        if (largestComponent.contains(sg.index("Bacon, Kevin"))) {
            StdOut.println("Largest component contains Kevin Bacon");
        }
        LinearProbingHashST<Integer, Integer> st = new LinearProbingHashST<>();
        LinearProbingHashST<Integer, Integer> invSt = new LinearProbingHashST<>();
        int i = 0;
        // map from new vertex number to old vertex number
        for (int v : largestComponent.keys()) {
            st.put(v, i);
            invSt.put(i++, v);
        }
        Graph lg = new Graph(largestComponent.size());
        for (int v : largestComponent.keys()) {
            for (int w : g.adj(v)) {
                if (!lg.hasEdge(st.get(v), st.get(w))) {
                    lg.addEdge(st.get(v), st.get(w));
                }
            }
        }
        GraphProperties graphProperties = new GraphProperties(lg);
        StdOut.println("Largest component: ");
        StdOut.println("\tDiameter: " + graphProperties.diameter());
        StdOut.println("\tRadius: " + graphProperties.radius());
        StdOut.println("\tCenter: " + sg.name(invSt.get(graphProperties.center())));
        StdOut.println("\tGirth: " + graphProperties.girth());
    }
}
