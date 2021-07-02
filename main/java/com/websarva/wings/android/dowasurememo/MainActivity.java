package com.websarva.wings.android.dowasurememo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btSize=findViewById(R.id.bt_size);


    }

    public void onClick(View v){
        Intent intent=new Intent(MainActivity.this,SizeMemo.class);
        startActivity(intent);


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
