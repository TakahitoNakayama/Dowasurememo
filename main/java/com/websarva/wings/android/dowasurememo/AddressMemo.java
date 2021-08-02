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

public class AddressMemo extends AppCompatActivity {

    private Databasehelper _helper;
    private String _category = "address";

    private int indexCounter=2;
    int tagId;
    String table="address";
    Context context=AddressMemo.this;

    LayoutInflater inflater;
    LinearLayout llAddressLayout;
    LinearLayout llAddressInputform;
    LinearLayout llAddressFrame;
    LinearLayout llPostNumberinputform;

    EditText etAddressTitle;
    EditText etPostNumber1;
    EditText etPostNumber2;
    EditText etAddressDetail;
    ImageButton btDelete;

    String strAddressTitle;
    String strPostNumber1;
    String strPostNumber2;
    String strAddressDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_memo);

        Intent intent=getIntent();
        llAddressLayout=findViewById(R.id.ll_address_layout);

//        llAddressLayout.removeAllViews();
//        DatabaseControl control4=new DatabaseControl(context,table);
//        control4.DatabaseAllDelete();

        _helper=new Databasehelper(getApplicationContext());
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlSelect="SELECT * FROM address";
        Cursor cursor=db.rawQuery(sqlSelect,null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int i = cursor.getColumnIndex("_id");
            tagId = cursor.getInt(i);
            inflater=LayoutInflater.from(getApplicationContext());
            llAddressLayout=findViewById(R.id.ll_address_layout);
            llAddressInputform= (LinearLayout) inflater.inflate(R.layout.address_inputform,null);
            llAddressLayout.addView(llAddressInputform);

            llAddressFrame=llAddressInputform.findViewById(R.id.ll_address_frame);
            llPostNumberinputform=llAddressInputform.findViewById(R.id.ll_postnumber_inputform);

            etAddressTitle = llAddressFrame.findViewById(R.id.et_address_title);
            etPostNumber1 = llPostNumberinputform.findViewById(R.id.et_postnumber1);
            etPostNumber2 = llPostNumberinputform.findViewById(R.id.et_postnumber2);
            etAddressDetail = llAddressFrame.findViewById(R.id.et_addres_detail);
            btDelete = llPostNumberinputform.findViewById(R.id.bt_delete);
            btDelete.setOnClickListener
                    (new DeleteButton(context, llAddressLayout, llAddressInputform, table));

            i = cursor.getColumnIndex("addresstitle");
            strAddressTitle = cursor.getString(i);

            i = cursor.getColumnIndex("postnumber1");
            strPostNumber1 = cursor.getString(i);

            i = cursor.getColumnIndex("postnumber2");
            strPostNumber2 = cursor.getString(i);

            i = cursor.getColumnIndex("addressdetail");
            strAddressDetail = cursor.getString(i);


            try {
                etAddressTitle.setText(strAddressTitle);
            } catch (NullPointerException e) {
                strAddressTitle = "";
            }

            try {
                etPostNumber1.setText(strPostNumber1);
            } catch (NullPointerException e) {
                strPostNumber1 = "";
            }

            try {
                etPostNumber2.setText(strPostNumber2);
            } catch (NullPointerException e) {
                strPostNumber2 = "";
            }

            try {
                etAddressDetail.setText(strAddressDetail);
            } catch (NullPointerException e) {
                strAddressDetail = "";
            }
        }

        if(llAddressLayout.getChildCount()!=0){
            LinearLayout firstView= (LinearLayout) llAddressLayout.getChildAt(0);
            firstView.setVisibility(View.GONE);
        }else {
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
                if(llAddressLayout.getChildCount()==0){
                    inflater=LayoutInflater.from(getApplicationContext());
                    llAddressLayout=findViewById(R.id.ll_address_layout);
                    llAddressInputform= (LinearLayout) inflater.inflate(R.layout.address_inputform,null);
                    llAddressLayout.addView(llAddressInputform);
                    llAddressInputform.setVisibility(View.GONE);

                    String str="";
                    DatabaseControl control = new DatabaseControl(context, table);
                    control.DatabaseDelete(1);

                    String column1="addresstitle";
                    String column2="postnumber1";
                    String column3="postnumber2";
                    String column4="addressdetail";

                    DatabaseControl control2 = new DatabaseControl
                            (context, table,1, _category, str, str, str, str);
                    control2.DatabaseInsertFourColumns(column1, column2, column3,column4);
                }
                inflater=LayoutInflater.from(getApplicationContext());
                llAddressLayout=findViewById(R.id.ll_address_layout);
                llAddressInputform = (LinearLayout) inflater.inflate(R.layout.address_inputform, null);
                llAddressLayout.addView(llAddressInputform);

                llAddressFrame=llAddressInputform.findViewById(R.id.ll_address_frame);
                llPostNumberinputform=llAddressFrame.findViewById(R.id.ll_postnumber_inputform);


                etAddressTitle=llAddressFrame.findViewById(R.id.et_address_title);
                etPostNumber1=llPostNumberinputform.findViewById(R.id.et_postnumber1);
                etPostNumber2=llPostNumberinputform.findViewById(R.id.et_postnumber2);
                etAddressDetail=llAddressFrame.findViewById(R.id.et_addres_detail);
                btDelete=llPostNumberinputform.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(AddressMemo.this,llAddressLayout,llAddressInputform,table));

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        indexCounter = 2;

        for (int i = 0; i < llAddressLayout.getChildCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) llAddressLayout.getChildAt(i);
            etAddressTitle = linearLayout.findViewById(R.id.et_address_title);
            etPostNumber1 = linearLayout.findViewById(R.id.et_postnumber1);
            etPostNumber2 = linearLayout.findViewById(R.id.et_postnumber2);
            etAddressDetail = linearLayout.findViewById(R.id.et_addres_detail);

            strAddressTitle = etAddressTitle.getText().toString();
            strPostNumber1 = etPostNumber1.getText().toString();
            strPostNumber2 = etPostNumber2.getText().toString();
            strAddressDetail = etAddressDetail.getText().toString();

            DatabaseControl control = new DatabaseControl(context, table);
            control.DatabaseDelete(indexCounter);

            String column1="addresstitle";
            String column2="postnumber1";
            String column3="postnumber2";
            String column4="addressdetail";

            DatabaseControl control2 = new DatabaseControl
                    (context, table, indexCounter, _category, strAddressTitle,strPostNumber1,strPostNumber2,strAddressDetail);
            control2.DatabaseInsertFourColumns(column1, column2, column3, column4);

            Log.d("pause358", "" + indexCounter);
            indexCounter++;

        }
    }

}