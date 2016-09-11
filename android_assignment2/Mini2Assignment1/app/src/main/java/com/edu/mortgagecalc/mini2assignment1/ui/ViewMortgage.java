package com.edu.mortgagecalc.mini2assignment1.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.edu.mortgagecalc.mini2assignment1.R;
import com.edu.mortgagecalc.mini2assignment1.model.MortgageCalc;
import com.edu.mortgagecalc.mini2assignment1.util.DatabaseIO;

public class ViewMortgage extends AppCompatActivity {
    private TextView tvName,tvPrice,tvTerm,tvInterest,tvFirstDate,tvMonthlyPay,tvTotalPay,tvPayoffDate,tvRecordTime;
    private int id;
    private static DatabaseIO db;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_mortgage);

        initTextView();
        initBackButton();
        extractIdFromIntent();

        if(db == null){
            db = new DatabaseIO(ViewMortgage.this);
        }

        showDetails(db.getEntry(this.id));
    }

    private void initBackButton(){
        this.btnBack = (Button)findViewById(R.id.buttonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();   //should not start a new activity, otherwise native back arrow will malfunction (activity stack)
            }
        });
    }

    private void initTextView(){
        this.tvName = (TextView)findViewById(R.id.viewName);
        this.tvPrice = (TextView)findViewById(R.id.viewPrice);
        this.tvTerm = (TextView)findViewById(R.id.viewTerm);
        this.tvInterest = (TextView)findViewById(R.id.viewInterest);
        this.tvFirstDate = (TextView)findViewById(R.id.viewFirstDate);
        this.tvMonthlyPay = (TextView)findViewById(R.id.viewMonthlyPayment);
        this.tvTotalPay = (TextView)findViewById(R.id.viewTotalPayment);
        this.tvPayoffDate = (TextView)findViewById(R.id.viewPayoffDate);
        this.tvRecordTime = (TextView)findViewById(R.id.viewRecordTime);
    }

    private void extractIdFromIntent(){
        Bundle extras = getIntent().getExtras();
        this.id = (int)extras.getLong("ID");
        showID();
    }

    private void showDetails(Cursor cursor){
        cursor.moveToFirst();

        this.tvName.setText(cursor.getString(cursor.getColumnIndex(db.NAME)));
        this.tvPrice.setText(cursor.getString(cursor.getColumnIndex(db.PRICE)));
        this.tvTerm.setText(cursor.getString(cursor.getColumnIndex(db.TERM)));
        this.tvFirstDate.setText(cursor.getString(cursor.getColumnIndex(db.FIRST_PAYMENT_DATE)));
        this.tvInterest.setText(cursor.getString(cursor.getColumnIndex(db.INTEREST_RATE)));
        this.tvPayoffDate.setText(cursor.getString(cursor.getColumnIndex(db.PAYOFF_DATE)));
        this.tvRecordTime.setText(cursor.getString(cursor.getColumnIndex(db.RECORD_TIME)));
        this.tvTotalPay.setText(cursor.getString(cursor.getColumnIndex(db.TOTAL_PAYMENT)));
        this.tvMonthlyPay.setText(cursor.getString(cursor.getColumnIndex(db.MONTHLY_PAYMENT)));
    }

    private void showID(){
        System.out.println("[INFO] ID is "+this.id);
    }


}
