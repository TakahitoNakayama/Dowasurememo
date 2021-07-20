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
    private String _category = "date";

    private int indexCounter=1;
    int tagId;
    String table="date";
    Context context=DateMemo.this;

    EditText etDateTitle;
    EditText etYear;
    EditText etMonth;
    EditText etDay;
    ImageButton btDelete;

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

        _helper=new Databasehelper(getApplicationContext());
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlSelect="SELECT * FROM date";
        Cursor cursor=db.rawQuery(sqlSelect,null);
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

            etDateTitle.setTag(tagId);
            etYear.setTag(tagId);
            etMonth.setTag(tagId);
            etDay.setTag(tagId);
            btDelete.setTag(tagId);

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
                EditEventListener etListener=new EditEventListener(etDateTitle,DateMemo.this);
                etDateTitle.addTextChangedListener(etListener);
            } catch (NullPointerException e) {
                strDateTitle = "";
            }

            try {
                etYear.setText(strYear);
                EditEventListener etListener2=new EditEventListener(etYear,DateMemo.this);
                etYear.addTextChangedListener(etListener2);
            } catch (NullPointerException e) {
                strYear = "";
            }

            try {
                etMonth.setText(strMonth);
                EditEventListener etListener3=new EditEventListener(etMonth,DateMemo.this);
                etMonth.addTextChangedListener(etListener3);
            } catch (NullPointerException e) {
                strMonth = "";
            }

            try {
                etDay.setText(strDay);
                EditEventListener etListener3=new EditEventListener(etDay,DateMemo.this);
                etDay.addTextChangedListener(etListener3);
            } catch (NullPointerException e) {
                strDay = "";
            }
        }

        DatabaseControl control=new DatabaseControl(context,table);
        indexCounter=control.GetIndexCounter();
        Log.d("main",""+indexCounter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.optionmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_add:
                inflater = LayoutInflater.from(getApplicationContext());
                llDateInputform = (LinearLayout) inflater.inflate(R.layout.date_inputform, null);
                llDateLayout.addView(llDateInputform);

                llDateTitle=llDateInputform.findViewById(R.id.ll_date_title);
                llDateSelect=llDateInputform.findViewById(R.id.ll_date_select);


                etDateTitle=llDateTitle.findViewById(R.id.et_date_title);
                etYear=llDateSelect.findViewById(R.id.et_date_year);
                etMonth=llDateSelect.findViewById(R.id.et_date_month);
                etDay=llDateSelect.findViewById(R.id.et_date_day);
                btDelete = llDateSelect.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(DateMemo.this,llDateLayout,llDateInputform,table));

                etDateTitle.setTag(indexCounter);
                etYear.setTag(indexCounter);
                etMonth.setTag(indexCounter);
                etDay.setTag(indexCounter);
                btDelete.setTag(indexCounter);

                EditEventListener etListener=new EditEventListener(etDateTitle,DateMemo.this);
                etDateTitle.addTextChangedListener(etListener);
                EditEventListener etListener2=new EditEventListener(etYear,DateMemo.this);
                etYear.addTextChangedListener(etListener2);
                EditEventListener etListener3=new EditEventListener(etMonth,DateMemo.this);
                etMonth.addTextChangedListener(etListener3);
                EditEventListener etListener4=new EditEventListener(etDay,DateMemo.this);
                etDay.addTextChangedListener(etListener4);

                tagId=indexCounter;
                String str="";

                DatabaseControl control=new DatabaseControl(context,table);
                control.DatabaseDelete(tagId);

                String column1="datetitle";
                String column2="dateyear";
                String column3="datemonth";
                String column4="dateday";

                DatabaseControl control2=new DatabaseControl
                        (context,table,tagId,_category,str);
                control2.DatabaseInsert(column1,column2,column3,column4);

                indexCounter++;
                control.IndexCounterUpdate(indexCounter);

        }
        return super.onOptionsItemSelected(item);
    }


}

//    public void showDatePickerDialog(View v) {
//
//        Toast.makeText
//                (DateMemo.this,
//                        "西暦の変更は左上に薄く表示されている西暦の箇所をタップしてください",
//                        Toast.LENGTH_LONG).show();
//
//        DatePickerFragment datePicker =
//                new DatePickerFragment(etYearOutput,etMonthOutput,etDayOutput);
//        datePicker.show(getSupportFragmentManager(), "datePicker");
//
//
//    }
//}

