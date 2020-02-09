package chapter2.part1;

import chapter1.part3.Queue;
import edu.princeton.cs.algs4.Date;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Transaction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SortTransactions {
    public static void main(String[] args) throws ParseException {
        In in  = new In();
        Queue<Transaction> queue = new Queue<>();
        while (!in.isEmpty()) {
            String who = in.readString();
            String whenStr = in.readString();
            Date when = new Date(whenStr);
            double amount = in.readDouble();
            Transaction t = new Transaction(who, when, amount);
            queue.enqueue(t);
        }
        int N = queue.size();
        Transaction[] a = new Transaction[N];
        for (int i= 0; i< N ; i++) {
            a[i] = queue.dequeue();
        }
        Shell.sort(a);
        for (Transaction t : a) {
            System.out.println(t);
        }
    }
}
