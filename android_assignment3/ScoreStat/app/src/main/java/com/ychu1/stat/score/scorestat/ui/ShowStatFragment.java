package com.ychu1.stat.score.scorestat.ui;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ychu1.stat.score.scorestat.R;
import com.ychu1.stat.score.scorestat.exception.MyException;
import com.ychu1.stat.score.scorestat.model.QuizStatistics;
import com.ychu1.stat.score.scorestat.model.Student;
import com.ychu1.stat.score.scorestat.util.DatabaseIO;
import com.ychu1.stat.score.scorestat.util.FileIO;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 */
public class ShowStatFragment extends Fragment {
    private static DatabaseIO db;
    private static String inputFile = "input.txt";
    private static QuizStatistics statistics;
    private TableLayout recordLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        constructDBFromTxt();
        if(statistics == null) statistics = new QuizStatistics(5);
    }

    private void constructDBFromTxt(){
        if(db == null){
            db = new DatabaseIO(getActivity());
        }

        InputStream ins = null;
        try {
            ins = ((Activity)getActivity()).getAssets().open(inputFile);
            List<Student> list = FileIO.readFile(ins);
            for(Student student:list){
                db.insertEntry(student);
            }
        } catch (IOException e) {
            try {
                throw new MyException(MyException.ERROR_FILE_NOT_FOUND);
            } catch (MyException e1) {
                e1.handle(getActivity());
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.show_statistics, container, false);

        this.recordLayout = (TableLayout)v.findViewById(R.id.show_tablelayout_records);

        showAllRecord(v);
        showStatistics(v);

        return v;
    }

    /**
     * Stud         Q1         Q2         Q3         Q4         Q5
     * 1234         78         83         87         91         86
     */
    private void showAllRecord(View v){
        this.statistics.clearList(); //clear old records
        /* table headings row */
        TableRow tr_head = new TableRow(getActivity());
        tr_head.setBackgroundColor(Color.GRAY);
        tr_head.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        /* head labels */
        TextView labelStud = new TextView(getActivity());
        labelStud.setText("Stud");
        labelStud.setPadding(5, 5, 5, 5);
        tr_head.addView(labelStud);

        String texts[] = {"Q1","Q2","Q3","Q4","Q5"};

        for (int i = 0; i < 5; i++) {
            TextView labelQ = new TextView(getActivity());
            labelQ.setText(texts[i]);
            labelQ.setPadding(5, 5, 5, 5);
            tr_head.addView(labelQ);
        }

        /* add row to table */
        this.recordLayout.addView(tr_head,new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));

        /* query db to show the scores */
        Cursor cursor = db.getAll();
        while (cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex(db.ID));
            String scores[] = new String[5];

            for (int i = 1; i < 6; i++) {
                scores[i-1] = cursor.getString(cursor.getColumnIndex("Q"+i));
            }

            this.statistics.addStudentToList(new Student(id,scores));

            TableRow row = new TableRow(getActivity());
            row.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView tvID = new TextView(getActivity());
            tvID.setText(id);
            tvID.setPadding(5,5,5,5);
            row.addView(tvID);

            for (int i = 0; i < 5; i++) {
                TextView tvTemp = new TextView(getActivity());
                tvTemp.setText(scores[i]);
                tvTemp.setPadding(5,5,5,5);
                row.addView(tvTemp);
            }

            this.recordLayout.addView(row);
        }

    }

    private void showStatistics(View v){
        // do calculation
        this.statistics.calculate();
        String[] high = this.statistics.getHighscores();
        String[] low = this.statistics.getLowscores();
        String[] avg = this.statistics.getAvgscores();

        String[][] result = {high,low,avg};

        // show result
        String[] texts = {"High Score","Low Score","Average"};

        for (int i = 0; i < texts.length; i++) {
            TableRow row = new TableRow(getActivity());
            row.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView tvID = new TextView(getActivity());
            tvID.setText(texts[i]);
            tvID.setPadding(5,5,5,5);
            row.addView(tvID);

            for (int j = 0; j < 5; j++) {
                TextView tvTemp = new TextView(getActivity());
                tvTemp.setText(result[i][j]);
                tvTemp.setPadding(5,5,5,5);
                row.addView(tvTemp);
            }

            this.recordLayout.addView(row);
        }
    }

}
