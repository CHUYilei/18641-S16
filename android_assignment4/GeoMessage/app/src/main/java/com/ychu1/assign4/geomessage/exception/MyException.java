package com.ychu1.assign4.geomessage.exception;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Customize exception handler
 */
public class MyException extends Exception {
    public static final int ERROR_GPS_NETWORK = 1;
    public static final int ERROR_SMS = 2;
    public static final int ERROR_PHONENOEMPTY = 3;
    private static final String defaultPhoneNo = "4129323558";

    private int errno;
    private String msg;
    private String smsContent;
    private EditText etPhone;

    public MyException(int errno){
        this.errno = errno;
    }

    public void setSmsContent(String content){
        smsContent = content;
    }

    public void setEtPhoneNo(EditText et){
        etPhone = et;
        System.err.println("[exception] et has been set to have text "+ et.getText().toString());
    }

    /**
     * Provide default value and show toast
     * @param context
     */
    public void handle(Context context){
        switch(errno){
            case ERROR_GPS_NETWORK:
                msg = "Location requires GPS or network";
                Log.e("[Geo]","network and GPS unavailable");
                System.err.println("[Geo] network and GPS unavailable");
                break;
            case ERROR_SMS:
                msg = "SMS failed, please try again. The content is "+smsContent;
                Log.e("[SMS]", "SMS failed, please try again.");
                System.err.println("[SMS] SMS failed, please try again.");
                break;
            case ERROR_PHONENOEMPTY:
                msg = "Invalid phone number! Phone number has been automatically corrected to "+ defaultPhoneNo;
                Log.e("[SMS]",msg);
                etPhone.setText(defaultPhoneNo, TextView.BufferType.EDITABLE);
                System.err.println("[SMS] "+msg);
                break;
            default:
                msg = "No such exception is supported";
        }

        if(context != null){
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, msg, duration);
            toast.show();
        }
    }
}
