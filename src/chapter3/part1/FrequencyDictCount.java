package chapter3.part1;

import chapter3.ST;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.List;

/**
 * Ex3.1.26
 */
public class FrequencyDictCount {

    // Do not instantiate.
    private FrequencyDictCount() { }

    /**
     * Reads in a command-line integer and sequence of words from
     * standard input and prints out a word (whose length exceeds
     * the threshold) that occurs most frequently to standard output.
     * It also prints out the number of words whose length exceeds
     * the threshold and the number of distinct such words.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        int distinct = 0, words = 0;
        String dictFile = args[0];
        In dictIn = new In(dictFile);
        ST<String, Integer> st = new BinarySearchST<>();
        while (!dictIn.isEmpty()) {
            String key = dictIn.readString();
            // we assume all keys in dictionary are distinct
            st.put(key,0);
        }
        In in = new In("src/chapter3/test.txt");
        // compute frequency counts
        String lastPut = null;
        int pre_processed = -1;
        while (!in.isEmpty()) {
            String key = in.readString();
            words++;
            lastPut = key;
            pre_processed++;
            // we only add words in the file that also appear in the dictionary, and ignore everything else
            if (st.contains(key)) {
                st.put(key, st.get(key) + 1);
            }
        }
        System.out.println("Last word inserted: " + lastPut);
        System.out.println("Number of words processed before this word: " + pre_processed);
        // print st in order found in dictionary file
        for (String key : st.keys()) {
            System.out.print(key + " " + st.get(key));
        }

        // print st in order of frequency
        String[] keyArray = new String[st.size()];
        int i = 0;
        for (String key : st.keys()) {
            keyArray[i++] = key;
        }
        sort(keyArray, st);
        for (String key : keyArray) {
            System.out.print(key + " " + st.get(key));
        }

        // find a key with the highest frequency count
        String max = "";
        st.put(max, 0);
        for (String word : st.keys()) {
            if (st.get(word) > st.get(max))
                max = word;
        }

        StdOut.println(max + " " + st.get(max));
        StdOut.println("distinct = " + distinct);
        StdOut.println("words    = " + words);
    }

    private static void sort(String[] keyArray, ST<String, Integer> st) {
        // use merge sort to respect stability
        String[] aux = new String[keyArray.length];
        sort(keyArray, aux, st, 0, st.size() - 1);
    }

    private static void sort(String[] keyArray, String[] aux, ST<String, Integer> st, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int mid = lo + (hi - lo)/2;
        sort(keyArray, aux, st, lo, mid);
        sort(keyArray, aux, st, mid + 1, hi);
        merge(keyArray, aux, st, lo, mid, hi);
    }

    private static void merge(String[] keyArray, String[] aux, ST<String,Integer> st, int lo, int mid, int hi) {
        for (int k = lo; k <= hi; k++) {
            aux[k] = keyArray[k];
        }
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) keyArray[k] = aux[j++];
            else if (j > hi) keyArray[k] = aux[i++];
            else if (less(st, aux[j], aux[i])) keyArray[k] = aux[j++];
            else keyArray[k] = aux[i++];
        }
    }

    private static boolean less(ST<String, Integer> st, String key1, String key2) {
        int f1 = st.get(key1);
        int f2 = st.get(key2);
        return f1 < f2;
    }
}


