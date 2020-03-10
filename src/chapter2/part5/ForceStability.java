package chapter2.part5;

import chapter2.part3.Quick;

import java.util.Date;

/**
 * Ex2.5.18
 */
public class ForceStability {
    private static class Transaction implements Comparable<Transaction> {
        private int order;
        private int amount;

        Transaction(int order, int amount) {
            this.order = order;
            this.amount = amount;
        }

        public int compareTo(Transaction that) {
            return Integer.compare(this.amount, that.amount);
        }

        public String toString() {
            return order + ": " + amount;
        }
    }

    private static class StableKey implements Comparable<StableKey> {
        private Comparable original;
        private int index;

        public StableKey(Comparable original, int index) {
            this.original = original;
            this.index = index;
        }

        public Comparable stripIndex() {
            return original;
        }

        public int compareTo(StableKey that) {
            int cmp = this.original.compareTo(that.original);
            if (cmp < 0) {
                return -1;
            } else if (cmp > 0) {
                return 1;
            } else {
                //handle stability
                if (this.index < that.index) {
                    return -1;
                } else if (this.index > that.index) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }
    public static void sort(Comparable[] a) {
        int N = a.length;
        StableKey[] stableA = new StableKey[N];
        for (int i = 0; i < N; i++) {
            stableA[i] = new StableKey(a[i], i);
        }
        Quick.sort(stableA);
        for (int i = 0; i < N; i++) {
            a[i] = stableA[i].stripIndex();
        }
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        Transaction[] a = new Transaction[N];
        for (int i = 0 ; i < N; i++) {
            Date date = new Date();
            a[i] = new Transaction(i, (int) (Math.random() * N));
            System.out.println(a[i]);
        }
        sort(a);
        System.out.println("-------------After sorting---------------");
        for (int i = 0; i < N; i++) {
            System.out.println(a[i]);
        }
    }
}
