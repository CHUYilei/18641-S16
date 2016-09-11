package com.edu.mortgagecalc.mini2assignment1.exception;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Customize exception handler
 * User input exception in CreateMortgage and MortgageCalc, provide default value when error
 */
public class MyException extends Exception {
    public static final int ERROR_PRICE = 1;
    public static final int ERROR_INTEREST = 2;
    public static final int ERROR_TERM = 3;
    public static final int EMPTY_NAME = 4;
    public static final int DIVIDE_BY_ZERO = 5;

    private EditText et;
    private int errno;
    private String msg;
    private int default_result;

    public MyException(int errno, EditText et){
        this.errno = errno;
        this.et = et;
    }

    /**
     * Provide default value and show toast
     * @param context
     */
    public void handle(Context context){
        switch(errno){
            case ERROR_PRICE:
                et.setText("1000");
                msg = "Purchase price is set to default value 1000";
                break;
            case ERROR_INTEREST:
                et.setText("10");
                msg = "Interest rate is set to default 10";
                break;
            case EMPTY_NAME:
                et.setText("new user");
                msg = "Name is set to default new user";
                break;
            case ERROR_TERM:
                et.setText("5");
                msg = "Term is set to default 5 years";
                break;
            case DIVIDE_BY_ZERO:
                default_result = 0;
            default:
                msg = "No such exception is supported";
        }

        Log.e("input exception",msg);

        if(context != null){
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, msg, duration);
            toast.show();
        }
    }

    public int getDefault_result() {
        return default_result;
    }
}
