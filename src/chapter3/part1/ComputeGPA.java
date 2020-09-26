package chapter3.part1;

import chapter1.BinarySearch;
import edu.princeton.cs.algs4.StdIn;

/**
 * Ex3.1.1
 */
public class ComputeGPA {
    private static void populateSymbolTable(BinarySearchST<String, Double> st) {
        // just add other values
        st.put("A+", 4.33);
        st.put("A-", 3.67);
    }

    public static void main(String[] args) {
        BinarySearchST<String, Double> st = new BinarySearchST<>();
        populateSymbolTable(st);
        double totalScore = 0;
        int count = 0;
        while (!StdIn.isEmpty()) {
            String grade = StdIn.readLine();
            if (st.contains(grade)) {
                count++;
                totalScore += st.get(grade);
            }
        }
        double avg = totalScore / count;
        System.out.println("Average score: " + avg);
    }
}
