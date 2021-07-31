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

public class CarMemo extends AppCompatActivity {

    private Databasehelper _helper;
    private String _category = "car";

    private int indexCounter=2;
    int tagId;
    String table="car";
    Context context=CarMemo.this;

    LayoutInflater inflater;
    LinearLayout linearLayout;
    LinearLayout llCarLayout;
    LinearLayout llCarNameInputform;
    LinearLayout llCarDetailInputform;

    EditText etCarName;
    EditText etCarMemoTitle;
    EditText etCarMemoContents;
    ImageButton btDelete;
    ImageButton btCarDetailAdd;

    String strCarName;
    String strCarMemoTitle;
    String strCarMemoContents;

    String name="name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_memo);

        Intent intent = getIntent();
        llCarLayout=findViewById(R.id.ll_car_layout);

//        llCarLayout.removeAllViews();
//        DatabaseControl control4=new DatabaseControl(context,table);
//        control4.DatabaseAllDelete();

        _helper=new Databasehelper(getApplicationContext());
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlSelect="SELECT * FROM car";
        Cursor cursor=db.rawQuery(sqlSelect,null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {

            int i = cursor.getColumnIndex("_id");
            tagId = cursor.getInt(i);
            i=cursor.getColumnIndex("inputform");
            String st=cursor.getString(i);
            if(st.equals(name)){
                inflater = LayoutInflater.from(getApplicationContext());
                llCarNameInputform=(LinearLayout)inflater.inflate(R.layout.car_name_inputform,null);
                llCarLayout.addView(llCarNameInputform);

                btDelete=llCarNameInputform.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(CarMemo.this,llCarLayout,llCarNameInputform,table));

                btCarDetailAdd=llCarNameInputform.findViewById(R.id.bt_cardetail_add);
                btCarDetailAdd.setOnClickListener(new AddCarDetail(CarMemo.this));

                etCarName=llCarNameInputform.findViewById(R.id.et_car_name);

                i = cursor.getColumnIndex("carname");
                strCarName = cursor.getString(i);

                try {
                    etCarName.setText(strCarName);
                } catch (NullPointerException e) {
                    strCarName = "";
                }

            }
            else{
                inflater = LayoutInflater.from(getApplicationContext());
                llCarDetailInputform= (LinearLayout) inflater.inflate(R.layout.car_detail_inputform,null);
                llCarLayout.addView(llCarDetailInputform);

                btDelete=llCarDetailInputform.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(CarMemo.this,llCarLayout,llCarDetailInputform,table));


                etCarMemoTitle=llCarDetailInputform.findViewById(R.id.et_car_memo_title);
                etCarMemoContents=llCarDetailInputform.findViewById(R.id.et_car_memo_contents);

                i = cursor.getColumnIndex("carmemotitle");
                strCarMemoTitle = cursor.getString(i);

                i = cursor.getColumnIndex("carmemocontents");
                strCarMemoContents = cursor.getString(i);

                try {
                    etCarMemoTitle.setText(strCarMemoTitle);
                } catch (NullPointerException e) {
                    strCarMemoTitle = "";
                }

                try {
                    etCarMemoContents.setText(strCarMemoContents);
                } catch (NullPointerException e) {
                    strCarMemoContents = "";
                }
            }
        }

        if(llCarLayout.getChildCount()!=0){
            LinearLayout firstView= (LinearLayout) llCarLayout.getChildAt(0);
            firstView.setVisibility(View.GONE);
        }else {
        }

    }


    public class AddCarDetail extends LinearLayout implements View.OnClickListener{
        public AddCarDetail(Context context) {
            super(context);
        }
        @Override
        public void onClick(View v) {
            llCarDetailInputform= (LinearLayout) inflater.inflate(R.layout.car_detail_inputform,null);
            linearLayout= (LinearLayout) v.getParent();
            for(int i=0;i<llCarLayout.getChildCount();i++){
                LinearLayout sameLinearLayout= (LinearLayout) llCarLayout.getChildAt(i);
                if(linearLayout==sameLinearLayout){
                    llCarLayout.addView(llCarDetailInputform,i+1);

                    btDelete=llCarDetailInputform.findViewById(R.id.bt_delete);
                    btDelete.setOnClickListener
                            (new DeleteButton(CarMemo.this,llCarLayout,llCarDetailInputform,table));

                    etCarMemoTitle=llCarDetailInputform.findViewById(R.id.et_car_memo_title);
                    etCarMemoContents=llCarDetailInputform.findViewById(R.id.et_car_memo_contents);

                }
            }
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
                if(llCarLayout.getChildCount()==0){
                    inflater = LayoutInflater.from(getApplicationContext());
                    llCarNameInputform=(LinearLayout)inflater.inflate(R.layout.car_name_inputform,null);
                    llCarLayout.addView(llCarNameInputform);
                    llCarNameInputform.setVisibility(View.GONE);

                    String str="";
                    DatabaseControl control=new DatabaseControl(context,table);
                    control.DatabaseDelete(1);

                    String column1="carname";
                    String column2="inputform";
                    String inputform="name";

                    DatabaseControl control2=new DatabaseControl
                            (context,table,1,_category,str,inputform);
                    control2.DatabaseInsertTwoColumns(column1,column2);
                }

                inflater = LayoutInflater.from(getApplicationContext());
                llCarNameInputform=(LinearLayout)inflater.inflate(R.layout.car_name_inputform,null);
                llCarLayout.addView(llCarNameInputform);

                btCarDetailAdd=llCarNameInputform.findViewById(R.id.bt_cardetail_add);
                btCarDetailAdd.setOnClickListener(new AddCarDetail(CarMemo.this));

                btDelete=llCarNameInputform.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(CarMemo.this,llCarLayout,llCarNameInputform,table));

                etCarName=llCarNameInputform.findViewById(R.id.et_car_name);
        }

        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        DatabaseControl control4=new DatabaseControl(context,table);
        control4.DatabaseAllDelete();

        for(int i=0;i<llCarLayout.getChildCount();i++){
            LinearLayout linearLayout= (LinearLayout) llCarLayout.getChildAt(i);
            switch (linearLayout.getId()){
                case R.id.ll_car_name_inputform:
                    etCarName=linearLayout.findViewById(R.id.et_car_name);
                    strCarName=etCarName.getText().toString();

                    DatabaseControl control=new DatabaseControl(context,table);
                    control.DatabaseDelete(indexCounter);

                    String column1="carname";
                    String column2="inputform";
                    String inputform="name";

                    DatabaseControl control2=new DatabaseControl
                            (context,table,indexCounter,_category,strCarName,inputform);
                    control2.DatabaseInsertTwoColumns(column1,column2);

                    indexCounter++;

                    break;

                case R.id.ll_car_detail_inputform:
                    etCarMemoTitle=linearLayout.findViewById(R.id.et_car_memo_title);
                    etCarMemoContents=linearLayout.findViewById(R.id.et_car_memo_contents);
                    strCarMemoTitle=etCarMemoTitle.getText().toString();
                    strCarMemoContents=etCarMemoContents.getText().toString();

                    DatabaseControl control1=new DatabaseControl(context,table);
                    control1.DatabaseDelete(indexCounter);

                    String memocolumn1="carmemotitle";
                    String memocolumn2="carmemocontents";
                    String memocolumn3="inputform";
                    String memoinputform="detail";

                    DatabaseControl control3=new DatabaseControl
                            (context,table,indexCounter,_category,strCarMemoTitle,strCarMemoContents,memoinputform);
                    control3.DatabaseInsertThreeColumns(memocolumn1,memocolumn2,memocolumn3);

                    Log.d("pause381",""+indexCounter);
                    indexCounter++;


                    break;
            }
        }
    }
}