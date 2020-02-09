package chapter1.part1;

public class Exercise20 {
    private static double ln(long i) {
        return Math.log(i) / Math.log(Math.E);
    }

    //this is equivalent to ln(N!) because
    //ln(1*2*3...*N) = ln(1) + ln(2) + ... + ln(N);
    public static double function(int i) {
        if (i == 1) return 0;
        return ln(i) + function(i - 1);
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        System.out.println(N + " " + function(N));
        //this is only to prove that our implementation is correct
        long result = 1;
        for (int i = 1; i <= N; i++) {
            result *= i;
        }
        System.out.println(ln(result));
    }
}
