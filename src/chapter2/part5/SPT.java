package chapter2.part5;

import edu.princeton.cs.algs4.StdIn;

/**
 * Ex2.5.12
 */
public class SPT {
    private static class Job implements Comparable<Job> {
        String name;
        double time;

        public Job(String line) {
            String[] fields = line.split(" ");
            name = fields[0];
            time = Double.parseDouble(fields[1]);
        }

        public int compareTo(Job that) {
            if (this.time < that.time) {
                return -1;
            } else if (this.time > that.time) {
                return 1;
            } else {
                return 0;
            }
        }

        public String toString() {
            return name + " " + time;
        }
    }

    private static Job[] pq = new Job[2];
    private static int N = 0;

    public static void main(String[] args) {
        while (StdIn.hasNextLine()) {
            String line = StdIn.readLine();
            Job job = new Job(line);
            insert(job);
        }
        while (N > 0) {
            Job min = delMin();
            System.out.println(min);
        }
    }

    private static Job delMin() {
        Job min = pq[1];
        exch(1, N--);
        pq[N + 1] = null;
        sink(1);
        return min;
    }

    private static void insert(Job job) {
        if (N + 1== pq.length) {
            resize(2 * pq.length);
        }
        N++;
        pq[N] = job;
        swim(N);
    }

    private static void swim(int k) {
        while (k > 1 && less(k, k/2)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    private static void sink(int k) {
        while (k * 2 <= N) {
            int j = k * 2;
            if (j < N && less(j + 1, j)) j++;
            if (!less(j, k)) break;
            exch(j, k);
            k = j;
        }
    }

    private static boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private static void exch(int i, int j) {
        Job tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
    }

    private static void resize(int newSz) {
        Job[] tmp = new Job[newSz];
        for (int i = 1; i <= N; i++) {
            tmp[i] = pq[i];
        }
        pq = tmp;
    }
}
