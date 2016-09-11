package com.edu.mortgagecalc.mini2assignment1.model;

import com.edu.mortgagecalc.mini2assignment1.exception.MyException;
import com.edu.mortgagecalc.mini2assignment1.util.DatabaseIO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @Author: Yilei CHU
 */
public class MortgageCalc {
    // input
    private String userName;
    private double purchasePrice;
    private int termInYears;    //years
    private double interestRate;    //%
    private String firstPaymentDate;

    private SimpleDateFormat format = null;

    private String recordTime;

    // result
    private double monthlyPayment;
    private double totalTermPayment;
    private String payOffDate;

    // database
    private DatabaseIO db;

    public MortgageCalc(String userName, double purchasePrice, int termInYears, double interestRate, String firstPaymentDate,String recordTime,DatabaseIO db) {
        this.userName = userName;
        this.purchasePrice = purchasePrice;
        this.termInYears = termInYears;
        this.interestRate = interestRate;
        this.firstPaymentDate = firstPaymentDate;
        this.recordTime = recordTime;
        this.db = db;

        format = new SimpleDateFormat("yyyy-MM-dd");
        //Note: calculate and store into db should be called from outside
    }

    /**
     * calculate and set the output
     *
     * @reference http://learn-java-by-example.com/java/monthly-payment-calculator/
     *
     */
    public void calculate(){
        // convert interest rate
        double realInterestRate = this.interestRate / 100;
        double monthlyRate = realInterestRate / 12.0;
        int termInMonth = this.termInYears * 12;

        double denom = (1-Math.pow(1+monthlyRate,-termInMonth));

        try {
            if (denom == 0) {
                throw new MyException(MyException.DIVIDE_BY_ZERO, null);
            }

            this.monthlyPayment = purchasePrice * monthlyRate / denom;
        } catch (MyException e ){
            e.handle(null);
            this.monthlyPayment = e.getDefault_result();
        }

        this.totalTermPayment = this.monthlyPayment * termInMonth;
        calcPayoffDate();
    }

    public void storeIntoDatabase(){
        db.insertEntry(this);
    }

    /**
     * Calculate payoff date
     */
    private void calcPayoffDate(){
        // extract date from string YYYY-mm-dd
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(format.parse(this.firstPaymentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // compute
        cal.add(Calendar.MONTH, this.termInYears * 12);

        // turn into string
        this.payOffDate = format.format(cal.getTime());
    }

    /* getters */
    public String getMonthlyPayment() {
        return String.format("%.2f", monthlyPayment);
    }

    public String getTotalTermPayment() {
        return String.format("%.2f", totalTermPayment);
    }

    public String getPayOffDate() {
        return payOffDate.substring(0,payOffDate.lastIndexOf("-"));
    }

    public String getUserName() {
        return userName;
    }

    public String getPurchasePrice() {
        return String.valueOf(purchasePrice);
    }

    public String getTermInYears() {
        return String.valueOf(termInYears);
    }

    public String getInterestRate() {
        return String.valueOf(interestRate);
    }

    public String getFirstPaymentDate() {
        return firstPaymentDate.substring(0,firstPaymentDate.lastIndexOf("-"));
    }

    public String getRecordTime() {
        return recordTime;
    }
}
