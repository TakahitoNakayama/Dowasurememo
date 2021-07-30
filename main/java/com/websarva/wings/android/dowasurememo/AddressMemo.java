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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class AddressMemo extends AppCompatActivity {

    private Databasehelper _helper;
    private String _category = "address";

    private int indexCounter=1;
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

            etAddressTitle.setTag(tagId);
            etPostNumber1.setTag(tagId);
            etPostNumber2.setTag(tagId);
            etAddressDetail.setTag(tagId);
            btDelete.setTag(tagId);

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
                EditEventListener etListener=new EditEventListener(etAddressTitle,AddressMemo.this);
                etAddressTitle.addTextChangedListener(etListener);
            } catch (NullPointerException e) {
                strAddressTitle = "";
            }

            try {
                etPostNumber1.setText(strPostNumber1);
                EditEventListener etListener2=new EditEventListener(etPostNumber1,AddressMemo.this);
                etPostNumber1.addTextChangedListener(etListener2);
            } catch (NullPointerException e) {
                strPostNumber1 = "";
            }

            try {
                etPostNumber2.setText(strPostNumber2);
                EditEventListener etListener3=new EditEventListener(etPostNumber2,AddressMemo.this);
                etPostNumber2.addTextChangedListener(etListener3);
            } catch (NullPointerException e) {
                strPostNumber2 = "";
            }

            try {
                etAddressDetail.setText(strAddressDetail);
                EditEventListener etListener3=new EditEventListener(etAddressDetail,AddressMemo.this);
                etAddressDetail.addTextChangedListener(etListener3);
            } catch (NullPointerException e) {
                strAddressDetail = "";
            }
        }

        DatabaseControl control=new DatabaseControl(context,table);
        indexCounter=control.GetIndexCounter();
        Log.d("main",""+indexCounter);
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


                etAddressTitle.setTag(indexCounter);
                etPostNumber1.setTag(indexCounter);
                etPostNumber2.setTag(indexCounter);
                etAddressDetail.setTag(indexCounter);
                btDelete.setTag(indexCounter);

                EditEventListener etListener=new EditEventListener(etAddressTitle,AddressMemo.this);
                etAddressTitle.addTextChangedListener(etListener);
                EditEventListener etListener2=new EditEventListener(etPostNumber1,AddressMemo.this);
                etPostNumber1.addTextChangedListener(etListener2);
                EditEventListener etListener3=new EditEventListener(etPostNumber2,AddressMemo.this);
                etPostNumber2.addTextChangedListener(etListener3);
                EditEventListener etListener4=new EditEventListener(etAddressDetail,AddressMemo.this);
                etAddressDetail.addTextChangedListener(etListener4);

                tagId=indexCounter;
                String str="";

                DatabaseControl control=new DatabaseControl(context,table);
                control.DatabaseDelete(tagId);

                String column1="addresstitle";
                String column2="postnumber1";
                String column3="postnumber2";
                String column4="addressdetail";

                DatabaseControl control2=new DatabaseControl
                        (context,table,tagId,_category,str);
                control2.DatabaseInsert(column1,column2,column3,column4);

                indexCounter++;
                control.IndexCounterUpdate(indexCounter);
        }
        return super.onOptionsItemSelected(item);
    }


}