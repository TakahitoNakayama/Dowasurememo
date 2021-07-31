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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class SubscMemo extends AppCompatActivity {

    private Databasehelper _helper;
    private String _category = "subsc";

    private int indexCounter = 2;
    int tagId;
    String table = "subsc";
    Context context = SubscMemo.this;

    LayoutInflater inflater;
    LinearLayout linearLayout;
    LinearLayout llSubscLayout;
    LinearLayout llSubscInputform;
    LinearLayout llSubscTitle;
    LinearLayout llSubscPrice;
    LinearLayout llSubscFrame;

    EditText etSubscTitle;
    EditText etSubscPrice;
    Button btSubscCulc;
    ImageButton btDelete;
    Spinner spPaymentInterbal;

    String strSubscTitle;
    String strSubscPrice;
    int intSpinnerIndex;
    String strSpinnerIndex;

    int monthPaymentAmount;
    TextView tvMonthPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subsc_memo);

        Intent intent = getIntent();
        llSubscLayout = findViewById(R.id.ll_subsc_layout);
        tvMonthPayment = findViewById(R.id.tv_month_payment);

        _helper = new Databasehelper(getApplicationContext());
        SQLiteDatabase db = _helper.getWritableDatabase();
        String sqlSelect = "SELECT * FROM subsc";
        Cursor cursor = db.rawQuery(sqlSelect, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int d = cursor.getColumnIndex("_id");
            tagId = cursor.getInt(d);

            inflater = LayoutInflater.from(getApplicationContext());
            llSubscLayout = findViewById(R.id.ll_subsc_layout);
            llSubscInputform = (LinearLayout) inflater.inflate(R.layout.subsc_inputform, null);
            llSubscLayout.addView(llSubscInputform);

            llSubscFrame = llSubscInputform.findViewById(R.id.ll_subsc_frame);
            llSubscTitle = llSubscFrame.findViewById(R.id.ll_subsc_title);
            llSubscPrice = llSubscFrame.findViewById(R.id.ll_subsc_price);

            etSubscTitle = llSubscTitle.findViewById(R.id.et_subsc_title);
            etSubscPrice = llSubscPrice.findViewById(R.id.et_subsc_price);
            btDelete = llSubscTitle.findViewById(R.id.bt_delete);
            btDelete.setOnClickListener
                    (new DeleteButton(SubscMemo.this, llSubscLayout, llSubscInputform, table));
            spPaymentInterbal = llSubscPrice.findViewById(R.id.sp_payment_interbal);
            btSubscCulc=findViewById(R.id.bt_subsc_culc);
            btSubscCulc.setOnClickListener(new CulcButtonListener());

            int i = cursor.getColumnIndex("subsctitle");
            strSubscTitle = cursor.getString(i);

            i = cursor.getColumnIndex("subscprice");
            strSubscPrice = cursor.getString(i);

            i = cursor.getColumnIndex("subscinterbal");
            intSpinnerIndex = cursor.getInt(i);
            spPaymentInterbal.setSelection(intSpinnerIndex);

            try {
                etSubscTitle.setText(strSubscTitle);
            } catch (NullPointerException e) {
                strSubscTitle = "";
            }

            try {
                etSubscPrice.setText(strSubscPrice);
            } catch (NullPointerException e) {
                strSubscPrice = "";
            }

        }
        if(llSubscLayout.getChildCount()!=0){
            LinearLayout firstView= (LinearLayout) llSubscLayout.getChildAt(0);
            firstView.setVisibility(View.GONE);
        }else {
        }

    }

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
                if(llSubscLayout.getChildCount()==0){
                    inflater = LayoutInflater.from(getApplicationContext());
                    llSubscLayout = findViewById(R.id.ll_subsc_layout);
                    llSubscInputform = (LinearLayout) inflater.inflate(R.layout.subsc_inputform, null);
                    llSubscLayout.addView(llSubscInputform);
                    llSubscInputform.setVisibility(View.GONE);

                    String str="";
                    DatabaseControl control = new DatabaseControl(context, table);
                    control.DatabaseDelete(1);

                    String column1 = "subsctitle";
                    String column2 = "subscprice";
                    String column3 = "subscinterbal";

                    DatabaseControl control2 = new DatabaseControl
                            (context, table,1, _category, str, str,str);
                    control2.DatabaseInsertThreeColumns(column1, column2,column3);
                }
                llSubscLayout = findViewById(R.id.ll_subsc_layout);
                inflater = LayoutInflater.from(getApplicationContext());
                llSubscInputform = (LinearLayout) inflater.inflate(R.layout.subsc_inputform, null);
                llSubscLayout.addView(llSubscInputform);


                llSubscFrame = llSubscInputform.findViewById(R.id.ll_subsc_frame);
                llSubscTitle = llSubscFrame.findViewById(R.id.ll_subsc_title);
                llSubscPrice = llSubscFrame.findViewById(R.id.ll_subsc_price);


                etSubscTitle = llSubscTitle.findViewById(R.id.et_subsc_title);
                etSubscPrice = llSubscPrice.findViewById(R.id.et_subsc_price);
                btDelete = llSubscTitle.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(SubscMemo.this, llSubscLayout, llSubscInputform, table));
                spPaymentInterbal = llSubscPrice.findViewById(R.id.sp_payment_interbal);
                btSubscCulc=findViewById(R.id.bt_subsc_culc);
                btSubscCulc.setOnClickListener(new CulcButtonListener());

        }
        return super.onOptionsItemSelected(item);
    }

    class CulcButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            int price;
            String strprice;
            monthPaymentAmount = 0;

            for (int i = 0; i < llSubscLayout.getChildCount(); i++) {
                linearLayout = (LinearLayout) llSubscLayout.getChildAt(i);
                etSubscPrice = linearLayout.findViewById(R.id.et_subsc_price);
                price = 0;

                try {
                    strprice = String.valueOf(etSubscPrice.getText());
                    price = Integer.valueOf(strprice);
                } catch (NumberFormatException e) {
                    strprice = "0";
                }

                spPaymentInterbal = linearLayout.findViewById(R.id.sp_payment_interbal);
                String strInterbal = (String) spPaymentInterbal.getSelectedItem();
                switch (strInterbal) {
                    case "毎月":
                        monthPaymentAmount += price;
                        break;
                    case "2ヶ月":
                        monthPaymentAmount += price / 2;
                        break;
                    case "3ヶ月":
                        monthPaymentAmount += price / 3;
                        break;
                    case "4ヶ月":
                        monthPaymentAmount += price / 4;
                        break;
                    case "半年":
                        monthPaymentAmount += price / 6;
                        break;
                    case "1年":
                        monthPaymentAmount += price / 12;
                        break;
                    case "2年":
                        monthPaymentAmount += price / 24;
                        break;

                }
            }
            tvMonthPayment.setText(String.format("%,d", monthPaymentAmount));
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        for (int i = 0; i < llSubscLayout.getChildCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) llSubscLayout.getChildAt(i);
            etSubscTitle = linearLayout.findViewById(R.id.et_subsc_title);
            etSubscPrice = linearLayout.findViewById(R.id.et_subsc_price);
            spPaymentInterbal=linearLayout.findViewById(R.id.sp_payment_interbal);

            strSubscTitle = etSubscTitle.getText().toString();
            strSubscPrice = etSubscPrice.getText().toString();
            intSpinnerIndex=spPaymentInterbal.getSelectedItemPosition();
            strSpinnerIndex=String.valueOf(intSpinnerIndex);


            DatabaseControl control = new DatabaseControl(context, table);
            control.DatabaseDelete(indexCounter);

            String column1 = "subsctitle";
            String column2 = "subscprice";
            String column3 = "subscinterbal";


            DatabaseControl control2 = new DatabaseControl
                    (context, table, indexCounter, _category, strSubscTitle, strSubscPrice,strSpinnerIndex);
            control2.DatabaseInsertThreeColumns(column1, column2,column3);

            Log.d("pause358", "" + indexCounter);
            indexCounter++;

        }
    }
}

