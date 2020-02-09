package chapter1.part2;

//Exercise 16
public class Rational {
    private long num, den;

    public Rational(long numerator, long denominator) {
        this.num = numerator;
        this.den = denominator;
        simplify();
    }

    public Rational plus(Rational b) {
        b.simplify();
        Rational result;
        if (this.den == b.den) {
            result = new Rational(this.num + b.num, this.den);
        } else {
            result = new Rational(this.num * b.den + b.num * this.den, this.den * b.den);
        }
        result.simplify();
        return result;
    }

    public Rational minus(Rational b) {
        Rational minusB = new Rational (-b.num, b.den);
        return plus(minusB);
    }

    public Rational times(Rational b) {
        b.simplify();
        Rational result = new Rational(this.num * b.num, this.den * b.den);
        result.simplify();
        return result;
    }

    public Rational divides(Rational b) {
        Rational inverseB = new Rational(b.den, b.num);
        return times(inverseB);
    }

    public boolean equals(Rational that) {
        that.simplify();
        if (that.num == this.num && that.den == this.den) {
            return true;
        }
        return false;
    }

    public String toString() {
        return num + "/" + den;
    }

    private void simplify() {
        long gcd = gcd(num, den);
        while (gcd != 1) {
            num /= gcd;
            den /= gcd;
            gcd = gcd(num, den);
        }
    }

    private long gcd(long n, long d) {
        if (d == 0) return n;
        long r = n % d;
        return gcd(d, r);
    }

    public static void main(String[] args) {
        int num1 = Integer.parseInt(args[0]);
        int den1 = Integer.parseInt(args[1]);
        int num2 = Integer.parseInt(args[2]);
        int den2 = Integer.parseInt(args[3]);
        Rational rat1 = new Rational(num1, den1);
        Rational rat2 = new Rational(num2, den2);
        System.out.println(rat1);
        System.out.println(rat2);
        System.out.println(rat1.plus(rat2));
        System.out.println(rat1.minus(rat2));
        System.out.println(rat1.times(rat2));
        System.out.println(rat1.divides(rat2));
    }
}
