package chapter1.part3;

import edu.princeton.cs.algs4.StdIn;

public class Exercise10 {
    public static void main(String[] args) {
        Stack<String> operators = new Stack<>();
        Stack<String> operands = new Stack<>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.equals("q")) {
                break;
            }
            else if (s.equals("(")) {

            }
            else if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")) {
                operators.push(s);
            }
            else if (s.equals(")")) {
                String opr = operators.pop();
                String right = operands.pop();
                String left = operands.pop();
                operands.push(left + " " + right + " " + opr);
            }
            else {
                operands.push(s);
            }
        }
        System.out.println(operands.pop());
    }
}
