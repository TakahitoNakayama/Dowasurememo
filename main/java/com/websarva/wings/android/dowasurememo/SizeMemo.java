package com.websarva.wings.android.dowasurememo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SizeMemo extends AppCompatActivity {

    private Databasehelper _helper;
    private String _category = "size";
    Context context = SizeMemo.this;
    String table = "size";

    String strBodyPart;
    String strRecord;
    String strUnit;

    EditText etBodyPart;
    EditText etRecord;
    EditText etUnit;
    ImageButton btDelete;

    LinearLayout llSizeLayout;
    LayoutInflater inflater;
    LinearLayout llSizeInputform;

    private int indexCounter = 2;
    int tagId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_memo);

        Intent intent = getIntent();
        llSizeLayout = findViewById(R.id.ll_size_layout);

//        llSizeLayout.removeAllViews();
//        DatabaseControl control4=new DatabaseControl(context,table);
//        control4.DatabaseAllDelete();

        _helper = new Databasehelper(getApplicationContext());
        SQLiteDatabase db = _helper.getWritableDatabase();
        String sqlSelect = "SELECT * FROM size";
        Cursor cursor = db.rawQuery(sqlSelect, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int i = cursor.getColumnIndex("_id");
            tagId = cursor.getInt(i);
            Log.d("oncreate66",""+tagId);

            inflater = LayoutInflater.from(getApplicationContext());
            llSizeInputform = (LinearLayout) inflater.inflate(R.layout.size_inputform, null);
            llSizeLayout.addView(llSizeInputform);
            ImageView circle = llSizeInputform.findViewById(R.id.circle);
            circle.setColorFilter(Color.rgb(127, 255, 212));

            etBodyPart = llSizeInputform.findViewById(R.id.et_bodypart);
            etRecord = llSizeInputform.findViewById(R.id.et_record);
            etUnit = llSizeInputform.findViewById(R.id.et_unit);
            btDelete = llSizeInputform.findViewById(R.id.bt_delete);
            btDelete.setOnClickListener
                    (new DeleteButton(context,llSizeLayout,llSizeInputform,table));

            i = cursor.getColumnIndex("bodypart");
            strBodyPart = cursor.getString(i);

            i = cursor.getColumnIndex("records");
            strRecord = cursor.getString(i);

            i = cursor.getColumnIndex("unit");
            strUnit = cursor.getString(i);


            try {
                etBodyPart.setText(strBodyPart);
            } catch (NullPointerException e) {
                strBodyPart = "";
            }

            try {
                etRecord.setText(strRecord);
            } catch (NullPointerException e) {
                strRecord = "";
            }

            try {
                etUnit.setText(strUnit);
            } catch (NullPointerException e) {
                strUnit = "";
            }

        }

        if(llSizeLayout.getChildCount()!=0){
            LinearLayout firstView= (LinearLayout) llSizeLayout.getChildAt(0);
            firstView.setVisibility(View.GONE);
        }else {
        }
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
                if(llSizeLayout.getChildCount()==0){
                    inflater = LayoutInflater.from(getApplicationContext());
                    llSizeInputform=(LinearLayout)inflater.inflate(R.layout.size_inputform,null);
                    llSizeLayout.addView(llSizeInputform);
                    llSizeInputform.setVisibility(View.GONE);

                    String str="";
                    DatabaseControl control = new DatabaseControl(context, table);
                    control.DatabaseDelete(1);

                    String column1 = "bodypart";
                    String column2 = "records";
                    String column3 = "unit";

                    DatabaseControl control2 = new DatabaseControl
                            (context, table,1, _category, str, str, str);
                    control2.DatabaseInsertThreeColumns(column1, column2, column3);
                }

                llSizeLayout=findViewById(R.id.ll_size_layout);
                inflater = LayoutInflater.from(getApplicationContext());
                llSizeInputform = (LinearLayout) inflater.inflate(R.layout.size_inputform, null);
                llSizeLayout.addView(llSizeInputform);

                ImageView circle = llSizeInputform.findViewById(R.id.circle);
                circle.setColorFilter(Color.rgb(127, 255, 212));

                etBodyPart = llSizeInputform.findViewById(R.id.et_bodypart);
                etRecord = llSizeInputform.findViewById(R.id.et_record);
                etUnit = llSizeInputform.findViewById(R.id.et_unit);
                btDelete = llSizeInputform.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(context,llSizeLayout,llSizeInputform,table));
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPause() {
        super.onPause();

        indexCounter = 2;

        for (int i = 0; i < llSizeLayout.getChildCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) llSizeLayout.getChildAt(i);
            etBodyPart = linearLayout.findViewById(R.id.et_bodypart);
            etRecord = linearLayout.findViewById(R.id.et_record);
            etUnit = linearLayout.findViewById(R.id.et_unit);

            strBodyPart = etBodyPart.getText().toString();
            strRecord = etRecord.getText().toString();
            strUnit = etUnit.getText().toString();

            DatabaseControl control = new DatabaseControl(context, table);
            control.DatabaseDelete(indexCounter);

            String column1 = "bodypart";
            String column2 = "records";
            String column3 = "unit";

            DatabaseControl control2 = new DatabaseControl
                    (context, table, indexCounter, _category, strBodyPart, strRecord, strUnit);
            control2.DatabaseInsertThreeColumns(column1, column2, column3);

            Log.d("pause194", "" + indexCounter);
            indexCounter++;

        }
    }
}

