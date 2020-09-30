package chapter4.part1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Ex4.1.22
 */
public class BaconNumberFinder {
    public static void main(String[] args) {
        SymbolGraph sg = new SymbolGraph(args[0], args[1]);
        String sourceKey = args[2];
        if (!sg.contains(sourceKey)) {
            StdOut.println(sourceKey + " not in database");
            return;
        }
        In in = new In();
        Paths paths = new BreadthFirstPaths(sg.g(), sg.index(sourceKey));
        while (!in.isEmpty()) {
            String nominee = in.readLine();
            if (sg.contains(nominee)) {
                int v = sg.index(nominee);
                if (paths.hasPathTo(v)) {
                    StdOut.println(sourceKey + " Number: " + (paths.distTo(v) / 2));
                } else {
                    StdOut.println("Not connected");
                }
            } else {
                StdOut.println(nominee + " not in database");
            }
        }
    }
}
