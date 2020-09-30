package chapter4.part1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class DegreeOfSeparation {
    public static void main(String[] args) {
        SymbolGraph sg = new SymbolGraph(args[0], args[1]);
        Graph g = sg.g();
        String sourceKey = args[2];
        if (!sg.contains(sourceKey)) {
            StdOut.println(sourceKey + " not in database");
            return;
        }
        BreadthFirstPaths bfp = new BreadthFirstPaths(g, sg.index(sourceKey));
        // open stdIn to get the destination keys
        In in = new In();
        while (in.hasNextLine()) {
            String destKey = in.readLine();
            if (sg.contains(destKey)) {
                int v = sg.index(destKey);
                if (bfp.hasPathTo(v)) {
                    for (int w : bfp.pathTo(v)) {
                        StdOut.println("\t" + sg.name(w));
                    }
                } else {
                    StdOut.println("Not connected");
                }
            } else {
                StdOut.println(destKey + " not in database");
            }
        }
    }
}
