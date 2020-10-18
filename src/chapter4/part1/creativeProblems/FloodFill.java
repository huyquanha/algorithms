package chapter4.part1.creativeProblems;

import chapter1.part3.Queue;
import chapter4.part1.Graph;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Ex4.1.39
 */
public class FloodFill {
    public static void execute(Color[][] image, Color target, Color replacement) {
        int h = image.length;
        int w = image[0].length;
        // map from position in image to graph's vertex index
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (image[i][j].equals(target)) {
                    map.put(i * w + j, map.size());
                }
            }
        }
        int[] keys = new int[map.size()];
        for (int pos : map.keySet()) {
            keys[map.get(pos)] = pos;
        }
        Graph g = new Graph(map.size());
        for (int pos : map.keySet()) {
            int left = pos - 1;
            int right = pos + 1;
            int top = pos - w;
            int bottom = pos + w;
            if (left / w == pos / w && map.containsKey(left)) {
                g.addEdge(map.get(pos), map.get(left));
            }
            if (right / w == pos / w && map.containsKey(right)) {
                g.addEdge(map.get(pos), map.get(right));
            }
            if (top >= 0 && map.containsKey(top)) {
                g.addEdge(map.get(pos), map.get(top));
            }
            if (bottom < w * h && map.containsKey(bottom)) {
                g.addEdge(map.get(pos), map.get(bottom));
            }
        }
        boolean[] marked = new boolean[g.v()];
        for (int v = 0; v < g.v(); v++) {
            if (!marked[v]) {
                bfs(g, v, marked, image, keys, replacement);
            }
        }
    }

    private static void bfs(Graph g, int s, boolean[] marked, Color[][] image, int[] keys, Color replacement) {
        marked[s] = true;
        Queue<Integer> q = new Queue<>();
        q.enqueue(s);
        while (!q.isEmpty()) {
            int v = q.dequeue();
            changeColor(v, image, keys, replacement);
            for (int w : g.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }

    private static void changeColor(int v, Color[][] image, int[] keys, Color replacement) {
        int pos = keys[v];
        int w = image[0].length;
        int i = pos / w;
        int j = pos % w;
        image[i][j] = replacement;
    }
}
