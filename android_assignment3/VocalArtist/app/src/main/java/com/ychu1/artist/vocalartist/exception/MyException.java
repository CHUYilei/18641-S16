package com.ychu1.artist.vocalartist.exception;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Customize exception handler
 * User input exception in CreateMortgage and MortgageCalc, provide default value when error
 */
public class MyException extends Exception {
    public static final int ERROR_NULL_PLAYER = 1;

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
            case ERROR_NULL_PLAYER:
                msg = "Currently no player";
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
