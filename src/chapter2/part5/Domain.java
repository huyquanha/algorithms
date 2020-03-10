package chapter2.part5;

/**
 * Ex2.5.14
 */
public class Domain implements Comparable<Domain> {
    private String name;
    private String[] parts;

    public Domain(String name) {
        this.name = name.toLowerCase();
        parts = name.split(".");
    }

    public int compareTo(Domain that) {
        if (this.name.equals(that.name)) {
            return 0;
        }
        int thisLength = this.parts.length;
        int thatLength = that.parts.length;
        int commonLength = Math.min(thisLength, thatLength);
        for (int i = 1; i <= commonLength; i++) {
            //iterate through the parts in reverse order
            int cmp = this.parts[thisLength - i].compareTo(that.parts[thatLength - i]);
            if (cmp < 0) {
                return -1;
            } else if (cmp > 0) {
                return 1;
            }
        }
        //if we reach here, that means the common part of the reverse of both domains are the same
        //example: cs.princeton.edu and abc.cs.princeton.edu have reverses: edu.princeton.cs, edu.princeton.cs.abc
        //and so far the common part (edu.princeton.cs) are the same
        if (thisLength < thatLength) {
            return -1;
        } else if(thisLength > thatLength) {
            return 1;
        } else {
            return 0;
        }
    }
}
