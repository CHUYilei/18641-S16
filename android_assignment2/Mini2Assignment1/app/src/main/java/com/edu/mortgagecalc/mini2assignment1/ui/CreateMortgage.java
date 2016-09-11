package com.edu.mortgagecalc.mini2assignment1.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.edu.mortgagecalc.mini2assignment1.R;
import com.edu.mortgagecalc.mini2assignment1.exception.MyException;
import com.edu.mortgagecalc.mini2assignment1.model.MortgageCalc;
import com.edu.mortgagecalc.mini2assignment1.util.DatabaseIO;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * GUI for user input info to add a new mortagage into db
 */
public class CreateMortgage extends AppCompatActivity {
    private Spinner spinnerMonth,spinnerYear;   // date picker
    private Button btnCalc;
    private MortgageCalc calculator;
    private EditText etName,etPrice, etTerm,etInterest;
    private String errMsg = "";

    private static DatabaseIO db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_mortgage);
        // find view
        initSpinners();
        initCalcButton();
        initTextView();

        if(db == null){
            db = new DatabaseIO(CreateMortgage.this);
        }
    }

    private void initTextView(){
        this.etName = (EditText) findViewById(R.id.editTextName);
        this.etPrice = (EditText) findViewById(R.id.editTextPurchasePrice);
        this.etTerm = (EditText) findViewById(R.id.editTextMortgageTerm);
        this.etInterest = (EditText) findViewById(R.id.editTextInterestRate);
    }

    private void initSpinners(){
        this.spinnerMonth = (Spinner) findViewById(R.id.spinnerMonth);
        ArrayAdapter<CharSequence> adapterMonth = ArrayAdapter.createFromResource(this,
                R.array.months, android.R.layout.simple_spinner_item);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapterMonth);
        this.spinnerMonth.setSelection(0);

        this.spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        ArrayAdapter<CharSequence> adapterYear = ArrayAdapter.createFromResource(this,
                R.array.years, android.R.layout.simple_spinner_item);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapterYear);
        spinnerYear.setSelection(0);
    }

    private void initCalcButton(){
        this.btnCalc = (Button) findViewById(R.id.buttonCalc);
        this.btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (!isValid()) {     // handle error input or exception
                showWarning();
                return;
            }

            // do in background
            AsyncTask<Object, Object, Object> calcAndSaveTask =
                new AsyncTask<Object, Object, Object>() {
                    @Override
                    protected Object doInBackground(Object... params) {

                        // calculate and store obejct into db
                        calculator.calculate();
                        calculator.storeIntoDatabase();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object result) {
                        showSucessMsg();
                    }
                }; // end task definition
            calcAndSaveTask.execute();
            }
        });
    }

    /**
     * Check validity of each field
     * if value: set local object and return true
     * else: return false
     * @return
     */
    private boolean isValid(){
        this.calculator = null;
        String name = "";
        double price = -1;
        int term = -1;
        double interest = -1;

        // extract user input
        try {
            name = this.etName.getText().toString();

            if (name.isEmpty()) {
                throw new MyException(MyException.EMPTY_NAME, this.etName);
            }

            try {
                price = Double.parseDouble(this.etPrice.getText().toString());
                if (price < 0)
                    throw new MyException(MyException.ERROR_PRICE, this.etPrice);
            } catch (NumberFormatException e) {
                throw new MyException(MyException.ERROR_PRICE, this.etPrice);
            }

            try {
                term = Integer.parseInt(this.etTerm.getText().toString());
                if (term <= 0) throw new MyException(MyException.ERROR_TERM, this.etTerm);
            } catch (NumberFormatException e) {
                throw new MyException(MyException.ERROR_TERM, this.etTerm);
            }

            try {
                interest = Double.parseDouble(this.etInterest.getText().toString());
                if (interest < 0)
                    throw new MyException(MyException.ERROR_INTEREST, this.etInterest);
            } catch (NumberFormatException e) {
                throw new MyException(MyException.ERROR_INTEREST, this.etInterest);
            }


            String month = this.spinnerMonth.getSelectedItem().toString();
            String yr = this.spinnerYear.getSelectedItem().toString();
            String firstPaymentDate = yr + "-" + month + "-01";
            String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            // construct mortgage calc
            this.calculator = new MortgageCalc(name,price,term,interest,firstPaymentDate,currentTime,db);
            return true;

        } catch (MyException e){
            e.handle(getApplicationContext());
            return false;
        }
    }

    private void showWarning(){
        new AlertDialog.Builder(CreateMortgage.this)
                .setTitle("Error input")
                .setMessage(errMsg)
            .setPositiveButton(android.R.string.yes, null)
                .show();
    }

    private void showTempResult(){
        new AlertDialog.Builder(CreateMortgage.this)
                .setTitle("Temp result")
                .setMessage(String.valueOf(this.calculator.getMonthlyPayment()))
                .setPositiveButton(android.R.string.yes, null)
                .show();
    }

    private void showSucessMsg(){
        new AlertDialog.Builder(CreateMortgage.this)
                .setTitle("Success")
                .setMessage("Mortgage calculation result has already been saved to database")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish(); // go  back to previous activity
                    }
                })
                .show();
    }

}
