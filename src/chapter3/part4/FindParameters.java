package chapter3.part4;

/**
 * Ex3.4.4
 */
public class FindParameters {
    public static void main(String[] args) {
        String input = "SEARCHXMPL";
        int[] vals = new int[input.length()];
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            System.out.println(c + ": " + (c - 'A' + 1));
            vals[i] = c - 'A' + 1;
        }
        // among all the letters, 24 (X) is the largest => choose M = 24, a = 1 is guaranteed to work,
        // because all other letters will hash to a smaller number than 24, and all letters are unique.
        // Here we strive to find an even smaller M than 24
        // M has to be at least larger the string length (10) => choose initial M = 10
        int M = 10, a = 1;
        int[] freq = new int[M];
        while (true) {
            boolean noDuplicates = true;
            for (int i = 0; i < vals.length && noDuplicates; i++) {
                int h = (a * vals[i]) % M;
                if (freq[h] == 0) {
                    freq[h]++;
                } else {
                    // there's already anther character hashed to this h
                    a++;
                    freq = new int[M];
                    noDuplicates = false;
                }
            }
            if (noDuplicates) {
                // smallest M possible is M = 20, with a = 1
                System.out.println("a is: " + a);
                System.out.println("M is: " + M);
                return;
            }
            if (a == Integer.MAX_VALUE / 24) {
                M++;
                freq = new int[M];
                a = 1;
            }
        }
    }
}
