package chapter1.part2;

public class Exercise6 {
    public static boolean detectShift(String s, String t) {
        return s.length() == t.length() && (s+s).indexOf(t) != -1;
    }

    public static void main(String[] args) {
        String s = args[0];
        String t = args[1];
        System.out.println(detectShift(s,t));
    }
}
