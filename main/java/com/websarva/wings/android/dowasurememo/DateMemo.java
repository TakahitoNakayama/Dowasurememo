package com.websarva.wings.android.dowasurememo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
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

    EditText etDateTitle;
    EditText etYear;
    EditText etMonth;
    EditText etDay;
    LayoutInflater inflater;
    LinearLayout llDateLayout;
    LinearLayout llDateInputform;
    LinearLayout llDateTitle;
    LinearLayout llDateSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_memo);

        Intent intent = getIntent();

        _helper=new Databasehelper(getApplicationContext());
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlSelect="SELECT * FROM date WHERE _id = 8";
        Cursor cursor=db.rawQuery(sqlSelect,null);
        cursor.moveToFirst();
        int i = cursor.getColumnIndex("_id");
        tagId = cursor.getInt(i);
        Log.d("main",""+tagId);

        inflater = LayoutInflater.from(getApplicationContext());
        llDateLayout = findViewById(R.id.ll_date_layout);
        llDateInputform = (LinearLayout) inflater.inflate(R.layout.date_inputform, null);
        llDateLayout.addView(llDateInputform);

        llDateTitle=llDateInputform.findViewById(R.id.ll_date_title);
        llDateSelect=llDateInputform.findViewById(R.id.ll_date_select);
        ImageButton btDelete=llDateSelect.findViewById(R.id.bt_delete);
        btDelete.setOnClickListener
                (new DeleteButton(DateMemo.this,llDateLayout,llDateInputform));


        etDateTitle=llDateTitle.findViewById(R.id.et_date_title);
        etYear=llDateSelect.findViewById(R.id.et_date_year);
        etMonth=llDateSelect.findViewById(R.id.et_date_month);
        etDay=llDateSelect.findViewById(R.id.et_date_day);
//        Button btDateSelect=findViewById(R.id.bt_date_select);


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
                llDateLayout=findViewById(R.id.ll_date_layout);
                llDateInputform = (LinearLayout) inflater.inflate(R.layout.date_inputform, null);
                llDateLayout.addView(llDateInputform);

                llDateTitle=llDateInputform.findViewById(R.id.ll_date_title);
                llDateSelect=llDateInputform.findViewById(R.id.ll_date_select);
                ImageButton btDelete=llDateSelect.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(DateMemo.this,llDateLayout,llDateInputform));

                etDateTitle=llDateTitle.findViewById(R.id.et_date_title);
                etYear=llDateSelect.findViewById(R.id.et_date_year);
                etMonth=llDateSelect.findViewById(R.id.et_date_month);
                etDay=llDateSelect.findViewById(R.id.et_date_day);

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

                _helper=new Databasehelper(DateMemo.this);
                SQLiteDatabase db=_helper.getWritableDatabase();

                String sqlDelete="DELETE FROM date WHERE _id = ?";
                SQLiteStatement statement=db.compileStatement(sqlDelete);
                statement.bindLong(1,tagId);
                statement.executeUpdateDelete();

                String sqlInsert=
                        "INSERT INTO date" +
                                "(_id,category,datetitle,dateyear,datemonth,dateday) " +
                                "VALUES(?,?,?,?,?,?)";
                statement=db.compileStatement(sqlInsert);
                statement.bindLong(1,tagId);
                statement.bindString(2,_category);
                statement.bindString(3,str);
                statement.bindString(4,str);
                statement.bindString(5,str);
                statement.bindString(6,str);
                statement.executeInsert();


                indexCounter++;

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

