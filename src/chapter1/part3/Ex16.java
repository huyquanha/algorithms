package chapter1.part3;

import edu.princeton.cs.algs4.Date;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Transaction;

public class Ex16 {
    public static Date[] readDates(String name) {
        In in = new In(name);
        Queue<Date> q = new Queue<>();
        while (!in.isEmpty()) {
            String dateStr = in.readString();
            Date date = formatDate(dateStr);
            q.enqueue(date);
        }
        int N = q.size();
        Date[] dates = new Date[N];
        for (int i = 0; i < N; i++) {
            dates[i] = q.dequeue();
        }
        return dates;
    }

    public static Transaction[] readTransactions(String name) {
        In in = new In(name);
        Queue<Transaction> q = new Queue<>();
        while (!in.isEmpty()) {
            String customer = in.readString();
            String dateStr = in.readString();
            double amount = in.readDouble();
            Date date = formatDate(dateStr);
            Transaction t = new Transaction(customer, date, amount);
            q.enqueue(t);
        }
        int N = q.size();
        Transaction[] transactions = new Transaction[N];
        for (int i = 0; i < N; i++) {
            transactions[i] = q.dequeue();
        }
        return transactions;
    }

    private static Date formatDate(String dateStr) {
        String[] fields = dateStr.split("/");
        int month = Integer.parseInt(fields[0]);
        int day = Integer.parseInt(fields[1]);
        int year = Integer.parseInt(fields[2]);
        Date date = new Date(month, day, year);
        return date;
    }
}
