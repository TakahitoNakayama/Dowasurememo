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

public class UpdateMemo extends AppCompatActivity {

    LayoutInflater inflater;
    LinearLayout linearLayout;
    LinearLayout llUpdateLayout;
    LinearLayout llUpdateInputform;
    LinearLayout llUpdateTitle;
    LinearLayout llUpdateDeadline;

    EditText etUpdateTitle;
    EditText etUpdateYear;
    EditText etUpdateMonth;
    EditText etUpdateDay;

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


        LinearLayout llUpdateDeadline=llUpdateInputform.findViewById(R.id.ll_update_deadline);
        ImageButton btDelete=llUpdateDeadline.findViewById(R.id.bt_delete);
        btDelete.setOnClickListener
                (new DeleteButton(UpdateMemo.this,llUpdateLayout,llUpdateInputform));



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
                llUpdateInputform=(LinearLayout)inflater.inflate(R.layout.update_inputform,null);
                llUpdateLayout.addView(llUpdateInputform);

                llUpdateTitle=llUpdateInputform.findViewById(R.id.ll_update_title);
                llUpdateDeadline=llUpdateInputform.findViewById(R.id.ll_update_deadline);
                ImageButton btDelete=llUpdateDeadline.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(UpdateMemo.this,llUpdateLayout,llUpdateInputform));

                etUpdateTitle=llUpdateTitle.findViewById(R.id.et_update_title);
                etUpdateYear=llUpdateDeadline.findViewById(R.id.et_update_year);
                etUpdateMonth=llUpdateDeadline.findViewById(R.id.et_update_month);
                etUpdateDay=llUpdateDeadline.findViewById(R.id.et_update_day);

                EditEventListener etListener=new EditEventListener(etUpdateTitle,UpdateMemo.this);
                etUpdateTitle.addTextChangedListener(etListener);
                EditEventListener etListener2=new EditEventListener(etUpdateYear,UpdateMemo.this);
                etUpdateYear.addTextChangedListener(etListener2);
                EditEventListener etListener3=new EditEventListener(etUpdateMonth,UpdateMemo.this);
                etUpdateMonth.addTextChangedListener(etListener3);
                EditEventListener etListener4=new EditEventListener(etUpdateDay,UpdateMemo.this);
                etUpdateDay.addTextChangedListener(etListener4);

        }
        return super.onOptionsItemSelected(item);
    }


}