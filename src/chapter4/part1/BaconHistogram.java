package chapter4.part1;

import chapter1.part3.Queue;
import chapter3.part4.LinearProbingHashST;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Ex4.1.23
 */
public class BaconHistogram {
    public static void main(String[] args) {
        SymbolGraph sg = new SymbolGraph(args[0], args[1]);
        String sourceKey = args[2];
        if (!sg.contains(sourceKey)) {
            StdOut.println(sourceKey + " not in database");
            return;
        }
        Graph g = sg.g();
        Paths paths = new BreadthFirstPaths(g, sg.index(sourceKey));
        LinearProbingHashST<Integer, Queue<String>> st = new LinearProbingHashST<>();
        for (int v = 0; v < g.v(); v++) {
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
