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
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SizeMemo extends AppCompatActivity {

    private DatabaseSQLite _helper;
    private String _category = "size";
    int lvId;
    String strInput = "null";
    List<Map<String, String>> sizeList;
    Map<String, String> sizeMap;
    String[] from = {"bodypart", "record", "unit"};
    int[] to = {R.id.et_bodypart, R.id.et_record, R.id.et_unit};

    String strBodyPart;
    String strRecord;
    String strUnit;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_memo);

        Intent intent = getIntent();

        sizeList = new ArrayList<>();
        sizeMap = new HashMap<>();
        sizeMap.put("bodypart", "身長");
        sizeMap.put("record", "");
        sizeMap.put("unit", "cm");
        sizeList.add(sizeMap);

        sizeMap = new HashMap<>();
        sizeMap.put("bodypart","体重");
        sizeMap.put("record", "");
        sizeMap.put("unit", "kg");
        sizeList.add(sizeMap);


        ListView lvSizeList = findViewById(R.id.lv_size_list);
        SimpleAdapter simpleAdapter =
                new SimpleAdapter
                        (SizeMemo.this, sizeList, R.layout.size_input, from, to);
        lvSizeList.setAdapter(simpleAdapter);



//        EditText etBodyPart = null;
//        etBodyPart.setId(R.id.et_bodypart);
//        EditText etRecord=null;
//        etRecord.setId(R.id.et_record);
//        EditText etUnit=null;
//        etUnit.setId(R.id.et_unit);

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        EditText etBodyPart = (EditText) inflater.inflate(R.layout.size_input,lvSizeList, false);
        EditText etRecord = (EditText) inflater.inflate(R.layout.size_input,lvSizeList, false);
        EditText etUnit = (EditText) inflater.inflate(R.layout.size_input,lvSizeList, false);

        etBodyPart.setId(R.id.et_bodypart);
        etRecord.setId(R.id.et_record);
        etUnit.setId(R.id.et_unit);

        EditEventListener etListener=new EditEventListener();
        etBodyPart.addTextChangedListener(etListener);
        etRecord.addTextChangedListener(etListener);
        etUnit.addTextChangedListener(etListener);
    }


    private class EditEventListener implements TextWatcher{

        private EditText editText;



        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            lvId=sizeList.indexOf(sizeMap);

            switch (editText.getId()){
                case R.id.et_bodypart:
                    strBodyPart=s.toString();
                    break;
                case  R.id.et_record:
                    strRecord=s.toString();
                    break;
                case R.id.et_unit:
                    strUnit=s.toString();
                    break;

            }
            Log.d("main",""+strBodyPart);

//

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
        switch (item.getItemId()) {
            case R.id.option_add:
                sizeMap = new HashMap<>();
                sizeMap.put("bodypart", "");
                sizeMap.put("record", "");
                sizeMap.put("unit", "");
                sizeList.add(sizeMap);

                ListView lvSizeList = findViewById(R.id.lv_size_list);
                SimpleAdapter simpleAdapter =
                        new SimpleAdapter
                                (SizeMemo.this, sizeList, R.layout.size_input, from, to);
                lvSizeList.setAdapter(simpleAdapter);
        }
        return super.onOptionsItemSelected(item);
    }


}



//etId=editText.getId();
//            EditText etInput;
//            etInput=findViewById(etId);
//
//            try{
//                strInput=etInput.getText().toString();
//            }catch (NumberFormatException e){
//                strInput="null";
//            }
//
//            SQLiteDatabase db=_helper.getWritableDatabase();
//            String sqlDelete="DELETE FROM zibunmemo WHERE _id = ?";
//            SQLiteStatement statement=db.compileStatement(sqlDelete);
//            statement.bindLong(1,etId);
//            statement.executeUpdateDelete();
//
//            String sqlInsert="INSERT INTO zibunmemo(_id,category,number) VALUES(?,?,?)";
//            statement=db.compileStatement(sqlInsert);
//            statement.bindLong(1,etId);
//            statement.bindString(2,_category);
//            statement.bindString(3,strInput);
//            statement.executeInsert();




//        _helper=new DatabaseSQLite(SizeMemo.this);
//        EditText etInput;
//
//        EditText etHeight=findViewById(R.id.et_height);
//        EditEventListener editEventListener=new EditEventListener(etHeight);
//        etHeight.addTextChangedListener(editEventListener);
//
//        EditText etWeight=findViewById(R.id.et_weight);
//        editEventListener=new EditEventListener(etWeight);
//        etWeight.addTextChangedListener(editEventListener);
//
//        SQLiteDatabase db=_helper.getWritableDatabase();
//        String sqlSelect="SELECT * FROM zibunmemo";
//        Cursor cursor=db.rawQuery(sqlSelect,null);
//        while (cursor.moveToNext()){
//            int index =cursor.getColumnIndex("_id");
//            etId=cursor.getInt(index);
//            etInput=findViewById(etId);
//            index=cursor.getColumnIndex("number");
//            strInput=cursor.getString(index);
//
//            try{
//                etInput.setText(strInput);
//                editEventListener=new EditEventListener(etInput);
//                etInput.addTextChangedListener(editEventListener);
//            }catch (NullPointerException e){
//                etInput=etHeight;
//            }
//            //最初から記述されている入力欄はデータベースにデータがないからリスナーが登録されない
//            //oncreate下に配置する必要あり
//            //このtry catchだと一番最初に身長か体重のどちらかしか入力されなかった場合、
//            // 正常に動かない可能性がある//
//        }
//    }
//
//    private class EditEventListener implements TextWatcher{
//
//        private EditText editText;
//        public EditEventListener(EditText editText){
//           this.editText=editText;
//        }
//
//
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//
//            etId=editText.getId();
//            EditText etInput;
//            etInput=findViewById(etId);
//
//            try{
//                strInput=etInput.getText().toString();
//            }catch (NumberFormatException e){
//                strInput="null";
//            }
//
//            SQLiteDatabase db=_helper.getWritableDatabase();
//            String sqlDelete="DELETE FROM zibunmemo WHERE _id = ?";
//            SQLiteStatement statement=db.compileStatement(sqlDelete);
//            statement.bindLong(1,etId);
//            statement.executeUpdateDelete();
//
//            String sqlInsert="INSERT INTO zibunmemo(_id,category,number) VALUES(?,?,?)";
//            statement=db.compileStatement(sqlInsert);
//            statement.bindLong(1,etId);
//            statement.bindString(2,_category);
//            statement.bindString(3,strInput);
//            statement.executeInsert();
//
//        }
//    }
//
//    @Override
//    public void onBackPressed(){
//
//        finish();
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater=getMenuInflater();
//        inflater.inflate(R.menu.optionmenu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.Q)
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.option_add:
//                LinearLayout addLayout=findViewById(R.id.addlayout);
//                AddInputForm adin=new AddInputForm(SizeMemo.this);
//                adin.createinputform();
//                addLayout.addView(AddInputForm.linearLayout);
//
//        }
//        return super.onOptionsItemSelected(item);
//    }



