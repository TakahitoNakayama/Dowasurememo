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
    int intHeight=0;
    int etId=0;
    String strHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_memo);
        EditText etHeight=findViewById(R.id.et_height);
        Intent intent=getIntent();

        _helper=new DatabaseSQLite(SizeMemo.this);

        EditEventListener editEventListener=new EditEventListener();
       etHeight.addTextChangedListener(editEventListener);

        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlSelect="SELECT * FROM zibunmemo";
        Cursor cursor=db.rawQuery(sqlSelect,null);
        while (cursor.moveToNext()){
            int index=cursor.getColumnIndex("number");
            strHeight=cursor.getString(index);
            index =cursor.getColumnIndex("_id");
            etId=cursor.getInt(index);

            etHeight.setText(strHeight);

        }




    }

    private class EditEventListener implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            EditText etHeight=findViewById(R.id.et_height);

            try{
            strHeight=etHeight.getText().toString();
//            intHeight=Integer.parseInt(strHeight);
            }catch (NumberFormatException e){
                strHeight="null";
//                intHeight=0;
            }

            etId=etHeight.getId();

            SQLiteDatabase db=_helper.getWritableDatabase();
            String sqlDelete="DELETE FROM zibunmemo WHERE _id = ?";
            SQLiteStatement statement=db.compileStatement(sqlDelete);
            statement.bindLong(1,etId);
            statement.executeUpdateDelete();


            String sqlInsert="INSERT INTO zibunmemo(_id,category,number) VALUES(?,?,?)";
            statement=db.compileStatement(sqlInsert);
            statement.bindLong(1,etId);
            statement.bindString(2,_category);
            statement.bindString(3,strHeight);
            statement.executeInsert();

            Log.d("main",""+intHeight);


        }




    }

    @Override
    public void onBackPressed(){
        finish();
    }

}

