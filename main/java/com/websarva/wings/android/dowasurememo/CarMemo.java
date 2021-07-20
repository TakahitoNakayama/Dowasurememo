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

    private int indexCounter=1;
    int tagId;
    String table="car";
    Context context=CarMemo.this;

    LayoutInflater inflater;
    LinearLayout linearLayout;
    LinearLayout llCarLayout;
    LinearLayout llCarInputform;
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
    String detail="detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_memo);

        Intent intent = getIntent();

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
                llCarLayout=findViewById(R.id.ll_car_layout);
                inflater = LayoutInflater.from(getApplicationContext());
                llCarNameInputform=(LinearLayout)inflater.inflate(R.layout.car_name_inputform,null);
                llCarLayout.addView(llCarNameInputform);

                btDelete=llCarNameInputform.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(CarMemo.this,llCarLayout,llCarNameInputform,table));

                btCarDetailAdd=llCarNameInputform.findViewById(R.id.bt_cardetail_add);
                btCarDetailAdd.setOnClickListener(new AddCarDetail(CarMemo.this));

                etCarName=llCarNameInputform.findViewById(R.id.et_car_name);

                etCarName.setTag(tagId);
                btDelete.setTag(tagId);

                i = cursor.getColumnIndex("carname");
                strCarName = cursor.getString(i);

                try {
                    etCarName.setText(strCarName);
                    EditEventListener etListener=new EditEventListener(etCarName,CarMemo.this);
                    etCarName.addTextChangedListener(etListener);
                } catch (NullPointerException e) {
                    strCarName = "";
                }

            }
            else{
                llCarLayout=findViewById(R.id.ll_car_layout);
                inflater = LayoutInflater.from(getApplicationContext());
                llCarDetailInputform= (LinearLayout) inflater.inflate(R.layout.car_detail_inputform,null);
                llCarLayout.addView(llCarDetailInputform);

                btDelete=llCarDetailInputform.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(CarMemo.this,llCarLayout,llCarDetailInputform,table));


                etCarMemoTitle=llCarDetailInputform.findViewById(R.id.et_car_memo_title);
                etCarMemoContents=llCarDetailInputform.findViewById(R.id.et_car_memo_contents);

                etCarMemoTitle.setTag(tagId);
                etCarMemoContents.setTag(tagId);
                btDelete.setTag(tagId);

                i = cursor.getColumnIndex("carmemotitle");
                strCarMemoTitle = cursor.getString(i);

                i = cursor.getColumnIndex("carmemocontents");
                strCarMemoContents = cursor.getString(i);

                try {
                    etCarMemoTitle.setText(strCarMemoTitle);
                    EditEventListener etListener=new EditEventListener(etCarMemoTitle,CarMemo.this);
                    etCarMemoTitle.addTextChangedListener(etListener);
                } catch (NullPointerException e) {
                    strCarMemoTitle = "";
                }

                try {
                    etCarMemoContents.setText(strCarMemoContents);
                    EditEventListener etListener2=new EditEventListener(etCarMemoContents,CarMemo.this);
                    etCarMemoContents.addTextChangedListener(etListener2);
                } catch (NullPointerException e) {
                    strCarMemoContents = "";
                }
            }
        }

        DatabaseControl control=new DatabaseControl(context,table);
        indexCounter=control.GetIndexCounter();
        Log.d("main",""+indexCounter);

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

                    etCarMemoTitle.setTag(indexCounter);
                    etCarMemoContents.setTag(indexCounter);
                    btDelete.setTag(indexCounter);


                    EditEventListener etListener=new EditEventListener(etCarMemoTitle,CarMemo.this);
                    etCarMemoTitle.addTextChangedListener(etListener);
                    EditEventListener etListener2=new EditEventListener(etCarMemoContents,CarMemo.this);
                    etCarMemoContents.addTextChangedListener(etListener2);

                    tagId=indexCounter;
                    String str="";

                    DatabaseControl control=new DatabaseControl(context,table);
                    control.DatabaseDelete(tagId);

                    String column1="carmemotitle";
                    String column2="carmemocontents";
                    String column3="inputform";
                    String inputform="detail";

                    DatabaseControl control2=new DatabaseControl
                            (context,table,tagId,_category,str,inputform);
                    control2.DatabaseInsertCar(column1,column2,column3);

                    indexCounter++;
                    control.IndexCounterUpdate(indexCounter);

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
                inflater = LayoutInflater.from(getApplicationContext());
                llCarNameInputform=(LinearLayout)inflater.inflate(R.layout.car_name_inputform,null);
                llCarNameInputform.setTag(tagId);
                llCarLayout.addView(llCarNameInputform);

                btCarDetailAdd=llCarNameInputform.findViewById(R.id.bt_cardetail_add);
                btCarDetailAdd.setOnClickListener(new AddCarDetail(CarMemo.this));

                btDelete=llCarNameInputform.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(CarMemo.this,llCarLayout,llCarNameInputform,table));

                etCarName=llCarNameInputform.findViewById(R.id.et_car_name);
                EditEventListener etListener=new EditEventListener(etCarName,CarMemo.this);
                etCarName.addTextChangedListener(etListener);

                etCarName.setTag(indexCounter);
                btDelete.setTag(indexCounter);

                tagId=indexCounter;
                String str="";

                DatabaseControl control=new DatabaseControl(context,table);
                control.DatabaseDelete(tagId);

                String column1="carname";
                String column2="inputform";
                String inputform="name";

                DatabaseControl control2=new DatabaseControl
                        (context,table,tagId,_category,str,inputform);
                control2.DatabaseInsertCar(column1,column2);

                indexCounter++;
                control.IndexCounterUpdate(indexCounter);
        }

        return true;
    }

}