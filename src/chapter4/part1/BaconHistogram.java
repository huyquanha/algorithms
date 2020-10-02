package chapter4.part1;

import chapter1.part3.Queue;
import chapter3.part4.LinearProbingHashST;
import chapter3.part5.RedBlackBSSet;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Ex4.1.23
 */
public class BaconHistogram {
    public static void main(String[] args) {
        String filename = args[0];
        String delim = args[1];
        SymbolGraph sg = new SymbolGraph(filename, delim);
        RedBlackBSSet<String> movieSet = new RedBlackBSSet<>();
        In in = new In(filename);
        while (in.hasNextLine()) {
            String[] parts = in.readLine().split(args[1]);
            movieSet.add(parts[0]);
        }
        String sourceKey = args[2];
        if (!sg.contains(sourceKey)) {
            StdOut.println(sourceKey + " not in database");
            return;
        }
        Graph g = sg.g();
        Paths paths = new BreadthFirstPaths(g, sg.index(sourceKey));
        LinearProbingHashST<Integer, Queue<String>> st = new LinearProbingHashST<>();
        for (int v = 0; v < g.v(); v++) {
            if (!paths.hasPathTo(v)) {
                // this node is not connected to source, but it could be a movie
                String name = sg.name(v);
                if (!movieSet.contains(name)) {
                    // a performer
                    if (!st.contains(Integer.MAX_VALUE)) {
                        st.put(Integer.MAX_VALUE, new Queue<>());
                    }
                    st.get(Integer.MAX_VALUE).enqueue(name);
                }
            } else {
                int dist = paths.distTo(v);
                if (dist % 2 == 0) {
                    // a performer, not a movie
                    int bucket = dist / 2;
                    if (!st.contains(bucket)) {
                        st.put(bucket, new Queue<>());
                    }
                    st.get(bucket).enqueue(sg.name(v));
                }
            }
        }
        List<Integer> bucketList = new ArrayList<>();
        for (int k : st.keys()) {
            bucketList.add(k);
        }
        Collections.sort(bucketList);
        for (int bucket : bucketList) {
            StdOut.print(bucket + ": ");
            for (String performer : st.get(bucket)) {
                StdOut.print(performer + " | ");
            }
            StdOut.println();
        }
    }
}
