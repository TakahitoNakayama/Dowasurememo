package com.websarva.wings.android.dowasurememo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class AddressMemo extends AppCompatActivity {

    LayoutInflater inflater;
    LinearLayout llAddressLayout;
    LinearLayout llAddressInputform;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_memo);

        Intent intent=getIntent();

        inflater=LayoutInflater.from(getApplicationContext());
        llAddressLayout=findViewById(R.id.ll_address_layout);
        llAddressInputform= (LinearLayout) inflater.inflate(R.layout.address_inputform,null);
        llAddressLayout.addView(llAddressInputform);


    }
}