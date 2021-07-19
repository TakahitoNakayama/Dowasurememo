package com.websarva.wings.android.dowasurememo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

public class SubscMemo extends AppCompatActivity {

    LayoutInflater inflater;
    LinearLayout linearLayout;
    LinearLayout llSubscLayout;
    LinearLayout llSubscInputform;
    LinearLayout llSubscTitle;
    LinearLayout llSubscPrice;
    LinearLayout llSubscFrame;

    EditText etSubscTitle;
    EditText etSubscPrice;

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

        LinearLayout llSubscTitle=llSubscInputform.findViewById(R.id.ll_subsc_title);
        ImageButton btDelete=llSubscTitle.findViewById(R.id.bt_delete);
        btDelete.setOnClickListener
                (new DeleteButton(SubscMemo.this,llSubscLayout,llSubscInputform));
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
                llSubscInputform=(LinearLayout)inflater.inflate(R.layout.subsc_inputform,null);
                llSubscLayout.addView(llSubscInputform);

                llSubscFrame=llSubscInputform.findViewById(R.id.ll_subsc_frame);
                llSubscTitle=llSubscFrame.findViewById(R.id.ll_subsc_title);
                llSubscPrice=llSubscFrame.findViewById(R.id.ll_subsc_price);
                ImageButton btDelete=llSubscTitle.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(SubscMemo.this,llSubscLayout,llSubscInputform));

                etSubscTitle=llSubscTitle.findViewById(R.id.et_subsc_title);
                etSubscPrice=llSubscPrice.findViewById(R.id.et_subsc_price);

                EditEventListener etListener=new EditEventListener(etSubscTitle,SubscMemo.this);
                etSubscTitle.addTextChangedListener(etListener);
                EditEventListener etListener2=new EditEventListener(etSubscPrice,SubscMemo.this);
                etSubscPrice.addTextChangedListener(etListener2);

        }
        return super.onOptionsItemSelected(item);
    }

}