package com.websarva.wings.android.dowasurememo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class SubscMemo extends AppCompatActivity {

    LayoutInflater inflater;
    LinearLayout linearLayout;
    LinearLayout llSubscLayout;
    LinearLayout llSubscInputform;

    int tagId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subsc_memo);

        Intent intent = getIntent();

        llSubscLayout = findViewById(R.id.ll_subsc_layout);
        inflater = LayoutInflater.from(getApplicationContext());
        llSubscInputform=(LinearLayout)inflater.inflate(R.layout.subsc_inputform,null);
        llSubscLayout.addView(llSubscInputform,0);
    }
}