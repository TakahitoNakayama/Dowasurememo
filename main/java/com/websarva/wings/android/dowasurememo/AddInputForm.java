package com.websarva.wings.android.dowasurememo;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
             (ViewGroup.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));

     Button button=new Button(getContext());
     button.setText("555");
     TextView textView=new TextView(getContext());
     textView.setText("あああああ");
     linearLayout.addView(button);
     linearLayout.addView(textView);


     ImageView imageView=new ImageView(getContext());
     imageView.setImageResource(R.drawable.baseline_circle_black_24);
     imageView.setColorFilter(Color.rgb(127,255,212));
     linearLayout.addView(imageView);

     EditText editText=new EditText(getContext());
     editText.setTextColor(Color.BLACK);
     editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);





    }







}
