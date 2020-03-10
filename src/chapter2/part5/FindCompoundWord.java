package chapter2.part5;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Ex2.5.2
 */
public class FindCompoundWord {
    //TODO: find a collections of string to test this
    public static void main(String[] args) {
        String[] words = StdIn.readAllStrings();
        int N = words.length;
        sort(words, 0, N - 1); //use 3 way quick sort
        int i = 0;
        while (i < N) {
            int j = i + 1;
            while (j < N && words[j].startsWith(words[i])) {
                String leftOver = words[j].substring(words[i].length());
                //do a binary search on the left over string
                int leftOverIdx = search(words, leftOver, 0, N - 1);
                if (leftOverIdx != -1) {
                    System.out.println(words[j]);
                }
                j++;
            }
            i = j;
        }
    }

    private static int search(Comparable[] a, Comparable key, int lo, int hi) {
        while (lo <= hi) {
            int mid = lo + (hi - lo)/2;
            int cmp = key.compareTo(a[mid]);
            if (cmp < 0) {
                //key is smaller than a[mid]
                hi = mid  -1;
            } else if (cmp > 0) {
                // key is larger than a[mid]
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }


    private static void sort(Comparable[] a, int lo, int hi) {
        StdRandom.shuffle(a);
        Comparable v = a[lo];
        int lt = lo, gt = hi, i = lo + 1;
        //maintain the invariant that a[lo..lt-1] < v, a[lt...gt] = v, a[gt + 1...hi] > v
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if (cmp < 0) {
                exch(a, i++, lt++);
            } else if (cmp > 0) {
                exch(a, i, gt--);
            } else {
                i++;
            }
        }
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
