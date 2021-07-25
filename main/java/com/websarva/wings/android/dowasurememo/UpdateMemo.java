package com.websarva.wings.android.dowasurememo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class UpdateMemo extends AppCompatActivity {

    private Databasehelper _helper;
    private String _category = "update1";

    private int indexCounter=1;
    int tagId=1;
    String table="update1";
    Context context=UpdateMemo.this;

    LayoutInflater inflater;
    LinearLayout linearLayout;
    LinearLayout llUpdateLayout;
    LinearLayout llUpdateInputform;
    LinearLayout llUpdateTitle;
    LinearLayout llUpdateDeadline;

    EditText etUpdateTitle;
    EditText etUpdateYear;
    EditText etUpdateMonth;
    EditText etUpdateDay;
    ImageButton btDelete;
    ImageButton btDateSelect;

    String strUpdateTitle;
    String strUpdateYear;
    String strUpdateMonth;
    String strUpdateDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_memo);

        Intent intent = getIntent();
        llUpdateLayout = findViewById(R.id.ll_update_layout);

        _helper=new Databasehelper(getApplicationContext());
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlSelect="SELECT * FROM update1";
        Cursor cursor=db.rawQuery(sqlSelect,null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int i = cursor.getColumnIndex("_id");
            tagId = cursor.getInt(i);
            inflater = LayoutInflater.from(getApplicationContext());
            llUpdateLayout = findViewById(R.id.ll_update_layout);
            llUpdateInputform=(LinearLayout)inflater.inflate(R.layout.update_inputform,null);
            llUpdateLayout.addView(llUpdateInputform);

            llUpdateTitle = llUpdateInputform.findViewById(R.id.ll_update_title);
            llUpdateDeadline = llUpdateTitle.findViewById(R.id.ll_update_deadline);

            etUpdateTitle = llUpdateTitle.findViewById(R.id.et_update_title);
            etUpdateYear = llUpdateDeadline.findViewById(R.id.et_update_year);
            etUpdateMonth = llUpdateDeadline.findViewById(R.id.et_update_month);
            etUpdateDay = llUpdateDeadline.findViewById(R.id.et_update_day);
            btDelete = llUpdateDeadline.findViewById(R.id.bt_delete);
            btDelete.setOnClickListener
                    (new DeleteButton(UpdateMemo.this,llUpdateLayout,llUpdateInputform,table));
            btDateSelect = llUpdateDeadline.findViewById(R.id.bt_date_select);
            btDateSelect.setOnClickListener(new UpdateMemo.DatePicker());


            etUpdateTitle.setTag(tagId);
            etUpdateYear.setTag(tagId);
            etUpdateMonth.setTag(tagId);
            etUpdateDay.setTag(tagId);
            btDelete.setTag(tagId);



            i = cursor.getColumnIndex("updatetitle");
            strUpdateTitle = cursor.getString(i);

            i = cursor.getColumnIndex("updateyear");
            strUpdateYear = cursor.getString(i);

            i = cursor.getColumnIndex("updatemonth");
            strUpdateMonth = cursor.getString(i);

            i = cursor.getColumnIndex("updateday");
            strUpdateDay = cursor.getString(i);


            try {
                etUpdateTitle.setText(strUpdateTitle);
                EditEventListener etListener=new EditEventListener(etUpdateTitle,UpdateMemo.this);
                etUpdateTitle.addTextChangedListener(etListener);
            } catch (NullPointerException e) {
                strUpdateTitle = "";
            }

            try {
                etUpdateYear.setText(strUpdateYear);
                EditEventListener etListener2=new EditEventListener(etUpdateYear,UpdateMemo.this);
                etUpdateYear.addTextChangedListener(etListener2);
            } catch (NullPointerException e) {
                strUpdateYear = "";
            }

            try {
                etUpdateMonth.setText(strUpdateMonth);
                EditEventListener etListener3=new EditEventListener(etUpdateMonth,UpdateMemo.this);
                etUpdateMonth.addTextChangedListener(etListener3);
            } catch (NullPointerException e) {
                strUpdateMonth = "";
            }

            try {
                etUpdateDay.setText(strUpdateDay);
                EditEventListener etListener3=new EditEventListener(etUpdateDay,UpdateMemo.this);
                etUpdateDay.addTextChangedListener(etListener3);
            } catch (NullPointerException e) {
                strUpdateDay = "";
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
                llUpdateInputform=(LinearLayout)inflater.inflate(R.layout.update_inputform,null);
                llUpdateLayout.addView(llUpdateInputform);

                llUpdateTitle=llUpdateInputform.findViewById(R.id.ll_update_title);
                llUpdateDeadline=llUpdateTitle.findViewById(R.id.ll_update_deadline);

                etUpdateTitle=llUpdateTitle.findViewById(R.id.et_update_title);
                etUpdateYear=llUpdateDeadline.findViewById(R.id.et_update_year);
                etUpdateMonth=llUpdateDeadline.findViewById(R.id.et_update_month);
                etUpdateDay=llUpdateDeadline.findViewById(R.id.et_update_day);
                btDelete=llUpdateDeadline.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(UpdateMemo.this,llUpdateLayout,llUpdateInputform,table));
                btDateSelect = llUpdateDeadline.findViewById(R.id.bt_date_select);
                btDateSelect.setOnClickListener(new UpdateMemo.DatePicker());

                etUpdateTitle.setTag(indexCounter);
                etUpdateYear.setTag(indexCounter);
                etUpdateMonth.setTag(indexCounter);
                etUpdateDay.setTag(indexCounter);
                btDelete.setTag(indexCounter);

                EditEventListener etListener=new EditEventListener(etUpdateTitle,UpdateMemo.this);
                etUpdateTitle.addTextChangedListener(etListener);
                EditEventListener etListener2=new EditEventListener(etUpdateYear,UpdateMemo.this);
                etUpdateYear.addTextChangedListener(etListener2);
                EditEventListener etListener3=new EditEventListener(etUpdateMonth,UpdateMemo.this);
                etUpdateMonth.addTextChangedListener(etListener3);
                EditEventListener etListener4=new EditEventListener(etUpdateDay,UpdateMemo.this);
                etUpdateDay.addTextChangedListener(etListener4);

                tagId=indexCounter;
                String str="";

                DatabaseControl control=new DatabaseControl(context,table);
                control.DatabaseDelete(tagId);

                String column1="updatetitle";
                String column2="updateyear";
                String column3="updatemonth";
                String column4="updateday";

                DatabaseControl control2=new DatabaseControl
                        (context,table,tagId,_category,str);
                control2.DatabaseInsert(column1,column2,column3,column4);

                indexCounter++;
                control.IndexCounterUpdate(indexCounter);



        }
        return super.onOptionsItemSelected(item);
    }

    public class DatePicker implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            LinearLayout parentView= (LinearLayout) v.getParent();
            EditText etYear=parentView.findViewById(R.id.et_update_year);
            EditText etMonth=parentView.findViewById(R.id.et_update_month);
            EditText etDay=parentView.findViewById(R.id.et_update_day);


            Toast.makeText
                    (UpdateMemo.this,
                            "西暦の変更は左上に薄く表示されている西暦の箇所をタップしてください",
                            Toast.LENGTH_LONG).show();

            DatePickerFragment datePicker =
                    new DatePickerFragment(etYear,etMonth,etDay);
            datePicker.show(getSupportFragmentManager(), "datePicker");

        }
    }

}