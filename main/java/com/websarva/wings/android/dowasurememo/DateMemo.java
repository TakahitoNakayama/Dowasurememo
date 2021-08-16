package com.websarva.wings.android.dowasurememo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Calendar;

public class DateMemo extends AppCompatActivity {

    private Databasehelper _helper;
    private String _category = "date1";

    private int indexCounter = 2;
    int tagId;
    String table = "date1";
    Context context = DateMemo.this;

    EditText etDateTitle;
    EditText etYear;
    EditText etMonth;
    EditText etDay;
    ImageButton btDelete;
    ImageButton btDateSelect;

    LayoutInflater inflater;
    LinearLayout llDateLayout;
    LinearLayout llDateInputform;
    LinearLayout llDateTitle;
    LinearLayout llDateSelect;

    String strDateTitle;
    String strYear;
    String strMonth;
    String strDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_memo);

        Intent intent = getIntent();
        llDateLayout = findViewById(R.id.ll_date_layout);

//        llDateLayout.removeAllViews();
//        DatabaseControl control4=new DatabaseControl(context,table);
//        control4.DatabaseAllDelete();

        _helper = new Databasehelper(getApplicationContext());
        SQLiteDatabase db = _helper.getWritableDatabase();
        String sqlSelect = "SELECT * FROM date1";
        Cursor cursor = db.rawQuery(sqlSelect, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int i = cursor.getColumnIndex("_id");
            tagId = cursor.getInt(i);
            inflater = LayoutInflater.from(getApplicationContext());
            llDateLayout = findViewById(R.id.ll_date_layout);
            llDateInputform = (LinearLayout) inflater.inflate(R.layout.date_inputform, null);
            llDateLayout.addView(llDateInputform);

            llDateTitle = llDateInputform.findViewById(R.id.ll_date_title);
            llDateSelect = llDateInputform.findViewById(R.id.ll_date_select);

            etDateTitle = llDateTitle.findViewById(R.id.et_date_title);
            etYear = llDateSelect.findViewById(R.id.et_date_year);
            etMonth = llDateSelect.findViewById(R.id.et_date_month);
            etDay = llDateSelect.findViewById(R.id.et_date_day);
            btDelete = llDateSelect.findViewById(R.id.bt_delete);
            btDelete.setOnClickListener
                    (new DeleteButton(context, llDateLayout, llDateInputform, table));
            btDateSelect = llDateSelect.findViewById(R.id.bt_date_select);
            btDateSelect.setOnClickListener(new DatePicker());

            i = cursor.getColumnIndex("datetitle");
            strDateTitle = cursor.getString(i);

            i = cursor.getColumnIndex("dateyear");
            strYear = cursor.getString(i);

            i = cursor.getColumnIndex("datemonth");
            strMonth = cursor.getString(i);

            i = cursor.getColumnIndex("dateday");
            strDay = cursor.getString(i);


            try {
                etDateTitle.setText(strDateTitle);
            } catch (NullPointerException e) {
                strDateTitle = "";
            }

            try {
                etYear.setText(strYear);
            } catch (NullPointerException e) {
                strYear = "";
            }

            try {
                etMonth.setText(strMonth);
            } catch (NullPointerException e) {
                strMonth = "";
            }

            try {
                etDay.setText(strDay);
            } catch (NullPointerException e) {
                strDay = "";
            }
        }

//        if(llDateLayout.getChildCount()!=0){
//            LinearLayout firstView= (LinearLayout) llDateLayout.getChildAt(0);
//            firstView.setVisibility(View.GONE);
//        }else {
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_add:
//                if(llDateLayout.getChildCount()==0){
//                    inflater = LayoutInflater.from(getApplicationContext());
//                    llDateLayout = findViewById(R.id.ll_date_layout);
//                    llDateInputform = (LinearLayout) inflater.inflate(R.layout.date_inputform, null);
//                    llDateLayout.addView(llDateInputform);
//                    llDateInputform.setVisibility(View.GONE);
//
//                    String str="";
//                    DatabaseControl control = new DatabaseControl(context, table);
//                    control.DatabaseDelete(1);
//
//                    String column1 = "datetitle";
//                    String column2 = "dateyear";
//                    String column3 = "datemonth";
//                    String column4 = "dateday";
//
//                    DatabaseControl control2 = new DatabaseControl
//                            (context, table,1, _category, str, str, str, str);
//                    control2.DatabaseInsertFourColumns(column1, column2, column3,column4);
//                }
                inflater = LayoutInflater.from(getApplicationContext());
                llDateInputform = (LinearLayout) inflater.inflate(R.layout.date_inputform, null);
                llDateLayout.addView(llDateInputform);

                llDateTitle = llDateInputform.findViewById(R.id.ll_date_title);
                llDateSelect = llDateInputform.findViewById(R.id.ll_date_select);


                etDateTitle = llDateTitle.findViewById(R.id.et_date_title);
                etYear = llDateSelect.findViewById(R.id.et_date_year);
                etMonth = llDateSelect.findViewById(R.id.et_date_month);
                etDay = llDateSelect.findViewById(R.id.et_date_day);
                btDelete = llDateSelect.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(DateMemo.this, llDateLayout, llDateInputform, table));
                btDateSelect = llDateSelect.findViewById(R.id.bt_date_select);
                btDateSelect.setOnClickListener(new DatePicker());

        }
        return super.onOptionsItemSelected(item);
    }

    public class DatePicker implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            LinearLayout parentView= (LinearLayout) v.getParent();
            EditText etYear=parentView.findViewById(R.id.et_date_year);
            EditText etMonth=parentView.findViewById(R.id.et_date_month);
            EditText etDay=parentView.findViewById(R.id.et_date_day);


            Toast.makeText
                    (DateMemo.this,
                            "西暦の変更は左上に薄く表示されている西暦の箇所をタップしてください",
                            Toast.LENGTH_LONG).show();

            DatePickerFragment datePicker =
                    new DatePickerFragment(etYear,etMonth,etDay);
            datePicker.show(getSupportFragmentManager(), "datePicker");

        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        indexCounter = 2;

        for (int i = 0; i < llDateLayout.getChildCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) llDateLayout.getChildAt(i);
            etDateTitle = linearLayout.findViewById(R.id.et_date_title);
            etYear = linearLayout.findViewById(R.id.et_date_year);
            etMonth = linearLayout.findViewById(R.id.et_date_month);
            etDay = linearLayout.findViewById(R.id.et_date_day);

            strDateTitle = etDateTitle.getText().toString();
            strYear = etYear.getText().toString();
            strMonth = etMonth.getText().toString();
            strDay = etDay.getText().toString();

            DatabaseControl control = new DatabaseControl(context, table);
            control.DatabaseDelete(indexCounter);

            String column1 = "datetitle";
            String column2 = "dateyear";
            String column3 = "datemonth";
            String column4 = "dateday";

            DatabaseControl control2 = new DatabaseControl
                    (context, table, indexCounter, _category, strDateTitle,strYear,strMonth,strDay);
            control2.DatabaseInsertFourColumns(column1, column2, column3, column4);

            indexCounter++;

        }
    }
}





