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

public class AddressMemo extends AppCompatActivity {

    LayoutInflater inflater;
    LinearLayout llAddressLayout;
    LinearLayout llAddressInputform;
    LinearLayout llAddressFrame;
    EditText etAddressTitle;
    EditText etPostNumber1;
    EditText etPostNumber2;
    EditText etAddressDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_memo);

        Intent intent=getIntent();

        inflater=LayoutInflater.from(getApplicationContext());
        llAddressLayout=findViewById(R.id.ll_address_layout);
        llAddressInputform= (LinearLayout) inflater.inflate(R.layout.address_inputform,null);
        llAddressLayout.addView(llAddressInputform);

        LinearLayout llPostNumberinputform=llAddressInputform.findViewById(R.id.ll_postnumber_inputform);
        ImageButton btDelete=llPostNumberinputform.findViewById(R.id.bt_delete);
        btDelete.setOnClickListener
                (new DeleteButton(AddressMemo.this,llAddressLayout,llAddressInputform));

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
                llAddressLayout=findViewById(R.id.ll_address_layout);
                llAddressInputform = (LinearLayout) inflater.inflate(R.layout.address_inputform, null);
                llAddressLayout.addView(llAddressInputform);

                llAddressFrame=llAddressInputform.findViewById(R.id.ll_address_frame);
                LinearLayout llPostNumberinputform=llAddressFrame.findViewById(R.id.ll_postnumber_inputform);
                ImageButton btDelete=llPostNumberinputform.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(AddressMemo.this,llAddressLayout,llAddressInputform));

                etAddressTitle=llAddressFrame.findViewById(R.id.et_address_title);
                etPostNumber1=llPostNumberinputform.findViewById(R.id.et_postnumber1);
                etPostNumber2=llPostNumberinputform.findViewById(R.id.et_postnumber2);
                etAddressDetail=llAddressFrame.findViewById(R.id.et_addres_detail);

                EditEventListener etListener=new EditEventListener(etAddressTitle,AddressMemo.this);
                etAddressTitle.addTextChangedListener(etListener);
                EditEventListener etListener2=new EditEventListener(etPostNumber1,AddressMemo.this);
                etPostNumber1.addTextChangedListener(etListener2);
                EditEventListener etListener3=new EditEventListener(etPostNumber2,AddressMemo.this);
                etPostNumber2.addTextChangedListener(etListener3);
                EditEventListener etListener4=new EditEventListener(etAddressDetail,AddressMemo.this);
                etAddressDetail.addTextChangedListener(etListener4);


        }
        return super.onOptionsItemSelected(item);
    }


}