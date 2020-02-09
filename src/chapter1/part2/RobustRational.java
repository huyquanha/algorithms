package chapter1.part2;

// Exercise17
public class RobustRational {
    private int num, den;

    public RobustRational(int numerator, int denominator) {
        this.num = numerator;
        this.den = denominator;
        simplify();
    }

    public RobustRational plus(RobustRational b) {
        b.simplify();
        RobustRational result;
        if (this.den == b.den) {
            assert inRange((long) this.num + b.num) : "Overflow damn it";
            result = new RobustRational(this.num + b.num, this.den);
        } else {
            assert inRange((long) this.num * b.den + (long) b.num * this.den)
                    && inRange((long)this.den * b.den) : "Overflow damn it";
            result = new RobustRational(this.num * b.den + b.num * this.den, this.den * b.den);
        }
        result.simplify();
        return result;
    }

    public RobustRational minus(RobustRational b) {
        RobustRational minusB = new RobustRational(-b.num, b.den);
        return plus(minusB);
    }

    public RobustRational times(RobustRational b) {
        b.simplify();
        assert inRange((long)this.num * b.num) && inRange((long)this.den * b.den);
        RobustRational result = new RobustRational(this.num * b.num, this.den * b.den);
        result.simplify();
        return result;
    }

    public RobustRational divides(RobustRational b) {
        RobustRational inverseB = new RobustRational(b.den, b.num);
        return times(inverseB);
    }

    public boolean equals(RobustRational that) {
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
        int gcd = gcd(num, den);
        while (gcd != 1) {
            num /= gcd;
            den /= gcd;
            gcd = gcd(num, den);
        }
    }

    private int gcd(int n, int d) {
        if (d == 0) return n;
        int r = n % d;
        return gcd(d, r);
    }

    private boolean inRange(long a) {
        return a >= Integer.MIN_VALUE && a <= Integer.MAX_VALUE;
    }

    public static void main(String[] args) {
        int num1 = Integer.parseInt(args[0]);
        int den1 = Integer.parseInt(args[1]);
        int num2 = Integer.parseInt(args[2]);
        int den2 = Integer.parseInt(args[3]);
        RobustRational rat1 = new RobustRational(num1, den1);
        RobustRational rat2 = new RobustRational(num2, den2);
        System.out.println(rat1);
        System.out.println(rat2);
        System.out.println(rat1.plus(rat2));
        System.out.println(rat1.minus(rat2));
        System.out.println(rat1.times(rat2));
        System.out.println(rat1.divides(rat2));
    }
}
