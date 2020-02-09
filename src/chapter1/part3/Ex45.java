package chapter1.part3;

//Stack underflow: try to pop an item from emtpy stack
public class Ex45 {
    public static boolean underflow(String[] ops) {
        int pushes = 0;
        int pops = 0;
        for (String op : ops) {
            if (op.equals("-")) {
                pops++;
            } else { //op is a number
                pushes++;
            }
            if (pops > pushes) {
                //at any given point in time, if pops > pushes, underflow will happen
                return true;
            }
        }
        return false;
    }

    public static boolean isValidOutput(String[] args) {
        Stack<Integer> stack = new Stack<>();
        int N = args.length;
        int[] numbers = new int[N];
        for (int i = 0; i < N; i++) {
            numbers[i] = Integer.parseInt(args[i]);
        }
        int maxPush = -1;
        for (int i = 0; i < N; i++) {
            if (numbers[i] > maxPush) {
                for (int j = maxPush + 1; j <= numbers[i]; j++) {
                    stack.push(j);
                }
                maxPush = numbers[i];
                stack.pop();
            } else {
                if (numbers[i] != stack.peek()) {
                    return false;
                } else {
                    stack.pop();
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isValidOutput(args));
    }
}
