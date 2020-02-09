package chapter1.part2;

public class Exercise7 {
    public static String reverse(String s) {
        int N = s.length();
        if (N <= 1) return s;
        String a = s.substring(0, N/2);
        String b = s.substring(N/2, N);
        return reverse(b) + reverse(a);
    }

    public static void main(String[] args) {
        String s = args[0];
        System.out.println(s);
        System.out.println(reverse(s));
    }
}
