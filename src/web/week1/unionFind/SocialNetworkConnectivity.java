package web.week1.unionFind;

import java.util.Map;
import java.util.TreeMap;

/**
 * Web Exercise 1
 */
public class SocialNetworkConnectivity {
    /**
     * n is the number of people in the social network. For convenience, we will assign each of them an id from 0 to n-1
     * timestampMap: each entry represent a time when 2 friends (represented by 2 integers in the array) connect
     * We use a TreeMap so that the entries are in order of timestamps
     * @param n
     * @param timestampMap
     * @return the first timestamp when all people are connected
     */
    public static double whenConnected(int n, TreeMap<Double, Integer[]> timestampMap) {
        // initialize the array of people
        int[] id = new int[n];
        int[] sz = new int[n];
        int count = n;
        for (int i = 0; i < n; i++) {
            // assign the id to each people. At first each people is their own component, so id[i] points to i
            id[i] = i;
            sz[i] = 1;
        }
        for(Map.Entry<Double, Integer[]> entry : timestampMap.entrySet()) {
            double timestamp = entry.getKey();
            Integer[] connection = entry.getValue();
            count = connect(id, sz, count, connection[0], connection[1]);
            if (count == 0) {
                return timestamp;
            }
        }
        // signifying that after all the entries are processed, the group of people are not still all connected
        return Double.MAX_VALUE;
    }

    private static int connect(int[] id, int[] sz, int count, int p, int q) {
        int rootP = find(id, p);
        int rootQ = find(id, q);
        if (rootP == rootQ) {
            // p and q are already connected
            return count;
        } else {
            if (sz[rootP] < sz[rootQ]) {
                // rootP should go under rootQ
                id[rootP] = rootQ;
                sz[rootQ] += sz[rootP];
            } else {
                id[rootQ] = rootP;
                sz[rootP] += sz[rootQ];
            }
            return (count - 1);
        }
    }

    private static int find(int[] id, int p) {
        while (p != id[p]) {
            p = id[p];
        }
        return p;
    }
}
