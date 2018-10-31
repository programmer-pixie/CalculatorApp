package com.example.sebastia.calculator;


import android.util.Log;

import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Evaluate extends Calculator {
    final String DIVIDE_BY_0 = ("Dividing by 0 is undefined. Please try again.");
    final String OPERATOR_OPERAND_MISMATCH = "Invalid Expression. Try again";
    protected StringTokenizer tokenizer;
    ArrayDeque<Double> values;
    ArrayDeque<String> operators;
    double val1;
    double val2;
    private String result = "";

    protected Evaluate(String expression) {
        Log.e("POSTFIX: ", expression);
        tokenizer = new StringTokenizer(expression);
        values = new ArrayDeque<>();
        operators = new ArrayDeque<>();

        String token;
        String oper = "";
        while(tokenizer.hasMoreTokens()){
            token = tokenizer.nextToken();
            Log.e("TOKEN: ", token);
            if(token.contentEquals("+")||token.contentEquals("-")|| token.contentEquals("*")||token.contentEquals("/")){
                operators.add(token);
            }
            else
                values.add(Double.parseDouble(token));
        }

        if(operators.isEmpty()){
            result = expression;
        }

        while(!operators.isEmpty()){
            oper = operators.removeFirst();
            Log.e("OPERATOR: ", oper);
            if(!values.isEmpty()) {
                val1 = values.removeLast();
                Log.e("VAL1: ", String.valueOf(val1));

                if (!values.isEmpty()) {
                    val2 = values.removeLast();
                    result = findResult(oper, val1, val2);
                }
                else if(!result.contentEquals("")){
                    val2 = val1;
                    val1 = Double.parseDouble(result);
                    //val2 = Double.parseDouble(result);
                    result = findResult(oper, val1, val2);
                }
                Log.e("VAL2: ", String.valueOf(val2));
            }
            else{
                result = OPERATOR_OPERAND_MISMATCH;
                break;
            }
        }
    }

    protected String findResult(String operation, double val1, double val2){
        try {
            switch (operation) {
                case ("*"): {
                    return Double.toString(multiply(val1, val2));
                }
                case ("/"): {
                    if (val2 == 0.0) {
                        return DIVIDE_BY_0;
                    }
                    return Double.toString(divide(val1, val2));
                }
                case ("+"): {
                    return Double.toString(add(val1, val2));

                }
                case ("-"): {
                    return Double.toString(subtract(val1, val2));
                }
            }
        }catch(RuntimeException e){
            result = e.getMessage();
        }
        return Double.toString(val1);
    }

    protected double add(double oper1, double oper2) {
        return oper1 + oper2;
    }

    protected double subtract(double oper1, double oper2) {
        return oper1 - oper2;
    }

    protected double multiply(double oper1, double oper2) {
        return oper1 * oper2;
    }

    protected double divide(double oper1, double oper2) {
        Log.e("OPER1", Double.toString(oper1));
        Log.e("OPER2", Double.toString(oper2));

        return oper1 / oper2;
    }

    protected String getResult(){
        return result;
    }

}