package com.example.sebastia.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Stack;

public class Calculator extends AppCompatActivity {

    //Exception messages
    final String NO_OPAREN = "Parenthesis mismatch. Try again";
    protected int openParen = 0;
    boolean prevIsNumber = false;
    Stack<String> operatorStack = new Stack<>();
    String postfix = "";
    String value = "";
    String expression = "";
    TextView outputMessage;

//takes user input and switches it to a postfix notation. THe postfix string is passed to the Evaluate class to find the final solution

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        View v = new View(this);
        outputMessage = findViewById(R.id.output);

        //NUMBER BUTTON HANDLERS

        findViewById(R.id.oneBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkVals(true, "1");
            }
        });

        findViewById(R.id.twoBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkVals(true, "2");
            }
        });

        findViewById(R.id.threeBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkVals(true, "3");
            }
        });

        findViewById(R.id.fourBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkVals(true, "4");
            }
        });

        findViewById(R.id.fiveBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkVals(true, "5");
            }
        });

        findViewById(R.id.sixBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkVals(true, "6");
            }
        });

        findViewById(R.id.sevenBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkVals(true, "7");
            }
        });
        findViewById(R.id.eightBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkVals(true, "8");
            }
        });
        findViewById(R.id.nineBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkVals(true, "9");
            }
        });
        findViewById(R.id.zeroBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkVals(true, "0");
            }
        });

        //OPERATOR BUTTON HANDLERS

        //+
        findViewById(R.id.plusBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkVals(false, "+");
            }
        });

        //-
        findViewById(R.id.minusBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkVals(false, "-");
            }
        });

        //*
        findViewById(R.id.multBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkVals(false, "*");
            }
        });

        // /
        findViewById(R.id.divBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkVals(false, "/");
            }
        });

        //LEFT (OPEN) PAREN BUTTON HANDLER
        findViewById(R.id.oParenBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //first checks if a number is ready to be added to postfix
                if (prevIsNumber) {
                    postfix = postfix.concat(value + " ");
                    value = "";
                }
                //then checks if the expression ends with a right paren. If so, this signifies multiplication
                else if (expression.endsWith(")")) {
                    expression = expression.concat(" * ( ");
                }
                //if neither, handle as usual
                else {
                    expression = expression.concat(" ( ");
                }
                outputMessage.setText(expression);
                openParen++;
                operatorStack.push("(");
                prevIsNumber = false;
            }
        });

        //RIGHT (CLOSED) PAREN BUTTON HANDLER
        findViewById(R.id.cParenBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //check if value is ready to be added to postfix
                if (prevIsNumber) {
                    postfix = postfix.concat(value + " ");
                    value = "";
                }
                prevIsNumber = false;
                //if there is a matching open parenthesis
                if (openParen > 0) {
                    while (!operatorStack.isEmpty()) {
                        if (!operatorStack.peek().equals("(")) {
                            postfix = postfix.concat(operatorStack.pop() + " ");
                        } else {
                            operatorStack.pop();
                            break;
                        }
                    }
                    openParen--;
                    expression = expression.concat(" ) ");

                }
                else{
                    expression = NO_OPAREN;
                }
                outputMessage.setText(expression);
            }
        });

        //EQUAL BUTTON HANDLER
        findViewById(R.id.equalBtn).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (prevIsNumber) {
                    postfix = postfix.concat(value + " ");
                    value = "";
                }
                while (!operatorStack.isEmpty()) {
                    postfix = postfix.concat(operatorStack.pop() + " ");
                }
                if (openParen > 0) {
                    expression = NO_OPAREN;
                } else {
                    Evaluate eval = new Evaluate(postfix);
                    expression = eval.getResult();
                }

                outputMessage.setText(expression);
                prevIsNumber = false;
                value = "";
                expression = "";
                postfix = "";
            }
        });


        //CLR BUTTON HANDLER
        findViewById(R.id.clearBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                prevIsNumber = false;
                operatorStack = new Stack<>();
                postfix = "";
                value = "";
                openParen = 0;
                expression = "";
                outputMessage.setText(expression);
            }
        });
    }

    //Determines if this click is an addition to the current value, a new value, or an operator.
    private void checkVals(boolean isNumber, String token) {
        if (isNumber) {
            value = value.concat(token);
            prevIsNumber = true;
            expression = expression.concat(token);
            outputMessage.setText(expression);
        }
        else if (prevIsNumber) {
            postfix = postfix.concat(value + " ");
            value = "";
            process(token);
        }
        else {
            process(token);
        }

    }


    //METHODS

    //determines due to the operator's preference whether it is pushed to the hold stack or directly added to the postfix expression
    private void process(String token) {
        String temp = "";
        while(true){
            if (operatorStack.isEmpty() || getOrder(token) > getOrder(operatorStack.peek())) {
                operatorStack.push(token);
                break;
            } else {
                postfix = postfix.concat(operatorStack.pop() + " ");
            }
        }
        prevIsNumber = false;
        expression = expression.concat(" " + token + " ");
        outputMessage.setText(expression);
    }

    //returns the precidence give to each type of operator
    private int getOrder(String token) {
        if (token.contentEquals("+") || token.contentEquals("-")) {
            return 1;
        }
        else if(token.contentEquals("*") || token.contentEquals("/")) {
            return 2;
        }
        else
            return 0;
    }
}