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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MemoMemo extends AppCompatActivity {

    private Databasehelper _helper;
    private String _category = "memo";

    private int indexCounter=2;
    int tagId;
    String table="memo";
    Context context=MemoMemo.this;


    LayoutInflater inflater;
    LinearLayout llMemoLayout;
    LinearLayout llMemoInputform;
    LinearLayout llMemoFrame;
    LinearLayout llMemoTitle;

    EditText etMemoTitle;
    EditText etMemoContents;
    ImageButton btDelete;

    String strMemoTitle;
    String strMemoContents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_memo);

        Intent intent = getIntent();
        llMemoLayout = findViewById(R.id.ll_memo_layout);

//        llMemoLayout.removeAllViews();
//        DatabaseControl control4=new DatabaseControl(context,table);
//        control4.DatabaseAllDelete();

        _helper=new Databasehelper(getApplicationContext());
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlSelect="SELECT * FROM memo";
        Cursor cursor=db.rawQuery(sqlSelect,null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int i = cursor.getColumnIndex("_id");
            tagId = cursor.getInt(i);
            inflater = LayoutInflater.from(getApplicationContext());
            llMemoLayout = findViewById(R.id.ll_memo_layout);
            llMemoInputform=(LinearLayout)inflater.inflate(R.layout.memo_inputform,null);
            llMemoLayout.addView(llMemoInputform);

            llMemoFrame=llMemoInputform.findViewById(R.id.ll_memo_frame);
            llMemoTitle=llMemoFrame.findViewById(R.id.ll_memo_title);

            etMemoTitle=llMemoTitle.findViewById(R.id.et_memo_title);
            etMemoContents=llMemoFrame.findViewById(R.id.et_memo_contents);
            btDelete=llMemoTitle.findViewById(R.id.bt_delete);
            btDelete.setOnClickListener
                    (new DeleteButton(MemoMemo.this,llMemoLayout,llMemoInputform,table));

            i = cursor.getColumnIndex("memotitle");
            strMemoTitle = cursor.getString(i);

            i = cursor.getColumnIndex("memocontents");
            strMemoContents = cursor.getString(i);



            try {
                etMemoTitle.setText(strMemoTitle);
            } catch (NullPointerException e) {
                strMemoTitle = "";
            }

            try {
                etMemoContents.setText(strMemoContents);
            } catch (NullPointerException e) {
                strMemoContents = "";
            }

        }

        if(llMemoLayout.getChildCount()!=0){
            LinearLayout firstView= (LinearLayout) llMemoLayout.getChildAt(0);
            firstView.setVisibility(View.GONE);
        }else {
        }

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
                if(llMemoLayout.getChildCount()==0){
                    inflater = LayoutInflater.from(getApplicationContext());
                    llMemoLayout = findViewById(R.id.ll_memo_layout);
                    llMemoInputform=(LinearLayout)inflater.inflate(R.layout.memo_inputform,null);
                    llMemoLayout.addView(llMemoInputform);
                    llMemoInputform.setVisibility(View.GONE);

                    String str="";
                    DatabaseControl control = new DatabaseControl(context, table);
                    control.deleteDatabase(1);

                    String column1="memotitle";
                    String column2="memocontents";

                    DatabaseControl control2 = new DatabaseControl
                            (context, table,1, _category, str, str);
                    control2.insertDatabaseTwoColumns(column1, column2);
                }
                inflater = LayoutInflater.from(getApplicationContext());
                llMemoInputform=(LinearLayout)inflater.inflate(R.layout.memo_inputform,null);
                llMemoLayout.addView(llMemoInputform);

                llMemoFrame=llMemoInputform.findViewById(R.id.ll_memo_frame);
                llMemoTitle=llMemoFrame.findViewById(R.id.ll_memo_title);

                etMemoTitle=llMemoTitle.findViewById(R.id.et_memo_title);
                etMemoContents=llMemoFrame.findViewById(R.id.et_memo_contents);
                btDelete=llMemoTitle.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(MemoMemo.this,llMemoLayout,llMemoInputform,table));

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        indexCounter = 2;

        for (int i = 0; i < llMemoLayout.getChildCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) llMemoLayout.getChildAt(i);
            etMemoTitle = linearLayout.findViewById(R.id.et_memo_title);
            etMemoContents = linearLayout.findViewById(R.id.et_memo_contents);

            strMemoTitle = etMemoTitle.getText().toString();
            strMemoContents = etMemoContents.getText().toString();


            DatabaseControl control = new DatabaseControl(context, table);
            control.deleteDatabase(indexCounter);

            String column1="memotitle";
            String column2="memocontents";


            DatabaseControl control2=new DatabaseControl
                    (context,table,indexCounter,_category,strMemoTitle,strMemoContents);
            control2.insertDatabaseTwoColumns(column1,column2);

            indexCounter++;

        }
    }

}