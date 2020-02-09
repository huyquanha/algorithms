package chapter1.part1;

public class Exercise24 {
    /*
        p > q
        p = qm + r
        if x is gcd(p,q) => x divides q and x divides (qm + r)
        => x divides r
        => x is gcd of (q,r) because if exists y that > x and y is gcd(q,r)
        y will also be gcd of (q,qm + r) => wrong.
     */
    public static int gcd(int p, int q) {
        if (q==0) return p;
        int r = p%q;
        return gcd(q,r);
    }

    public static void main(String[] args) {
        int p = Integer.parseInt(args[0]);
        int q = Integer.parseInt(args[1]);
        System.out.println(gcd(p,q));
    }
}
