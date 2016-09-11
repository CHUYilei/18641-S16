package com.ychu1.assign4.geomessage.util;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.ychu1.assign4.geomessage.exception.MyException;

/**
 * Created by ychu1 on 16/4/5.
 * Helper class for sending out SMS message
 */
public class SmsHandler {
    private Context context;

    public SmsHandler(Context context){
        this.context = context;
    }

    public void sendSMSMessage(String phoneNo,String message) {
        Log.d("Will send SMS", "");

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            Toast.makeText(context, "SMS sent. Content is "+message, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            try {
                throw new MyException(MyException.ERROR_SMS);
            } catch (MyException e1) {
                e1.setSmsContent(message);
                e1.handle(context);
            }
        }
    }
}
