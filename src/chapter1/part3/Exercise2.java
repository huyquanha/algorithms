package chapter1.part3;

public class Exercise2 {
    private static void print(Stack<String> stack) {
        for (String s : stack) {
            System.out.print(s+ " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        for (String s : args) {
            if (s.equals("-")) {
                stack.pop();
            }
            else {
                stack.push(s);
            }
            print(stack);
        }
    }
}
