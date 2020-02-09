package chapter1.part3;

public class Exercise12 {
    public static Stack<String> copy(Stack<String> stack) {
        Stack<String> tmp = new Stack<>();
        // tmp will be reverse order of stack
        for (String s : stack) {
            tmp.push(s);
        }
        Stack<String> result = new Stack<>();
        // result will be reverse order of tmp, which is the same order with stack => perfect copy
        for (String s : tmp) {
            result.push(s);
        }
        return result;
    }
}
