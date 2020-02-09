package chapter1.part1;

public class Exercise9 {
    public static String convertToBinary(int n) {
        String s = "";
        while (n > 0) {
            s = (n % 2) + s;
            n = n / 2;
        }
        return s;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        System.out.println(convertToBinary(n));
    }
}
