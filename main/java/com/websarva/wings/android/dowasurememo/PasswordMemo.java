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

public class PasswordMemo extends AppCompatActivity {

    LayoutInflater inflater;
    LinearLayout linearLayout;
    LinearLayout llPasswordLayout;
    LinearLayout llPasswordInputform;
    LinearLayout llPasswordFrame;
    LinearLayout llPasswordTitle;
    LinearLayout llPasswordContents;

    EditText etPasswordTitle;
    EditText etPasswordContents;

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

        LinearLayout llPasswordTitle=llPasswordInputform.findViewById(R.id.ll_password_title);
        ImageButton btDelete=llPasswordTitle.findViewById(R.id.bt_delete);
        btDelete.setOnClickListener
                (new DeleteButton(PasswordMemo.this,llPasswordLayout,llPasswordInputform));
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
                llPasswordInputform=(LinearLayout)inflater.inflate(R.layout.password_inputform,null);
                llPasswordLayout.addView(llPasswordInputform);

                llPasswordFrame=llPasswordInputform.findViewById(R.id.ll_password_frame);
                llPasswordTitle=llPasswordFrame.findViewById(R.id.ll_password_title);
                llPasswordContents=llPasswordFrame.findViewById(R.id.ll_password_contents);
                ImageButton btDelete=llPasswordTitle.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(PasswordMemo.this,llPasswordLayout,llPasswordInputform));

                etPasswordTitle=llPasswordTitle.findViewById(R.id.et_password_title);
                etPasswordContents=llPasswordContents.findViewById(R.id.et_password_contents);

                EditEventListener etListener=new EditEventListener(etPasswordTitle,PasswordMemo.this);
                etPasswordTitle.addTextChangedListener(etListener);
                EditEventListener etListener2=new EditEventListener(etPasswordContents,PasswordMemo.this);
                etPasswordContents.addTextChangedListener(etListener2);




        }
        return super.onOptionsItemSelected(item);
    }
}