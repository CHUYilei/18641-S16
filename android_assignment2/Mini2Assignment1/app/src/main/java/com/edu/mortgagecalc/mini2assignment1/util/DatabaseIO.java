package com.edu.mortgagecalc.mini2assignment1.util;

/**
 * @Author: Yilei CHU
 *
 * handling database I/O using sqlite
 *
 * @reference: http://www.tutorialspoint.com/android/android_sqlite_database.htm
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.edu.mortgagecalc.mini2assignment1.model.MortgageCalc;

public class DatabaseIO extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mortgageCalc.db";
    public static final String TABLE_NAME = "mortgage";
    public static final String ID = "_id"; // necessary for cursor
    public static final String NAME = "name";
    public static final String PRICE = "price";
    public static final String TERM = "term";
    public static final String INTEREST_RATE = "interestRate";
    public static final String FIRST_PAYMENT_DATE = "firstPaymentDate";
    public static final String MONTHLY_PAYMENT = "monthlyPayment";
    public static final String TOTAL_PAYMENT = "totalPayment";
    public static final String PAYOFF_DATE = "payoffDate";
    public static final String RECORD_TIME = "recordTime";

    // constructor
    public DatabaseIO(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table IF NOT EXISTS " + TABLE_NAME +
                        " (" + ID + " integer primary key, " +
                        NAME + " text," +
                        PRICE + " text," +
                        TERM + " text," +
                        INTEREST_RATE + " text," +
                        FIRST_PAYMENT_DATE + " text," +
                        MONTHLY_PAYMENT + " text," +
                        TOTAL_PAYMENT + " text," +
                        PAYOFF_DATE + " text," +
                        RECORD_TIME + " text" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertEntry(MortgageCalc mort)
    {
        int newId = numberOfRows();
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,newId);
        contentValues.put(NAME,mort.getUserName());
        contentValues.put(PRICE,mort.getPurchasePrice());
        contentValues.put(TERM,mort.getTermInYears());
        contentValues.put(INTEREST_RATE,mort.getInterestRate());
        contentValues.put(FIRST_PAYMENT_DATE,mort.getFirstPaymentDate());
        contentValues.put(MONTHLY_PAYMENT,mort.getMonthlyPayment());
        contentValues.put(TOTAL_PAYMENT,mort.getTotalTermPayment());
        contentValues.put(PAYOFF_DATE,mort.getPayOffDate());
        contentValues.put(RECORD_TIME,mort.getRecordTime());

        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getEntry(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+" where "+ID+"="+id, null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateEntry (Integer id, MortgageCalc mort)
    {
        deleteEntry(id);
        insertEntry(mort);
        return true;
    }

    public Integer deleteEntry (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                ID+" = ? ",
                new String[] { Integer.toString(id) });
    }

    public Cursor getAllNames()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.query(TABLE_NAME,new String[]{ID+","+NAME},null,null,null,null,null);
        return res;
    }
}
