package com.websarva.wings.android.dowasurememo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class CarMemo extends AppCompatActivity {

    LayoutInflater inflater;
    LinearLayout linearLayout;
    LinearLayout llCarLayout;
    LinearLayout llCarInputform;
    LinearLayout llCarNameInputform;
    LinearLayout llCarDetailInputform;

    int tagId=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_memo);

        Intent intent = getIntent();

        llCarLayout=findViewById(R.id.ll_car_layout);
//        inflater = LayoutInflater.from(getApplicationContext());
//        llCarNameInputform=(LinearLayout)inflater.inflate(R.layout.car_name_inputform,null);
//        llCarLayout.addView(llCarNameInputform,0);
//
//        ImageButton btCarDetailAdd=findViewById(R.id.bt_cardetail_add);
//        btCarDetailAdd.setOnClickListener(new ButtonListener(CarMemo.this));


    }


    public class ButtonListener extends LinearLayout implements View.OnClickListener{
        public ButtonListener(Context context) {
            super(context);
        }
        int indexcounter=1;
        @Override
        public void onClick(View v) {
            llCarDetailInputform= (LinearLayout) inflater.inflate(R.layout.car_detail_inputform,null);
            linearLayout= (LinearLayout) v.getParent();
            for(int i=0;i<llCarLayout.getChildCount();i++){
                LinearLayout sameLinearLayout= (LinearLayout) llCarLayout.getChildAt(i);
                if(linearLayout==sameLinearLayout){
                    llCarLayout.addView(llCarDetailInputform,i+1);
                    break;
                }
            }
//            int childViewCounter=llCarLayout.getChildCount();
//            llCarLayout.addView(llCarDetailInputform,childViewCounter);
//            indexcounter++;
//            Log.d("maind",""+childViewCounter);
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
                int childViewCounter=llCarLayout.getChildCount();
                llCarLayout.addView(llCarNameInputform,childViewCounter);
                Log.d("maina",""+childViewCounter);
                ImageButton btCarDetailAdd=llCarNameInputform.findViewById(R.id.bt_cardetail_add);
                btCarDetailAdd.setOnClickListener(new ButtonListener(CarMemo.this));

                tagId++;

        }

        return true;
    }

}