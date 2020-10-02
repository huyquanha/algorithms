package chapter4.part1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class DepthFirstDegreeOfSeparation {
    public static void main(String[] args) {
        SymbolGraph sg = new SymbolGraph(args[0], args[1]);
        Graph g = sg.g();
        String sourceKey = args[2];
        if (sg.contains(sourceKey)) {
            Paths paths = new DepthFirstPaths(g, sg.index(sourceKey));
            In in = new In();
            while (!in.isEmpty()) {
                String destKey = in.readLine();
                if (sg.contains(destKey)) {
                    int v = sg.index(destKey);
                    if (paths.hasPathTo(v)) {
                        for (int w : paths.pathTo(v)) {
                            StdOut.println("\t" + sg.name(w));
                        }
                    } else {
                        StdOut.println("Not connected");
                    }
                } else {
                    StdOut.println(destKey + " not in database");
                }
            }
        } else {
            StdOut.println(sourceKey + " not in database");
        }
    }
}
