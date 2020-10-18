package chapter4.part2.creativeProblems;

import chapter1.part3.Queue;
import chapter4.part2.Digraph;
import chapter4.part2.TarjanSCC;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Ex4.2.26
 */
public class TwoSatisfiability {
    private static class Variable {
        Boolean value;
        int id;

        public Variable(int id) {
            this.id = id;
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj.getClass() != this.getClass()) return false;
            Variable that = (Variable) obj;
            return that.id == this.id;
        }

        public int hashCode() {
            return id;
        }

        public String toString() {
            return id + ": " + value;
        }
    }

    private static class Literal {
        Variable var;
        boolean negated;

        public Literal(Variable var, boolean negated) {
            this.var = var;
            this.negated = negated;
        }

        public Literal negate() {
            return new Literal(new Variable(var.id), !negated);
        }

        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj.getClass() != this.getClass()) return false;
            Literal that = (Literal) obj;
            return that.var.equals(this.var) && that.negated == this.negated;
        }

        public int hashCode() {
            return (var.hashCode() * 31 + Boolean.hashCode(negated)) & 0x7fffffff;
        }

        public String toString() {
            return negated ? "!(" + var.toString() + ")" : "(" + var.toString() + ")";
        }
    }

    private static class Clause {
        Literal x;
        Literal y;

        public Clause(Literal x, Literal y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "[" + x.toString() + " || " + y.toString() + "]";
        }
    }

    private chapter1.part3.Queue<Variable> variables;
    private boolean satisfiable;

    public TwoSatisfiability(Clause[] clauses) {
        Map<Literal, Integer> st = new HashMap<>();
        for (Clause clause : clauses) {
            if (!st.containsKey(clause.x)) {
                st.put(clause.x, st.size());
            }
            if (!st.containsKey(clause.y)) {
                st.put(clause.y, st.size());
            }
            if (!st.containsKey(clause.x.negate())) {
                st.put(clause.x.negate(), st.size());
            }
            if (!st.containsKey(clause.y.negate())) {
                st.put(clause.y.negate(), st.size());
            }
        }
        Literal[] literals = new Literal[st.size()];
        for (Literal literal : st.keySet()) {
            literals[st.get(literal)] = literal;
        }
        Digraph g = new Digraph(st.size());
        for (Clause clause : clauses) {
            Literal x = clause.x;
            Literal y = clause.y;
            g.addEdge(st.get(x.negate()), st.get(y));
            g.addEdge(st.get(y.negate()), st.get(x));
        }
        variables = new Queue<>();
        satisfiable = true;
        TarjanSCC scc = new TarjanSCC(g);
        Iterable<Integer>[] components = scc.getComponents();
        boolean[] marked = new boolean[components.length];
        for (int i = 0; i < components.length; i++) {
            if (!marked[i]) {
                int negateId = -1;
                for (int v : components[i]) {
                    Literal literal = literals[v];
                    Literal negation = literal.negate();
                    int w = st.get(negation);
                    if (negateId == -1) {
                        negateId = scc.id(w);
                    }
                    if (i == negateId) {
                        StdOut.println(literal + " is in the same SCC with " + negation);
                        satisfiable = false;
                        break;
                    }
                }
                if (!satisfiable) {
                    break;
                }
                // satisfiable is still true
                for (Variable v : markComponent(literals, components[i], true)) {
                    variables.enqueue(v);
                }
                for (Variable v : markComponent(literals, components[negateId], false)) {
                    variables.enqueue(v);
                }
                marked[i] = true;
                marked[negateId] = true;
            }
        }
    }

    public boolean isSatisfiable() { return satisfiable; }

    public Iterable<Variable> getVariables() {
        if (!isSatisfiable()) return null;
        return variables;
    }

    private Queue<Variable> markComponent(Literal[] literals, Iterable<Integer> component, boolean value) {
        Queue<Variable> result = new Queue<>();
        for (int v : component) {
            Literal literal = literals[v];
            literal.var.value = value;
            if (!literal.negated) {
               result.enqueue(literal.var);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String formula = args[0];
        Clause[] clauses = Arrays.stream(formula.split("&&"))
            .map(s -> {
                // Assuming each clause is enclosed in (), we remove the brackets
                String trimmed = s.trim().substring(1, s.trim().length() - 1);
                Literal[] literals = Arrays.stream(trimmed.split("\\|\\|")).map(l -> {
                   String trimmedLiteral = l.trim();
                   // We assume the literal is in the form of x123 or !x123
                   boolean negated = false;
                   int idStart = 1;
                   if (trimmedLiteral.startsWith("!")) {
                       negated = true;
                       idStart = 2;
                   }
                   Variable var = new Variable(Integer.parseInt(trimmedLiteral.substring(idStart)));
                   return new Literal(var, negated);
                }).toArray(Literal[]::new);
                return new Clause(literals[0], literals[1]);
            })
            .toArray(Clause[]::new);
        TwoSatisfiability twoSatisfiability = new TwoSatisfiability(clauses);
        StdOut.println(twoSatisfiability.isSatisfiable());
        if (twoSatisfiability.isSatisfiable()) {
            for (Variable var : twoSatisfiability.getVariables()) {
                StdOut.println(var);
            }
        }
    }
}
