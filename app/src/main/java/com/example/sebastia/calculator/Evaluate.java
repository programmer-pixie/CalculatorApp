package com.example.sebastia.calculator;


import android.util.Log;

import java.util.ArrayDeque;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Evaluate extends Calculator {
    final String DIVIDE_BY_0 = ("Cannot divide by 0. Undefined.");
    final String OPERATOR_OPERAND_MISMATCH = "Invalid Expression. Try again";
    Stack<Double> values;

    double val1;
    double val2;
    private String result;

    protected Evaluate(){};
    //contructor takes in a postfix expression and solves it
    protected Evaluate(String expression) {
        values = new Stack<>();
        String[] tokens = expression.split(" ");

        //try catch will catch any divide by zero exception
        try {
            //goes through each piece of the expression, puttion values in the values stack and executing operations
            for (String token : tokens) {

                switch (token) {
                    case "+":
                        values.push(add(values.pop(), values.pop()));
                        break;
                    case "-":
                        values.push(subtract(values.pop(), values.pop()));
                        break;
                    case "*":
                        values.push(multiply(values.pop(), values.pop()));
                        break;
                    //before diviision is completed, the denominator is verified not 0, otherwise an exception is thrown
                    case "/":
                        val1 = values.pop();
                        val2 = values.pop();

                        if (val1 == 0) {
                            result = DIVIDE_BY_0;
                            throw new RuntimeException(DIVIDE_BY_0);
                        } else
                            values.push(divide(val1, val2));
                        break;
                    default:
                        values.push(Double.parseDouble(token));
                        break;
                }
            }
            result = String.valueOf(values.pop());
        }
        //CATCH:
        catch(RuntimeException e){
            result = e.getMessage();
        }
    }

    //Math operations
    //adds two numbers
    protected double add(double oper2, double oper1) {
        return oper1 + oper2;
    }
    //subtracts one number from another
    protected double subtract(double oper2, double oper1) {
        return oper1 - oper2;
    }
    //multiplies one number times another
    protected double multiply(double oper2, double oper1) {
        return oper1 * oper2;
    }
    //divides one number by another
    protected double divide(double oper2, double oper1) {
        return oper1 / oper2;
    }

    //getResult returns the result found
    protected String getResult(){
        return result;
    }

}