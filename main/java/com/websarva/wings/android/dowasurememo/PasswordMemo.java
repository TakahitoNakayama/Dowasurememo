package com.websarva.wings.android.dowasurememo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class PasswordMemo extends AppCompatActivity {

    LayoutInflater inflater;
    LinearLayout linearLayout;
    LinearLayout llPasswordLayout;
    LinearLayout llPasswordInputform;

    int tagId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_memo);

        Intent intent = getIntent();

        llPasswordLayout = findViewById(R.id.ll_password_layout);
        inflater = LayoutInflater.from(getApplicationContext());
        llPasswordInputform=(LinearLayout)inflater.inflate(R.layout.password_inputform,null);
        llPasswordLayout.addView(llPasswordInputform,0);
    }
}