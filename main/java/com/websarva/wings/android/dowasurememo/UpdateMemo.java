package com.websarva.wings.android.dowasurememo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class UpdateMemo extends AppCompatActivity {

    LayoutInflater inflater;
    LinearLayout linearLayout;
    LinearLayout llUpdateLayout;
    LinearLayout llUpdateInputform;

    int tagId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_memo);

        Intent intent = getIntent();

        llUpdateLayout = findViewById(R.id.ll_update_layout);
        inflater = LayoutInflater.from(getApplicationContext());
        llUpdateInputform=(LinearLayout)inflater.inflate(R.layout.update_inputform,null);
        llUpdateLayout.addView(llUpdateInputform,0);

//        ImageButton btCarDetailAdd=findViewById(R.id.bt_cardetail_add);
//        btCarDetailAdd.setOnClickListener(new ButtonListener(CarMemo.this));

        LinearLayout llUpdateDeadline=llUpdateInputform.findViewById(R.id.ll_update_deadline);
        ImageButton btDelete=llUpdateDeadline.findViewById(R.id.bt_delete);
        btDelete.setOnClickListener
                (new DeleteButton(UpdateMemo.this,llUpdateLayout,llUpdateInputform));



    }
}