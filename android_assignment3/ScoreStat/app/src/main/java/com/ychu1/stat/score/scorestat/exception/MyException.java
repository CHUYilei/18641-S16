package com.ychu1.stat.score.scorestat.exception;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Customize exception handler
 * User input exception in CreateMortgage and MortgageCalc, provide default value when error
 */
public class MyException extends Exception {
    public static final int ERROR_FILE_NOT_FOUND = 1;

    private int errno;
    private String msg;

    public MyException(int errno){
        this.errno = errno;
    }

    /**
     * Provide default value and show toast
     * @param context
     */
    public void handle(Context context){
        switch(errno){
            case ERROR_FILE_NOT_FOUND:
                msg = "Text file not found! Please put it in assets folder";
                break;
            default:
                msg = "No such exception is supported";
        }

        Log.e("my exception",msg);

        if(context != null){
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, msg, duration);
            toast.show();
        }
    }
}
