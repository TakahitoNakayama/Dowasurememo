package com.websarva.wings.android.dowasurememo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DateMemo extends AppCompatActivity {

    private Databasehelper _helper;
    private static final String _CATEGORY = "DATE1";

    /**
     * データベースのテーブル名
     */
    private static final String TABLE= "date1";

    Context context = DateMemo.this;

    EditText etDateTitle;
    EditText etDateYear;
    EditText etDateMonth;
    EditText etDateDay;
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

    androidx.fragment.app.FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_memo);

        inflater = LayoutInflater.from(getApplicationContext());
        llDateLayout = findViewById(R.id.ll_date_layout);
        llDateInputform = (LinearLayout) inflater.inflate(R.layout.date_inputform, null);

//        llDateLayout.removeAllViews();
//        DatabaseControl control4=new DatabaseControl(context,TABLE);
//        control4.deleteAllDatabase();

        /**
         * データベースの列名の配列
         */
        String[] columnNames={"datetitle","dateyear","datemonth","dateday"};

        manager=getSupportFragmentManager();

        //データベースからデータを取り出して、レイアウトを作成する処理
        DatabaseControl control=new DatabaseControl(context,TABLE,columnNames,manager);
        control.selectDatabase(llDateLayout,llDateInputform);

//        _helper = new Databasehelper(getApplicationContext());
//        SQLiteDatabase db = _helper.getWritableDatabase();
//        String sqlSelect = "SELECT * FROM date1";
//        Cursor cursor = db.rawQuery(sqlSelect, null);
//        cursor.moveToFirst();
//        while (cursor.moveToNext()) {
//            int i = cursor.getColumnIndex("_id");
//            tagId = cursor.getInt(i);
//            inflater = LayoutInflater.from(getApplicationContext());
//            llDateLayout = findViewById(R.id.ll_date_layout);
//            llDateInputform = (LinearLayout) inflater.inflate(R.layout.date_inputform, null);
//            llDateLayout.addView(llDateInputform);
//
//            llDateTitle = llDateInputform.findViewById(R.id.ll_date_title);
//            llDateSelect = llDateInputform.findViewById(R.id.ll_date_select);
//
//            etDateTitle = llDateTitle.findViewById(R.id.et_date_title);
//            etDateYear = llDateSelect.findViewById(R.id.et_date_year);
//            etDateMonth = llDateSelect.findViewById(R.id.et_date_month);
//            etDateDay = llDateSelect.findViewById(R.id.et_date_day);
//            btDelete = llDateSelect.findViewById(R.id.bt_delete);
//            btDelete.setOnClickListener
//                    (new DeleteButton(context, llDateLayout, llDateInputform, table));
//            btDateSelect = llDateSelect.findViewById(R.id.bt_date_select);
//            btDateSelect.setOnClickListener(new DatePicker());

//            i = cursor.getColumnIndex("datetitle");
//            strDateTitle = cursor.getString(i);
//
//            i = cursor.getColumnIndex("dateyear");
//            strYear = cursor.getString(i);
//
//            i = cursor.getColumnIndex("datemonth");
//            strMonth = cursor.getString(i);
//
//            i = cursor.getColumnIndex("dateday");
//            strDay = cursor.getString(i);
//
//
//            try {
//                etDateTitle.setText(strDateTitle);
//            } catch (NullPointerException e) {
//                strDateTitle = "";
//            }
//
//            try {
//                etDateYear.setText(strYear);
//            } catch (NullPointerException e) {
//                strYear = "";
//            }
//
//            try {
//                etDateMonth.setText(strMonth);
//            } catch (NullPointerException e) {
//                strMonth = "";
//            }
//
//            try {
//                etDateDay.setText(strDay);
//            } catch (NullPointerException e) {
//                strDay = "";
//            }
//        }

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
//                    control.deleteDatabase(1);
//
//                    String column1 = "datetitle";
//                    String column2 = "dateyear";
//                    String column3 = "datemonth";
//                    String column4 = "dateday";
//
//                    DatabaseControl control2 = new DatabaseControl
//                            (context, table,1, _category, str, str, str, str);
//                    control2.insertDatabaseFourColumns(column1, column2, column3,column4);
//                }
                inflater = LayoutInflater.from(getApplicationContext());
                llDateInputform = (LinearLayout) inflater.inflate(R.layout.date_inputform, null);
                llDateLayout.addView(llDateInputform);

//                llDateTitle = llDateInputform.findViewById(R.id.ll_date_title);
                llDateSelect = llDateInputform.findViewById(R.id.ll_date_select);


//                etDateTitle = llDateTitle.findViewById(R.id.et_date_title);
//                etDateYear = llDateSelect.findViewById(R.id.et_date_year);
//                etDateMonth = llDateSelect.findViewById(R.id.et_date_month);
//                etDateDay = llDateSelect.findViewById(R.id.et_date_day);
                btDelete = llDateSelect.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(DateMemo.this, llDateLayout, llDateInputform, TABLE));
                btDateSelect = llDateSelect.findViewById(R.id.bt_date_select);
                btDateSelect.setOnClickListener(new DatePicker(context,manager));

        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onPause() {
        super.onPause();

        //データベースにある全てのデータを削除
        DatabaseControl control = new DatabaseControl(context, TABLE);
        control.deleteAllDatabase();

        for (int i = 0; i < llDateLayout.getChildCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) llDateLayout.getChildAt(i);
            etDateTitle = linearLayout.findViewById(R.id.et_date_title);
            etDateYear = linearLayout.findViewById(R.id.et_date_year);
            etDateMonth = linearLayout.findViewById(R.id.et_date_month);
            etDateDay = linearLayout.findViewById(R.id.et_date_day);

            strDateTitle = etDateTitle.getText().toString();
            strYear = etDateYear.getText().toString();
            strMonth = etDateMonth.getText().toString();
            strDay = etDateDay.getText().toString();

//            DatabaseControl control = new DatabaseControl(context, table);
//            control.deleteDatabase(indexCounter);

//            String column1 = "datetitle";
//            String column2 = "dateyear";
//            String column3 = "datemonth";
//            String column4 = "dateday";

            DatabaseControl control2 = new DatabaseControl
                    (context, TABLE, i, _CATEGORY, strDateTitle,strYear,strMonth,strDay);
            control2.insertDatabaseFourColumns("datetitle","dateyear","datemonth","dateday");

//            indexCounter++;

        }
    }
}

class DatePicker extends AppCompatActivity implements View.OnClickListener {

    Context context;
    FragmentManager manager;

    public DatePicker(Context _context, androidx.fragment.app.FragmentManager _manager){
        context=_context;
        manager=_manager;
    }

    @Override
    public void onClick(View v) {
        LinearLayout parentView= (LinearLayout) v.getParent();
        EditText etYear=parentView.findViewById(R.id.et_date_year);
        EditText etMonth=parentView.findViewById(R.id.et_date_month);
        EditText etDay=parentView.findViewById(R.id.et_date_day);


        Toast.makeText
                (context,
                        "西暦の変更は左上に薄く表示されている西暦の箇所をタップしてください",
                        Toast.LENGTH_LONG).show();

        DatePickerFragment datePicker =
                new DatePickerFragment(etYear,etMonth,etDay);
        datePicker.show(manager, "datePicker");

    }
}



