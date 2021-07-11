package com.websarva.wings.android.dowasurememo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.chrono.JapaneseDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btSize=findViewById(R.id.bt_size);
        TextView dateOutput=findViewById(R.id.date_output);
        TextView dateJPOutput=findViewById(R.id.dateJP_output);

        Calendar ca=Calendar.getInstance();
        Date da=ca.getTime();
        SimpleDateFormat dateFormat=new SimpleDateFormat
                ("y年 M月 d日 (E) H時 m分");
        dateOutput.setText(dateFormat.format(da));


//        Date date=new Date();
//        Locale locale = new Locale("ja", "JP", "JP");
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR,1999);
//        DateFormat dateFormatJP = new SimpleDateFormat("GGGGy年M月d日", locale);
//        Log.d("main",""+dateFormatJP.format(calendar.getTime()));
//        dateJPOutput.setText(dateFormatJP.format(date));



    }

    public void onClick(View v){

        switch (v.getId()){
            case R.id.bt_size:
                Intent intent=new Intent(MainActivity.this,SizeMemo.class);
                startActivity(intent);
                break;
            case R.id.bt_date:
                intent=new Intent(MainActivity.this,DateMemo.class);
                startActivity(intent);
                break;
            case R.id.bt_addres:
                break;
            case R.id.bt_car:
                break;
            case R.id.bt_update:
                break;
            case R.id.bt_password:
                break;
            case R.id.bt_subsc:
                break;
            case R.id.bt_wishlist:
                break;
            case R.id.bt_memo:
                break;


        }



    }

}

//class ButtonLayout extends androidx.appcompat.widget.AppCompatImageView {
//
//    public ButtonLayout(Context context) {
//        super(context);
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        int height = getMeasuredHeight();
//        // widthとheightに同じ値を指定することで正方形！
//        setMeasuredDimension(height, height);
//
//    }
//}
