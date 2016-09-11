package com.ychu1.artist.vocalartist.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ychu1.artist.vocalartist.R;

/**
 * Created by caixiaomo on 16/3/31.
 */
public class EmailFragment extends Fragment {
    private Button btnSubscribe;
    private EditText etName,etEmail;
    private Button btnBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.email_page, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {
        this.btnSubscribe = (Button) v.findViewById(R.id.email_btnSubscribe);
        this.etName = (EditText) v.findViewById(R.id.email_etName);
        this.etEmail = (EditText) v.findViewById(R.id.email_etEmail);

        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage();
            }
        });

        initBackButton(v);
    }

    private void showMessage(){
        new AlertDialog.Builder(getActivity())
            .setTitle("Success!")
            .setMessage("Dear "+this.etName.getText().toString() +",an verification email has been sent to "+this.etEmail.getText().toString()+".")
            .setPositiveButton(android.R.string.yes, null)
            .show();
    }

    private void initBackButton(View v){
        this.btnBack = (Button) v.findViewById(R.id.email_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();   //should not start a new activity, otherwise native back arrow will malfunction (activity stack)
            }
        });
    }
}
