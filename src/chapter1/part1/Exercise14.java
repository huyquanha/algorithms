package chapter1.part1;

public class Exercise14 {
    public static int lg(int n) {
        if (n <= 0) {
            throw new NumberFormatException();
        }
        int result = 0;
        while (n > 1) {
            n = n / 2;
            result++;
        }
        return result;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        System.out.println(lg(n));
    }
}
