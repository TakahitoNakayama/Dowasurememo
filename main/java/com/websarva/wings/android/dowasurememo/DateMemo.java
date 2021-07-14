package com.websarva.wings.android.dowasurememo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Calendar;

public class DateMemo extends AppCompatActivity {

    EditText etYearOutput;
    EditText etMonthOutput;
    EditText etDayOutput;
    LayoutInflater inflater;
    LinearLayout llDateLayout;
    LinearLayout llDateInputform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_memo);

        Intent intent = getIntent();

        inflater = LayoutInflater.from(getApplicationContext());
        llDateLayout = findViewById(R.id.ll_date_layout);
        llDateInputform = (LinearLayout) inflater.inflate(R.layout.date_inputform, null);
        llDateLayout.addView(llDateInputform);


//        etYearOutput=findViewById(R.id.et_year_output);
//        etMonthOutput=findViewById(R.id.et_month_output);
//        etDayOutput=findViewById(R.id.et_day_output);
//        Button btDateSelect=findViewById(R.id.bt_date_select);


    }
}

//    public void showDatePickerDialog(View v) {
//
//        Toast.makeText
//                (DateMemo.this,
//                        "西暦の変更は左上に薄く表示されている西暦の箇所をタップしてください",
//                        Toast.LENGTH_LONG).show();
//
//        DatePickerFragment datePicker =
//                new DatePickerFragment(etYearOutput,etMonthOutput,etDayOutput);
//        datePicker.show(getSupportFragmentManager(), "datePicker");
//
//
//    }
//}

