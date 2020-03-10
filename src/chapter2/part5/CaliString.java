package chapter2.part5;

/**
 * Ex2.5.16
 */
public class CaliString implements Comparable<CaliString> {
    private String value;
    private static int[] order = {17, 22, 16, 14, 9, 12, 21, 0, 7, 1, 18, 6, 25, 23, 13, 19, 2, 8, 4, 10, 20, 15, 3, 24, 5, 11};
    private static int[] rev;

    static void initializeReverse() {
        rev = new int[order.length];
        for (int i = 0; i < order.length; i++) {
            rev[order[i]] = i;
            //example: rev[17] or rev['R'] = 0
        }
    }

    public CaliString(String value) {
        this.value = value;
    }

    public int compareTo(CaliString that) {
        if (this == that) {
            return 0;
        }
        int minLength = Math.min(this.value.length(), that.value.length());
        for (int i = 0; i < minLength; i++) {
            char thisChar = this.value.charAt(i);
            char thatChar = that.value.charAt(i);
            int thisOrder = rev[thisChar - 'A'];
            int thatOrder = rev[thatChar - 'A'];
            if (thisOrder < thatOrder) {
                return -1;
            } else if (thisOrder > thatOrder) {
                return 1;
            }
        }
        if (this.value.length() < that.value.length()) {
            return -1;
        } else if (this.value.length() > that.value.length()){
            return 1;
        } else {
            return 0;
        }
    }
}
