package com.edu.mortgagecalc.mini2assignment1.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleCursorAdapter;

import com.edu.mortgagecalc.mini2assignment1.R;
import com.edu.mortgagecalc.mini2assignment1.util.DatabaseIO;

/**
 * @Author: Yilei CHU
 * March 18
 *
 * 18641 Mini2 Assignment 1 Main activity
 */
public class MainActivity extends AppCompatActivity {
    private Button btnCreateNew;
    private ListView listView;
    private static DatabaseIO db;
    private SimpleCursorAdapter adapter;
    private AsyncTask<Object, Object, Object> showNameListTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find view by id and set listener
        if(db == null){
            db = new DatabaseIO(MainActivity.this);
        }

        initAddBtn();
        initListView();
    }

    private void initAddBtn(){
        this.btnCreateNew = (Button) findViewById(R.id.buttonCreate);
        btnCreateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNewMortgage = new Intent(MainActivity.this, CreateMortgage.class);
                startActivity(addNewMortgage);
            }
        });
    }

    private void initListView(){
        this.listView = (ListView) findViewById(R.id.listViewNames);

        OnItemClickListener listener = new OnItemClickListener(){
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id){
                // pass the row id
                Intent viewDetail = new Intent(MainActivity.this,ViewMortgage.class);
                viewDetail.putExtra("ID",id);
                startActivity(viewDetail);
            }
        };
        this.listView.setOnItemClickListener(listener);


        String[] from = new String[] { "name" };
        int[] to = new int[] { R.id.textViewNameEntry };
        adapter = new SimpleCursorAdapter(
                MainActivity.this, R.layout.name_list_entry, null, from, to);
//        adapter.notifyDataSetChanged();
        this.listView.setAdapter(adapter);
    }

    @Override
    protected void onResume(){
        super.onResume();
        runShowNamesTask();
    }

    private void runShowNamesTask(){
        // do in background
        this.showNameListTask =
            new AsyncTask<Object, Object, Object>() {
                @Override
                protected Object doInBackground(Object... params) {
                    return db.getAllNames();
                }

                @Override
                protected void onPostExecute(Object cursor) {
                    adapter.changeCursor((Cursor)cursor);
                }
            }; // end task definition
        showNameListTask.execute();
    }

    @Override
    protected void onStop()
    {
        Cursor cursor = this.adapter.getCursor();
        if (cursor != null)
            cursor.deactivate(); // deactivate it
        this.adapter.changeCursor(null); // adapted now has no Cursor
        super.onStop();
    }

}
