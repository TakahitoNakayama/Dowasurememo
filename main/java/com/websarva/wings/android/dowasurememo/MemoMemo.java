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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MemoMemo extends AppCompatActivity {

    private Databasehelper _helper;
    private String _category = "memo";

    private int indexCounter=1;
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


        _helper=new Databasehelper(getApplicationContext());
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlSelect="SELECT * FROM memo";
        Cursor cursor=db.rawQuery(sqlSelect,null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int i = cursor.getColumnIndex("_id");
            tagId = cursor.getInt(i);
            llMemoLayout = findViewById(R.id.ll_memo_layout);
            inflater = LayoutInflater.from(getApplicationContext());
            llMemoInputform=(LinearLayout)inflater.inflate(R.layout.memo_inputform,null);
            llMemoLayout.addView(llMemoInputform);

            llMemoTitle=llMemoInputform.findViewById(R.id.ll_memo_title);

            etMemoTitle=llMemoTitle.findViewById(R.id.et_memo_title);
            etMemoContents=llMemoFrame.findViewById(R.id.et_memo_contents);
            btDelete=llMemoTitle.findViewById(R.id.bt_delete);
            btDelete.setOnClickListener
                    (new DeleteButton(MemoMemo.this,llMemoLayout,llMemoInputform,table));

            etMemoTitle.setTag(tagId);
            etMemoContents.setTag(tagId);
            btDelete.setTag(tagId);

            i = cursor.getColumnIndex("memotitle");
            strMemoTitle = cursor.getString(i);

            i = cursor.getColumnIndex("memocontents");
            strMemoContents = cursor.getString(i);



            try {
                etMemoTitle.setText(strMemoTitle);
                EditEventListener etListener=new EditEventListener(etMemoTitle,MemoMemo.this);
                etMemoTitle.addTextChangedListener(etListener);
            } catch (NullPointerException e) {
                strMemoTitle = "";
            }

            try {
                etMemoContents.setText(strMemoContents);
                EditEventListener etListener2=new EditEventListener(etMemoContents,MemoMemo.this);
                etMemoContents.addTextChangedListener(etListener2);
            } catch (NullPointerException e) {
                strMemoContents = "";
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
                llMemoInputform=(LinearLayout)inflater.inflate(R.layout.memo_inputform,null);
                llMemoLayout.addView(llMemoInputform);

                llMemoFrame=llMemoInputform.findViewById(R.id.ll_memo_frame);
                llMemoTitle=llMemoFrame.findViewById(R.id.ll_memo_title);

                etMemoTitle=llMemoTitle.findViewById(R.id.et_memo_title);
                etMemoContents=llMemoFrame.findViewById(R.id.et_memo_contents);
                btDelete=llMemoTitle.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(MemoMemo.this,llMemoLayout,llMemoInputform,table));

                etMemoTitle.setTag(indexCounter);
                etMemoContents.setTag(indexCounter);
                btDelete.setTag(indexCounter);

                EditEventListener etListener=new EditEventListener(etMemoTitle,MemoMemo.this);
                etMemoTitle.addTextChangedListener(etListener);
                EditEventListener etListener2=new EditEventListener(etMemoContents,MemoMemo.this);
                etMemoContents.addTextChangedListener(etListener2);

                tagId=indexCounter;
                String str="";

                DatabaseControl control=new DatabaseControl(context,table);
                control.DatabaseDelete(tagId);

                String column1="memotitle";
                String column2="memocontents";


                DatabaseControl control2=new DatabaseControl
                        (context,table,tagId,_category,str);
                control2.DatabaseInsert(column1,column2);

                indexCounter++;
                control.IndexCounterUpdate(indexCounter);

        }
        return super.onOptionsItemSelected(item);
    }


}