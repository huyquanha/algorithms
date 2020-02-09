package chapter1.part3;

import edu.princeton.cs.algs4.StdIn;

public class Exercise4 {
    private static boolean isBalanced(String left, String right) {
        return (left.equals("(") && right.equals(")")) ||
                (left.equals("[")&& right.equals("]")) ||
                (left.equals("{") && right.equals("}"));
    }

    public static void main(String[] args) {
        Stack<String> parentheses = new Stack<>();
        boolean isBalanced = true;
        while (isBalanced && !StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("q")) {
                break;
            }
            else if (s.equals("(") || s.equals("[") || s.equals("{")) {
                parentheses.push(s);
            }
            else {
                if (parentheses.isEmpty()) {
                    isBalanced = false;
                }
                String left = parentheses.pop();
                if (!isBalanced(left,s)) {
                    isBalanced = false;
                }
            }
        }
        System.out.println(isBalanced);
    }
}
