package com.dercg.algorithmic;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Evaluate {
    public static void main(String[] args) {
        Stack<String> ops = new Stack<>();
        Stack<Double> vals = new Stack<>();

        // ( 1 + ( ( 2 + 3 ) * ( 4 * 5 ) ) )
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if ("(".equals(s)) {} else if ("+".equals(s)) ops.push(s);
            else if ("-".equals(s)) ops.push(s);
            else if ("*".equals(s)) ops.push(s);
            else if ("/".equals(s)) ops.push(s);
            else if (s.equals(")")) {
                String op = ops.pop();
                Double val = vals.pop();
                switch (op) {
                    case "+":
                        val += vals.pop();
                        break;
                    case "-":
                        val -= vals.pop();
                        break;
                    case "*":
                        val *= vals.pop();
                        break;
                    case "/":
                        val /= vals.pop();
                        break;
                }
                vals.push(val);
            } else {
                vals.push(Double.parseDouble(s));
            }

            StdOut.println(vals.pop());
        }
    }
}
