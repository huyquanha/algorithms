package chapter2.part5;

import chapter2.part3.Quick;
import edu.princeton.cs.algs4.StdIn;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Ex2.5.13
 */
public class LPT {
    private class Job implements Comparable<Job> {
        String name;
        double time;

        public Job(String line) {
            String[] fields = line.split(" ");
            name = fields[0];
            time = Double.parseDouble(fields[1]);
        }

        //so we can sort them in reverse order
        public int compareTo(Job that) {
            if (this.time < that.time) {
                return 1;
            } else if (this.time > that.time) {
                return -1;
            } else {
                return 0;
            }
        }

        public String toString() {
            return name + " " + time;
        }
    }

    private class Processor implements Comparable<Processor> {
        List<Job> jobs = new ArrayList<>();
        double totalTime = 0;
        int id;

        public Processor(int id) {
            this.id = id;
        }

        public void addJob(Job job) {
            jobs.add(job);
            totalTime += job.time;
        }

        public int compareTo(Processor that) {
            if (this.totalTime < that.totalTime) {
                return -1;
            } else if (this.totalTime > that.totalTime) {
                return 1;
            } else {
                return 0;
            }
        }

        public String toString() {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("Processor id: " + id + "\n");
            for (Job j : jobs) {
                strBuilder.append(j);
                strBuilder.append("; ");
            }
            strBuilder.append("\n");
            strBuilder.append("Total time: " + totalTime + "\n");
            return strBuilder.toString();
        }
    }

    private Job[] jobs;
    private Processor[] processors;
    private int p = 0; //the current number of processors in the priority queue

    public LPT(int M) {
        processors = new Processor[M + 1];
        p = M;
        for (int i = 1; i <= p; i++) {
            //since all are equal, this is a minimum priority queue already
            processors[i] = new Processor(i);
        }
    }

    public void loadJobs(String[] jobStrs) {
        int N = jobStrs.length;
        jobs = new Job[N];
        for (int i = 0; i < N; i++) {
            jobs[i] = new Job(jobStrs[i]);
        }
        Quick.sort(jobs);
        for (int i = 0; i < N; i++) {
            Job job = jobs[i]; //get the most time consuming job left
            Processor nextProcessor = delMin(); //get the processor with the minimum processing time so far
            nextProcessor.addJob(job);
            insert(nextProcessor);
        }
    }

    public void printSchedule() {
        while (p > 0) {
            Processor min = delMin();
            System.out.print(min);
        }
    }

    private Processor delMin() {
        Processor min = processors[1];
        exch(1, p--);
        processors[p + 1] = null;
        sink(1);
        return min;
    }

    private void insert(Processor processor) {
        p++;
        processors[p] = processor;
        swim(p);
    }

    private void swim(int k) {
        while (k > 1 && less(k, k / 2)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (k * 2 <= p) {
            int j = k * 2;
            if (j < p && less(j + 1, j)) j++;
            if (!less(j, k)) break;
            exch(j, k);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        return processors[i].totalTime < processors[j].totalTime;
    }

    private void exch(int i, int j) {
        Processor tmp = processors[i];
        processors[i] = processors[j];
        processors[j] = tmp;
    }

    public static void main(String[] args) {
        int M = Integer.parseInt(args[0]);
        LPT lpt = new LPT(M);
        String[] jobStrs = StdIn.readAllLines();
        lpt.loadJobs(jobStrs);
        lpt.printSchedule();
    }
}
