package com.websarva.wings.android.dowasurememo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class SizeMemo extends AppCompatActivity {

    private DatabaseSQLite _helper;
    private String _category="size";
    int etId;
    String strInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_memo);

        Intent intent=getIntent();
        _helper=new DatabaseSQLite(SizeMemo.this);
        EditText etInput;

//        EditText etHeight=findViewById(R.id.et_height);
//        EditEventListener editEventListener=new EditEventListener(etHeight);
//        etHeight.addTextChangedListener(editEventListener);
//
//        EditText etWeight=findViewById(R.id.et_weight);
//        editEventListener=new EditEventListener(etWeight);
//        etWeight.addTextChangedListener(editEventListener);

        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlSelect="SELECT * FROM zibunmemo";
        Cursor cursor=db.rawQuery(sqlSelect,null);
        while (cursor.moveToNext()){
            int index =cursor.getColumnIndex("_id");
            etId=cursor.getInt(index);
            etInput=findViewById(etId);
            index=cursor.getColumnIndex("number");
            strInput=cursor.getString(index);

            etInput.setText(strInput);
            EditEventListener editEventListener=new EditEventListener(etInput);
            etInput.addTextChangedListener(editEventListener);
            //最初から記述されている入力欄はデータベースにデータがないからリスナーが登録されない
            //oncreate下に配置する必要あり
        }
    }

    private class EditEventListener implements TextWatcher{

        private EditText editText;
        public EditEventListener(EditText editText){
           this.editText=editText;
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            etId=editText.getId();
            EditText etInput;
            etInput=findViewById(etId);

            try{
                strInput=etInput.getText().toString();
            }catch (NumberFormatException e){
                strInput="null";
            }

            SQLiteDatabase db=_helper.getWritableDatabase();
            String sqlDelete="DELETE FROM zibunmemo WHERE _id = ?";
            SQLiteStatement statement=db.compileStatement(sqlDelete);
            statement.bindLong(1,etId);
            statement.executeUpdateDelete();

            String sqlInsert="INSERT INTO zibunmemo(_id,category,number) VALUES(?,?,?)";
            statement=db.compileStatement(sqlInsert);
            statement.bindLong(1,etId);
            statement.bindString(2,_category);
            statement.bindString(3,strInput);
            statement.executeInsert();

        }
    }

    @Override
    public void onBackPressed(){
        finish();
    }

}

