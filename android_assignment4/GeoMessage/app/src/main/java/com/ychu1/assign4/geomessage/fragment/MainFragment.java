package com.ychu1.assign4.geomessage.fragment;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ychu1.assign4.geomessage.R;
import com.ychu1.assign4.geomessage.exception.MyException;
import com.ychu1.assign4.geomessage.util.GeoLocator;
import com.ychu1.assign4.geomessage.util.SmsHandler;

/**
 * fragment created in main activity
 */
public class MainFragment extends Fragment {
    private Button btnSend;
    private static GeoLocator locator;
    private static Location location;
    private static SmsHandler smsHandler;
    private EditText etPhoneNo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        this.etPhoneNo = (EditText) v.findViewById(R.id.main_etPhoneNo);
        initButtons(v);

        return v;
    }

    private void initButtons(View v){
        this.btnSend = (Button)v.findViewById(R.id.main_btnSend);

        //allocate listeners
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locator == null) locator = new GeoLocator(getActivity());
                location = locator.getLocation();
                String locStr = "Location not available!";
                if (location != null)
                    locStr = "location: latitude " + location.getLatitude() + ",longitude " + location.getLongitude();

                try {
                    if (smsHandler == null) smsHandler = new SmsHandler(getActivity());

                    String phoneNo = getPhoneNo();
                    if (phoneNo == null || phoneNo.isEmpty()) {
                        throw new MyException(MyException.ERROR_PHONENOEMPTY);
                    }

                    smsHandler.sendSMSMessage(phoneNo, locStr);
                } catch (MyException e) {
                    System.err.println("[test] "+getEtPhoneNum());
                    e.setEtPhoneNo(getEtPhoneNum());
                    e.handle(getCurrentActivity());
                }
            }
        });
    }

    private String getPhoneNo(){
        return this.etPhoneNo.getText().toString();
    }
    private EditText getEtPhoneNum(){
        return this.etPhoneNo;
    }

    private Activity getCurrentActivity(){
        return getActivity();
    }

}
