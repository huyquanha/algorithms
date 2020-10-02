package chapter4.part1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdOut;

/**
 * Ex4.1.25: added year command-line args to ignore old movies
 */
public class DegreeOfSeparation {
    private static final String OUT_FILE = "data/new-movies.txt";

    public static void main(String[] args) {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        In in = new In(args[0]);
        String delim = args[1];
        int y = Integer.parseInt(args[3]);
        Out out = new Out(OUT_FILE);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] parts = line.split(delim);
            String movie = parts[0];
            String[] titleAndYear = movie.split(" ");
            String yearStr = titleAndYear[titleAndYear.length - 1];
            int year = Integer.parseInt(yearStr.substring(1, yearStr.length() - 1));
            if (year > y) {
                out.println(line);
            }
        }
        out.close();
        SymbolGraph sg = new SymbolGraph(OUT_FILE, delim);
        Graph g = sg.g();
        String sourceKey = args[2];
        if (!sg.contains(sourceKey)) {
            StdOut.println(sourceKey + " not in database");
            return;
        }
        BreadthFirstPaths bfp = new BreadthFirstPaths(g, sg.index(sourceKey));
        // open stdIn to get the destination keys
        in = new In();
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
