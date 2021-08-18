package com.websarva.wings.android.dowasurememo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 *住所を入力するAddressメモのクラス
 * @author nakayama
 * @version 1.0.2
 */
public class AddressMemo extends AppCompatActivity {

    private Databasehelper _helper;
    private static final String _CATEGORY = "ADDRESS";

    /**
     *データベースに保存する際のID値
     */
    private int tagId=2;

    /**
     * データベースのテーブル名
     */
    private static final String TABLE="address";

    private Context context=AddressMemo.this;

    private LayoutInflater inflater;
    private LinearLayout llAddressLayout;
    private LinearLayout llAddressInputform;
    private LinearLayout llAddressFrame;
    private LinearLayout llPostNumberinputform;

    private EditText etAddressTitle;
    private EditText etPostNumber1;
    private EditText etPostNumber2;
    private EditText etAddressDetail;
    private ImageButton btDelete;

    private String strAddressTitle;
    private String strPostNumber1;
    private String strPostNumber2;
    private String strAddressDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_memo);

        Intent intent=getIntent();
        inflater=LayoutInflater.from(getApplicationContext());
        llAddressLayout=findViewById(R.id.ll_address_layout);
        llAddressInputform= (LinearLayout) inflater.inflate(R.layout.address_inputform,null);

//        llAddressLayout.removeAllViews();
//        DatabaseControl control4=new DatabaseControl(context,table);
//        control4.DatabaseAllDelete();

        /**
         * データベースの列名の配列
         */
        String[] columnNames={"addresstitle","postnumber1","postnumber2","addressdetail"};

        /**
         * データベースからデータを取り出して、レイアウトを作成する
         * @param context　コンテキスト
         * @param TABLE　テーブル名
         * @param columnNames　データベースの列名の配列
         */
        DatabaseControl control=new DatabaseControl(context,TABLE,columnNames);
        control.selectDatabase(llAddressLayout,llAddressInputform);

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
                btDelete=llPostNumberinputform.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(AddressMemo.this,llAddressLayout,llAddressInputform,TABLE));

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        tagId = 2;

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

            DatabaseControl control = new DatabaseControl(context, TABLE);
            control.deleteDatabase(tagId);

            String column1="addresstitle";
            String column2="postnumber1";
            String column3="postnumber2";
            String column4="addressdetail";

            DatabaseControl control2 = new DatabaseControl
                    (context, TABLE, tagId, _CATEGORY, strAddressTitle,strPostNumber1,strPostNumber2,strAddressDetail);
            control2.insertDatabaseFourColumns(column1, column2, column3, column4);

            tagId++;

        }
    }

}