package chapter1.part2;

import edu.princeton.cs.algs4.Date;

// Transaction
public class Exercise13 {
    private final String who;
    private final Date when;
    private final double amount;

    public Exercise13(String who, Date when, double amount) {
        this.who = who;
        this.when = when;
        this.amount = amount;
    }

    public String who() {
        return who;
    }

    public Date when() {return when;}

    public double amount() {return amount; }

    public String toString() {
        return who + " " + when + " " + amount;
    }

    //Exercise 14
    public boolean equals(Object object) {
        if (object == this) return true;
        if (object == null) return false;
        if (object.getClass() != this.getClass()) return false;
        Exercise13 that = (Exercise13) object;
        return that.who.equals(this.who) && that.when.equals(this.when) && that.amount == this.amount;
    }
}
