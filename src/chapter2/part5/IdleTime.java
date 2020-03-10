package chapter2.part5;

import chapter1.part3.Queue;
import chapter2.part3.Quick;
import chapter2.part4.MinPQ;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Ex2.5.20
 */
public class IdleTime {
    private static class Job implements Comparable<Job> {
        double startTime, finishTime;

        public Job(double startTime, double finishTime) {
            this.startTime = startTime;
            this.finishTime = finishTime;
        }

        public int compareTo(Job that) {
            if (this.startTime < that.startTime) {
                return -1;
            } else if (this.startTime > that.startTime) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    private static class Event implements Comparable<Event> {
        double time;
        Job job;

        public Event(double time) {
            this.time = time;
        }

        public Event(double time, Job job) {
            this.time = time;
            this.job = job;
        }

        public int compareTo(Event that) {
            if (this.time < that.time) {
                return -1;
            } else if (this.time > that.time) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public static double[] getMaxIdleAndNonIdleTime(int N, Job[] jobs) {
        Quick.sort(jobs);
        double emptySlots = N;
        boolean isIdle = true;
        MinPQ<Event> events = new MinPQ<>();
        Queue<Job> q = new Queue<>();
        double maxIdle = 0, maxNonIdle = 0, curTime = 0, idleStart = 0, idleEnd = 0;
        for (int i = 0; i < jobs.length; i++) {
            // insert all of the job's start time to the priority queue
            events.insert(new Event(jobs[i].startTime, jobs[i]));
        }
        while (!events.isEmpty()) {
            Event next = events.delMin();
            curTime = next.time;
            Job nextJob = next.job;
            if (nextJob != null) {
                // enqueue the next job to be processed
                q.enqueue(nextJob);
            } else {
                // this is the event of a slot finishing a job
                emptySlots++;
                if (emptySlots == N) {
                    isIdle = true;
                    idleStart = curTime;
                    // idleStart will be the end of the last non-idle period, and the beginning of that is idleEnd
                    maxNonIdle = Math.max(maxNonIdle, idleStart - idleEnd);
                }
            }
            while (!q.isEmpty() && emptySlots > 0) {
                Job job = q.dequeue();
                emptySlots--;
                events.insert(new Event(job.finishTime));
                if (isIdle) {
                    // if the machine is currently idle, set it to non-idle
                    isIdle = false;
                    idleEnd = job.startTime;
                    // idleEnd will be the end of the idle period, while idleStart is the start of idle period
                    maxIdle = Math.max(maxIdle, idleEnd - idleStart);
                }
            }
        }
        double[] result = new double[2];
        result[0] = maxIdle;
        result[1] = maxNonIdle;
        return result;
    }

    public static void main(String[] args) throws IOException {
        int N = Integer.parseInt(args[0]);
        BufferedReader reader = new BufferedReader(new FileReader("src/chapter2/part5/idleTimeTest.txt"));
        String line;
        List<Job> jobs = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            String[] fields = line.split("\t");
            double startTime = Double.parseDouble(fields[0]);
            double duration = Double.parseDouble(fields[1]);
            double finishTime = startTime + duration;
            Job job = new Job(startTime, finishTime);
            jobs.add(job);
        }
        Job[] jobArr = new Job[jobs.size()];
        double[] maxTimes = getMaxIdleAndNonIdleTime(N, jobs.toArray(jobArr));
        System.out.println("Max idle time: " + maxTimes[0]);
        System.out.println("Max non-idle time: " + maxTimes[1]);
    }
}
