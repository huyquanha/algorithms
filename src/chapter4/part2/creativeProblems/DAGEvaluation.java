package chapter4.part2.creativeProblems;

import chapter4.part2.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class DAGEvaluation {
    private String[] values;
    private boolean[] marked;
    private double result;

    /**
     * we assume that if the operator is "-" or "/", the smaller index vertex is the left operand
     * and the larger index vertex is the right operand
     * @param g
     * @param values
     */
    public DAGEvaluation(Digraph g, String[] values) {
        this.values = values;
        marked = new boolean[g.v()];
        result = evaluate(g, 0);
    }

    private double evaluate(Digraph g, int v) {
        marked[v] = true;
        double leftOperand = Double.NaN, rightOperand = Double.NaN;
        int curIdx = -1;
        for (int w : g.adj(v)) {
            double wVal;
            if (!marked[w]) {
                wVal = evaluate(g, w);
                values[w] = Double.toString(wVal);
            } else {
                // w is marked => its value has already been calculated and replaced with literal value
                wVal = Double.parseDouble(values[w]);
            }
            if (curIdx == -1) {
                leftOperand = wVal;
                curIdx = w;
            } else {
                if (w < curIdx) {
                    rightOperand = leftOperand;
                    leftOperand = wVal;
                } else {
                    rightOperand = wVal;
                }
            }
        }
        if (curIdx == -1) {
            // v is leaf node => a literal value
            return Double.parseDouble(values[v]);
        } else {
            // v is an operator with 2 children
            switch (values[v]) {
                case "+":
                    return leftOperand + rightOperand;
                case "-":
                    return leftOperand - rightOperand;
                case "*":
                    return leftOperand * rightOperand;
                case "/":
                    return leftOperand / rightOperand;
                default:
                    return Double.NaN;
            }
        }
    }

    public double getResult() { return result; }

    public static void main(String[] args) {
        Digraph g = new Digraph(new In(args[0]));
        String[] values = new String[g.v()];
        In in = new In(args[1]);
        while (!in.isEmpty()) {
            // parts[0] is the index and parts[1] is the value (either a literal value or an operator)
            String[] parts = in.readLine().split(" ");
            values[Integer.parseInt(parts[0])] = parts[1];
        }
        DAGEvaluation dagEval = new DAGEvaluation(g, values);
        StdOut.println(dagEval.getResult());
    }
}
