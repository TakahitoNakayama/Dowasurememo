package com.websarva.wings.android.dowasurememo;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;

public class AddInputForm extends View {

    public static LinearLayout linearLayout;

    public AddInputForm(Context context) {
        super(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void createinputform(){
     linearLayout=new LinearLayout(getContext());
     linearLayout.setOrientation(LinearLayout.HORIZONTAL);
     linearLayout.setLayoutParams(new LinearLayout.LayoutParams
             (LinearLayout.LayoutParams.MATCH_PARENT,
                     LinearLayout.LayoutParams.WRAP_CONTENT,
                     (float) 1));
     linearLayout.setVerticalGravity(Gravity.CENTER);

     ImageView imageView=new ImageView(getContext());
     imageView.setLayoutParams(new LinearLayout.LayoutParams
             ((int) convertDp2Px(15,getContext()),
                     (int) convertDp2Px(15,getContext()),
                     (float) 0.1));
     imageView.setLayoutParams(new ViewGroup.MarginLayoutParams());
     imageView.setImageResource(R.drawable.baseline_circle_black_24);
     imageView.setColorFilter(Color.rgb(127,255,212));
     linearLayout.addView(imageView);

     EditText editText=new EditText(getContext());
     editText.setTextColor(Color.BLACK);
     editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
     editText.setLayoutParams(new LinearLayout.LayoutParams
             (0,
                     LinearLayout.LayoutParams.WRAP_CONTENT,
                     (float) 0.3));
     linearLayout.addView(editText);

     EditText editText2=new EditText(getContext());
     editText2.setTextColor(Color.BLACK);
     editText2.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
     editText2.setBackgroundResource(R.drawable.edittext_shape);
     editText2.setLayoutParams(new LinearLayout.LayoutParams
                (0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        (float) 0.3));
     linearLayout.addView(editText2);

     TextView textView=new TextView(getContext());
     textView.setText("aaa");
     textView.setLayoutParams(new LinearLayout.LayoutParams
             (0,
                     LinearLayout.LayoutParams.WRAP_CONTENT,
                     (float) 0.1));
     linearLayout.addView(textView);





    }
    public static float convertDp2Px(float dp, Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return dp * metrics.density;
    }

}


