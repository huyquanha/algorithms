package chapter2.part5;

import edu.princeton.cs.algs4.StdIn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Ex2.5.9
 */
public class SortableDataType implements Comparable<SortableDataType> {
    private Date date;
    private int amount;

    public SortableDataType(String line) throws ParseException {
        String[] fields = line.split(" ");
        String dateStr = fields[0];
        if (dateStr.length() == 8) {
            //that means the day part is a single digit
            dateStr = "0" + dateStr;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
        date = sdf.parse(dateStr);
        System.out.println(date);
        amount = Integer.parseInt(fields[1]);
    }

    public int compareTo(SortableDataType that) {
        if (this.amount < that.amount) {
            return -1;
        } else if (this.amount > that.amount) {
            return 1;
        } else {
            int cmp = this.date.compareTo(that.date);
            if (cmp < 0) {
                return 1;
            } else if (cmp > 0) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public static void main(String[] args) {
        try {
            while (StdIn.hasNextLine()) {
                String line = StdIn.readLine();
                SortableDataType record = new SortableDataType(line);
                System.out.println(record.amount);
                System.out.println(record.date);
            }
        } catch(ParseException e) {
            e.printStackTrace();
        }
    }
}
