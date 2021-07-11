package com.websarva.wings.android.dowasurememo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

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

    EditText et;
    EditText etBodyPart;
    EditText etRecord;
    EditText etUnit;

    LinearLayout llLayout;
    LayoutInflater inflater;
    LinearLayout inputform;

    int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_memo);

        Intent intent = getIntent();

        llLayout=findViewById(R.id.ll_layout);
        inflater = LayoutInflater.from(getApplicationContext());
        inputform=(LinearLayout)inflater.inflate(R.layout.et_bodypart,null);
        llLayout.addView(inputform,0);

        ImageView tag=inputform.findViewById(R.id.tag);
        tag.setColorFilter(Color.rgb(127,255,212));
        etBodyPart=inputform.findViewById(R.id.et_bodypart);
        etRecord=inputform.findViewById(R.id.et_record);
        etUnit=inputform.findViewById(R.id.et_unit);

        etBodyPart.setText(" ");
        etBodyPart.setTag(String.valueOf(index));
        etRecord.setTag(Integer.valueOf(index));
        etUnit.setTag(String.valueOf(index));

        EditEventListener etListener=new EditEventListener(etBodyPart);
        etBodyPart.addTextChangedListener(etListener);
        etRecord.addTextChangedListener(etListener);
        etUnit.addTextChangedListener(etListener);
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


                LinearLayout llLayout=findViewById(R.id.ll_layout);
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                LinearLayout inputform=(LinearLayout)inflater.inflate(R.layout.et_bodypart,null);
                llLayout.addView(inputform,0);


                ImageView tag=inputform.findViewById(R.id.tag);
                tag.setColorFilter(Color.rgb(127,255,212));
                etBodyPart=inputform.findViewById(R.id.et_bodypart);
                etRecord=inputform.findViewById(R.id.et_record);
                etUnit=inputform.findViewById(R.id.et_unit);

                etBodyPart.setText(" ");
                etBodyPart.setTag(String.valueOf(index));
                etRecord.setTag(String.valueOf(index));
                etUnit.setTag(String.valueOf(index));

                EditEventListener etListener=new EditEventListener(etBodyPart);
                etBodyPart.addTextChangedListener(etListener);
                etRecord.addTextChangedListener(etListener);
                etUnit.addTextChangedListener(etListener);


                index++;

        }
        return super.onOptionsItemSelected(item);
    }



}

//                ArrayList<EditText> etBodyPartList=new ArrayList<>();
//                etBodyPartList.add(etBodyPart);



//                EditText editText=findViewById(R.id.et_bodypart);
//                editText.setId(index);
//                editText.addTextChangedListener(etListener);
//                for(EditText e : etBodyPartList){
//                    e.addTextChangedListener(etListener);
//                }

//                Log.d("main",""+etBodyPart.getId());



//sizeList = new ArrayList<>();
//        sizeMap = new HashMap<>();
//        sizeMap.put("bodypart", "身長");
//        sizeMap.put("record", "");
//        sizeMap.put("unit", "cm");
//        sizeList.add(sizeMap);
//
//        sizeMap = new HashMap<>();
//        sizeMap.put("bodypart","体重");
//        sizeMap.put("record", "");
//        sizeMap.put("unit", "kg");
//        sizeList.add(sizeMap);
//
//
//        ListView lvSizeList = findViewById(R.id.lv_size_list);
//        SimpleAdapter simpleAdapter =
//                new SimpleAdapter
//                        (SizeMemo.this, sizeList, R.layout.size_input, from, to);
//        lvSizeList.setAdapter(simpleAdapter);
//
////        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
////        LinearLayout ll= (LinearLayout) inflater.inflate(R.layout.size_input,lvSizeList,false);
//////        ll.removeAllViews();
////        EditText etBodyPart= (EditText) ll.getChildAt(1);
////                etBodyPart.setId(R.id.et_bodypart);
////        EditText etRecord = ll.findViewById(R.id.et_record);
////        EditText etUnit = ll.findViewById(R.id.et_unit);
////        ll.addView(etBodyPart);
////        ll.addView(etRecord);
////        ll.addView(etUnit);
//
//
////        EditEventListener etListener=new EditEventListener(etBodyPart,etRecord,etUnit);
////        etBodyPart.addTextChangedListener(etListener);
////        etRecord.addTextChangedListener(etListener);
////        etUnit.addTextChangedListener(etListener);
//
////        et=findViewById(R.id.et);
////        et.addTextChangedListener(etListener);
//
//        ListListener listListener=new ListListener();
//        lvSizeList.setOnItemClickListener(listListener);





//                sizeMap = new HashMap<>();
//                sizeMap.put("bodypart", "");
//                sizeMap.put("record", "");
//                sizeMap.put("unit", "");
//                sizeList.add(sizeMap);
//
//                ListView lvSizeList = findViewById(R.id.lv_size_list);
//                SimpleAdapter simpleAdapter =
//                        new SimpleAdapter
//                                (SizeMemo.this, sizeList, R.layout.size_input, from, to);
//                lvSizeList.setAdapter(simpleAdapter);
//
//                et.setEnabled(true);
//                et.setVisibility(View.VISIBLE);



//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        Log.d("main","ああああ");
//        ViewSave viewSave=new ViewSave(SizeMemo.this);
//        viewSave.onSaveInstanceState();
//
//    }
//
//
//    @Override
//    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        Log.d("main","ああああ");
//        ViewSave viewSave=new ViewSave(SizeMemo.this);
//        viewSave.onRestoreInstanceState(savedInstanceState);
//    }



//    class ListListener implements AdapterView.OnItemClickListener{
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            int i=position;
//            Toast.makeText(SizeMemo.this,""+i,Toast.LENGTH_SHORT).show();
//            Log.d("main",""+i);
//        }
//    }


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



