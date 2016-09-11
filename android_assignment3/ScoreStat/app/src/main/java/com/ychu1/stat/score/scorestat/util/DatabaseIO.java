package com.ychu1.stat.score.scorestat.util;

/**
 * @Author: Yilei CHU
 *
 * handling database I/O using sqlite
 *
 * @reference: http://www.tutorialspoint.com/android/android_sqlite_database.htm
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.ychu1.stat.score.scorestat.model.Student;

public class DatabaseIO extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "quizscore.db";
    public static final String TABLE_NAME = "score";
    public static final String ID = "_id"; // necessary for cursor
    public static final String Q1 = "Q1";
    public static final String Q2 = "Q2";
    public static final String Q3 = "Q3";
    public static final String Q4 = "Q4";
    public static final String Q5 = "Q5";
    public static final String ORDER_ID = "ORDER_ID";

    private static int orderCnt = 0;


    // constructor
    public DatabaseIO(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table IF NOT EXISTS " + TABLE_NAME +
                        " (" + ID + " integer primary key, " +
                        Q1 + " TEXT," +
                        Q2 + " TEXT," +
                        Q3 + " TEXT," +
                        Q4 + " TEXT," +
                        Q5 + " TEXT," +
                        ORDER_ID + " integer" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertEntry(Student student)
    {
        if(isIdExists(student.getId())) return false;

        orderCnt++;
        SQLiteDatabase db = this.getWritableDatabase();
        double[] temp = student.getScores();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,student.getId());
        contentValues.put(Q1,String.valueOf(temp[0]));
        contentValues.put(Q2,String.valueOf(temp[1]));
        contentValues.put(Q3,String.valueOf(temp[2]));
        contentValues.put(Q4,String.valueOf(temp[3]));
        contentValues.put(Q5,String.valueOf(temp[4]));
        contentValues.put(ORDER_ID,orderCnt);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getEntry(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+" where "+ID+"="+id, null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public Integer deleteEntry (String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,
                ID+" = ? ",
                new String[] { id });
    }

    public Cursor getAll()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+" ORDER BY "+ORDER_ID, null );
        return res;
    }

    public boolean isIdExists(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+" where "+ID+"="+id, null );
        return res.getCount() > 0;
    }
}
