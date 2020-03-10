package chapter2.part5;

import chapter1.part3.Queue;
import edu.princeton.cs.algs4.Quick3way;

/**
 * Ex2.5.4
 */
public class Dedup {
    public static String[] dedup(String[] a) {
        Quick3way.sort(a);
        Queue<String> q= new Queue<>();
        q.enqueue(a[0]);
        for (int i = 1; i < a.length; i++) {
            if (a[i].compareTo(a[i - 1]) != 0) {
                q.enqueue(a[i]);
            }
        }
        String[] result = new String[q.size()];
        int i = 0;
        for (String s : q) {
            result[i++] = s;
        }
        return result;
    }

    //TODO: add code to test dedup
    public static void main(String[] args) {

    }
}
