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
import android.widget.Spinner;
import android.widget.TextView;

public class SubscMemo extends AppCompatActivity {

    private Databasehelper _helper;
    private String _category = "subsc";

    private int indexCounter=1;
    int tagId;
    String table="subsc";
    Context context=SubscMemo.this;

    LayoutInflater inflater;
    LinearLayout linearLayout;
    LinearLayout llSubscLayout;
    LinearLayout llSubscInputform;
    LinearLayout llSubscTitle;
    LinearLayout llSubscPrice;
    LinearLayout llSubscFrame;

    EditText etSubscTitle;
    EditText etSubscPrice;
    ImageButton btDelete;
    Spinner spPaymentInterbal;

    String strSubscTitle;
    String strSubscPrice;

    int monthPaymentAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subsc_memo);

        Intent intent = getIntent();
        llSubscLayout = findViewById(R.id.ll_subsc_layout);

        _helper=new Databasehelper(getApplicationContext());
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlSelect="SELECT * FROM subsc";
        Cursor cursor=db.rawQuery(sqlSelect,null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int i = cursor.getColumnIndex("_id");
            tagId = cursor.getInt(i);
            llSubscLayout = findViewById(R.id.ll_subsc_layout);
            inflater = LayoutInflater.from(getApplicationContext());
            llSubscInputform=(LinearLayout)inflater.inflate(R.layout.subsc_inputform,null);
            llSubscLayout.addView(llSubscInputform);

            llSubscFrame=llSubscInputform.findViewById(R.id.ll_subsc_frame);
            llSubscTitle=llSubscFrame.findViewById(R.id.ll_subsc_title);
            llSubscPrice=llSubscFrame.findViewById(R.id.ll_subsc_price);

            etSubscTitle=llSubscTitle.findViewById(R.id.et_subsc_title);
            etSubscPrice=llSubscPrice.findViewById(R.id.et_subsc_price);
            btDelete=llSubscTitle.findViewById(R.id.bt_delete);
            btDelete.setOnClickListener
                    (new DeleteButton(SubscMemo.this,llSubscLayout,llSubscInputform,table));


            etSubscTitle.setTag(tagId);
            etSubscPrice.setTag(tagId);
            btDelete.setTag(tagId);

            i = cursor.getColumnIndex("subsctitle");
            strSubscTitle = cursor.getString(i);

            i = cursor.getColumnIndex("subscprice");
            strSubscPrice = cursor.getString(i);


            try {
                etSubscTitle.setText(strSubscTitle);
                EditEventListener etListener=new EditEventListener(etSubscTitle,SubscMemo.this);
                etSubscTitle.addTextChangedListener(etListener);
            } catch (NullPointerException e) {
                strSubscTitle = "";
            }

            try {
                etSubscPrice.setText(strSubscPrice);
                EditEventListener etListener2=new EditEventListener(etSubscPrice,SubscMemo.this);
                etSubscPrice.addTextChangedListener(etListener2);
            } catch (NullPointerException e) {
                strSubscPrice = "";
            }

        }

        MonthPayment payment=new MonthPayment();
        payment.culcMonthPayment();

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
                inflater = LayoutInflater.from(getApplicationContext());
                llSubscInputform=(LinearLayout)inflater.inflate(R.layout.subsc_inputform,null);
                llSubscLayout.addView(llSubscInputform);

                llSubscFrame=llSubscInputform.findViewById(R.id.ll_subsc_frame);
                llSubscTitle=llSubscFrame.findViewById(R.id.ll_subsc_title);
                llSubscPrice=llSubscFrame.findViewById(R.id.ll_subsc_price);


                etSubscTitle=llSubscTitle.findViewById(R.id.et_subsc_title);
                etSubscPrice=llSubscPrice.findViewById(R.id.et_subsc_price);
                btDelete=llSubscTitle.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(SubscMemo.this,llSubscLayout,llSubscInputform,table));
                spPaymentInterbal=linearLayout.findViewById(R.id.sp_payment_interbal);

                etSubscTitle.setTag(indexCounter);
                etSubscPrice.setTag(indexCounter);
                btDelete.setTag(indexCounter);
                spPaymentInterbal.setTag(indexCounter);

                EditEventListener etListener=new EditEventListener(etSubscTitle,SubscMemo.this);
                etSubscTitle.addTextChangedListener(etListener);
                EditEventListener etListener2=new EditEventListener(etSubscPrice,SubscMemo.this);
                etSubscPrice.addTextChangedListener(etListener2);

                tagId=indexCounter;
                String str="";
                String spinnerIndex="0";

                DatabaseControl control=new DatabaseControl(context,table);
                control.DatabaseDelete(tagId);

                String column1="subsctitle";
                String column2="subscprice";
                String column3="subscinterbal";

                DatabaseControl control2=new DatabaseControl
                        (context,table,tagId,_category,str,spinnerIndex);
                control2.DatabaseInsertSubsc(column1,column2,column3);

                indexCounter++;
                control.IndexCounterUpdate(indexCounter);

                MonthPayment payment=new MonthPayment();
                payment.culcMonthPayment();


        }
        return super.onOptionsItemSelected(item);
    }

    class MonthPayment{
        public void culcMonthPayment(){
            TextView tvMonthPayment=findViewById(R.id.tv_month_payment);
            int price=0;
            String strprice;
            monthPaymentAmount=0;

            for(int i=0;i<llSubscLayout.getChildCount();i++){
                linearLayout= (LinearLayout) llSubscLayout.getChildAt(i);
                etSubscPrice=linearLayout.findViewById(R.id.et_subsc_price);
                price=0;

                try {
                    strprice= String.valueOf(etSubscPrice.getText());
                    price=Integer.valueOf(strprice);
                } catch (NumberFormatException e) {
                    strprice ="0";
                }

                spPaymentInterbal=linearLayout.findViewById(R.id.sp_payment_interbal);
                String strInterbal= (String) spPaymentInterbal.getSelectedItem();
                switch (strInterbal){
                    case "毎月":
                        monthPaymentAmount+=price;
                        break;
                    case "2ヶ月":
                        monthPaymentAmount+=price/2;
                        break;
                    case "3ヶ月":
                        monthPaymentAmount+=price/3;
                        break;
                    case "4ヶ月":
                        monthPaymentAmount+=price/4;
                        break;
                    case "半年":
                        monthPaymentAmount+=price/6;
                        break;
                    case "1年":
                        monthPaymentAmount+=price/12;
                        break;
                    case "2年":
                        monthPaymentAmount+=price/24;
                        break;

                }
                //paymentInterbal.setSelection(3);
                //Log.d("main179",""+strInterbal);
            }
            String strmonthPaymentAmount=String.valueOf(monthPaymentAmount);
            tvMonthPayment.setText(strmonthPaymentAmount);

//            Log.d("main176",""+llSubscLayout.getChildCount());
//            linearLayout= (LinearLayout) llSubscLayout.getChildAt(5);
//            Log.d("main179",""+linearLayout);


        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        for(int i=0;i<llSubscLayout.getChildCount();i++){
            linearLayout= (LinearLayout) llSubscLayout.getChildAt(i);
            spPaymentInterbal=linearLayout.findViewById(R.id.sp_payment_interbal);
            String strInterbal= (String) spPaymentInterbal.getSelectedItem();
            

            DatabaseControl control=new DatabaseControl(context,table);
            control.SpinnerIndexUpdate(strInterbal,);






    }
}


