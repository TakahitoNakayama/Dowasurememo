package com.websarva.wings.android.dowasurememo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SizeMemo extends AppCompatActivity {

    private DatabaseSQLite _helper;
    private String _category="size";
    int etId;
    String strInput="null";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_memo);

        Intent intent=getIntent();
        _helper=new DatabaseSQLite(SizeMemo.this);
        EditText etInput;

        EditText etHeight=findViewById(R.id.et_height);
        EditEventListener editEventListener=new EditEventListener(etHeight);
        etHeight.addTextChangedListener(editEventListener);

        EditText etWeight=findViewById(R.id.et_weight);
        editEventListener=new EditEventListener(etWeight);
        etWeight.addTextChangedListener(editEventListener);

        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlSelect="SELECT * FROM zibunmemo";
        Cursor cursor=db.rawQuery(sqlSelect,null);
        while (cursor.moveToNext()){
            int index =cursor.getColumnIndex("_id");
            etId=cursor.getInt(index);
            etInput=findViewById(etId);
            index=cursor.getColumnIndex("number");
            strInput=cursor.getString(index);

            try{
                etInput.setText(strInput);
                editEventListener=new EditEventListener(etInput);
                etInput.addTextChangedListener(editEventListener);
            }catch (NullPointerException e){
                etInput=etHeight;
            }
            //最初から記述されている入力欄はデータベースにデータがないからリスナーが登録されない
            //oncreate下に配置する必要あり
            //このtry catchだと一番最初に身長か体重のどちらかしか入力されなかった場合、
            // 正常に動かない可能性がある//
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.optionmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.option_add:
                LinearLayout addLayout=findViewById(R.id.addlayout);
                AddInputForm adin=new AddInputForm(SizeMemo.this);
                adin.createinputform();
                addLayout.addView(AddInputForm.linearLayout);




        }







        return super.onOptionsItemSelected(item);
    }
}

