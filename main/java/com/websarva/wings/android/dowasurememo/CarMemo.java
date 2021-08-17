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

        //データベースからデータを取り出して、viewを生成する処理
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
                //車の車種を入力するviewを生成
                inflater = LayoutInflater.from(getApplicationContext());
                llCarLayout=findViewById(R.id.ll_car_layout);
                llCarNameInputform=(LinearLayout)inflater.inflate(R.layout.car_name_inputform,null);
                llCarLayout.addView(llCarNameInputform);

                //viewの削除ボタンにリスナーをセット
                btDelete=llCarNameInputform.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(CarMemo.this,llCarLayout,llCarNameInputform,table));

                //viewの追加ボタンにリスナーをセット
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
                //車のナンバーなどの詳細を入力するviewを生成
                inflater = LayoutInflater.from(getApplicationContext());
                llCarLayout=findViewById(R.id.ll_car_layout);
                llCarDetailInputform= (LinearLayout) inflater.inflate(R.layout.car_detail_inputform,null);
                llCarLayout.addView(llCarDetailInputform);

                //viewの削除ボタンにリスナーをセット
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

        /*なぜか最初にviewを追加すると、１番目に追加したviewだけ消えてしまう事象が発生したため
        コード上であらかじめ追加しておいた1番目のviewを非表示にする処理*/
        if(llCarLayout.getChildCount()!=0){
            LinearLayout firstView= (LinearLayout) llCarLayout.getChildAt(0);
            firstView.setVisibility(View.GONE);
        }else {
        }

    }

    //車の詳細を入力するviewを追加するための処理
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

    //オプションメニューの実装
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.optionmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    //車の車種を入力するviewを追加するための処理
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.option_add:
                /*なぜか最初にviewを追加すると、１番目に追加したviewだけ消えてしまう事象が発生したため
                コード上であらかじめ1番目のviewを追加して非表示にする処理*/
                if(llCarLayout.getChildCount()==0){
                    inflater = LayoutInflater.from(getApplicationContext());
                    llCarNameInputform=(LinearLayout)inflater.inflate(R.layout.car_name_inputform,null);
                    llCarLayout.addView(llCarNameInputform);
                    llCarNameInputform.setVisibility(View.GONE);

                    String str="";
                    DatabaseControl control=new DatabaseControl(context,table);
                    control.deleteDatabase(1);

                    String column1="carname";
                    String column2="inputform";
                    String inputform="name";

                    DatabaseControl control2=new DatabaseControl
                            (context,table,1,_category,str,inputform);
                    control2.insertDatabaseTwoColumns(column1,column2);
                }

                //すでにレイアウト上に子ビューがある場合のviewの追加処理
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

        return super.onOptionsItemSelected(item);
    }

    //レイアウト上のviewをまとめてデータベースに保存
    @Override
    protected void onPause() {
        super.onPause();

        indexCounter = 2;

        for(int i=0;i<llCarLayout.getChildCount();i++){
            LinearLayout linearLayout= (LinearLayout) llCarLayout.getChildAt(i);
            switch (linearLayout.getId()){
                case R.id.ll_car_name_inputform:
                    etCarName=linearLayout.findViewById(R.id.et_car_name);
                    strCarName=etCarName.getText().toString();

                    DatabaseControl control=new DatabaseControl(context,table);
                    control.deleteDatabase(indexCounter);

                    String column1="carname";
                    String column2="inputform";
                    String inputform="name";

                    DatabaseControl control2=new DatabaseControl
                            (context,table,indexCounter,_category,strCarName,inputform);
                    control2.insertDatabaseTwoColumns(column1,column2);

                    indexCounter++;

                    break;

                case R.id.ll_car_detail_inputform:
                    etCarMemoTitle=linearLayout.findViewById(R.id.et_car_memo_title);
                    etCarMemoContents=linearLayout.findViewById(R.id.et_car_memo_contents);
                    strCarMemoTitle=etCarMemoTitle.getText().toString();
                    strCarMemoContents=etCarMemoContents.getText().toString();

                    DatabaseControl control1=new DatabaseControl(context,table);
                    control1.deleteDatabase(indexCounter);

                    String memocolumn1="carmemotitle";
                    String memocolumn2="carmemocontents";
                    String memocolumn3="inputform";
                    String memoinputform="detail";

                    DatabaseControl control3=new DatabaseControl
                            (context,table,indexCounter,_category,strCarMemoTitle,strCarMemoContents,memoinputform);
                    control3.insertDatabaseThreeColumns(memocolumn1,memocolumn2,memocolumn3);

                    Log.d("pause381",""+indexCounter);
                    indexCounter++;


                    break;
            }
        }
    }
}