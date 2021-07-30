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

            //int childViewCounter = llSizeLayout.getChildCount();
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

//            etBodyPart.setTag(tagId);
//            etRecord.setTag(tagId);
//            etUnit.setTag(tagId);
//            btDelete.setTag(tagId);

            //btDelete.setOnClickListener(new ButtonListener(SizeMemo.this));

            i = cursor.getColumnIndex("bodypart");
            strBodyPart = cursor.getString(i);

            i = cursor.getColumnIndex("records");
            strRecord = cursor.getString(i);

            i = cursor.getColumnIndex("unit");
            strUnit = cursor.getString(i);


            try {
                etBodyPart.setText(strBodyPart);
//                EditEventListener etListener = new EditEventListener(etBodyPart, SizeMemo.this);
//                etBodyPart.addTextChangedListener(etListener);
            } catch (NullPointerException e) {
                strBodyPart = "";
            }

            try {
                etRecord.setText(strRecord);
//                EditEventListener etListener2 = new EditEventListener(etRecord, SizeMemo.this);
//                etRecord.addTextChangedListener(etListener2);
            } catch (NullPointerException e) {
                strRecord = "";
            }

            try {
                etUnit.setText(strUnit);
//                EditEventListener etListener3 = new EditEventListener(etUnit, SizeMemo.this);
//                etUnit.addTextChangedListener(etListener3);
            } catch (NullPointerException e) {
                strUnit = "";
            }

        }

        if(llSizeLayout.getChildCount()!=0){
            LinearLayout firstView= (LinearLayout) llSizeLayout.getChildAt(0);
            firstView.setVisibility(View.GONE);
        }else {
        }

//        String sqlIndex = "SELECT memo FROM size  ";
//        cursor = db.rawQuery(sqlIndex, null);
//        cursor.moveToFirst();
//        int i = cursor.getColumnIndex("memo");
//        try {
//            indexCounter = Integer.valueOf(cursor.getString(i));
//        } catch (NumberFormatException e) {
//            indexCounter = 1;
//        } catch (CursorIndexOutOfBoundsException s) {
//            indexCounter = 1;
//        }
//        Log.d("maina", "" + indexCounter);
    }

//    public class ButtonListener extends LinearLayout implements View.OnClickListener {
//        public ButtonListener(Context context) {
//            super(context);
//        }
//
//        @Override
//        public void onClick(View v) {
//            tagId = (int) v.getTag();
//            View parentView = (View) v.getParent();
//            llSizeLayout.removeView(parentView);
//
//            _helper = new Databasehelper(SizeMemo.this);
//            SQLiteDatabase db = _helper.getWritableDatabase();
//            String sqlDelete = "DELETE FROM size WHERE _id = ?";
//            SQLiteStatement statement = db.compileStatement(sqlDelete);
//            statement.bindLong(1, tagId);
//            statement.executeUpdateDelete();
//        }
//    }


//    @Override
//    public void onBackPressed() {
//        finish();
//    }

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
//                int childViewCounter=llParentLayout.getChildCount();
                if(llSizeLayout.getChildCount()==0){
                    inflater = LayoutInflater.from(getApplicationContext());
                    llSizeInputform=(LinearLayout)inflater.inflate(R.layout.size_inputform,null);
                    //llCarNameInputform.setTag(tagId);
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

//                etBodyPart.setTag(indexCounter);
//                etRecord.setTag(indexCounter);
//                etUnit.setTag(indexCounter);
//                btDelete.setTag(indexCounter);
//
//                EditEventListener etListener = new EditEventListener(etBodyPart, SizeMemo.this);
//                etBodyPart.addTextChangedListener(etListener);
//                EditEventListener etListener2 = new EditEventListener(etRecord, SizeMemo.this);
//                etRecord.addTextChangedListener(etListener2);
//                EditEventListener etListener3 = new EditEventListener(etUnit, SizeMemo.this);
//                etUnit.addTextChangedListener(etListener3);
//
//                tagId = indexCounter;
//                String str = "";
//
//                _helper = new Databasehelper(SizeMemo.this);
//                SQLiteDatabase db = _helper.getWritableDatabase();
//
//                String sqlDelete = "DELETE FROM size WHERE _id = ?";
//                SQLiteStatement statement = db.compileStatement(sqlDelete);
//                statement.bindLong(1, tagId);
//                statement.executeUpdateDelete();
//
//                String sqlInsert =
//                        "INSERT INTO size" +
//                                "(_id,category,bodypart,records,unit) " +
//                                "VALUES(?,?,?,?,?)";
//                statement = db.compileStatement(sqlInsert);
//                statement.bindLong(1, tagId);
//                statement.bindString(2, _category);
//                statement.bindString(3, str);
//                statement.bindString(4, str);
//                statement.bindString(5, str);
//                statement.executeInsert();
//
//                indexCounter++;
//                String sqlCount = "UPDATE size SET memo = ? ";
//                statement = db.compileStatement(sqlCount);
//                statement.bindString(1, String.valueOf(indexCounter));
//                statement.executeUpdateDelete();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPause() {
        super.onPause();

//        DatabaseControl control4=new DatabaseControl(context,table);
//        control4.DatabaseAllDelete();

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

            Log.d("pause358", "" + indexCounter);
            indexCounter++;

        }
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


