//package com.websarva.wings.android.dowasurememo;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteStatement;
//import android.graphics.Color;
//import android.os.Build;
//import android.os.Parcelable;
//import android.text.Editable;
//import android.text.InputType;
//import android.text.TextWatcher;
//import android.util.DisplayMetrics;
//import android.view.Gravity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.DrawableRes;
//import androidx.annotation.RequiresApi;
//
//public class AddInputForm extends View {
//
//    public static LinearLayout linearLayout;
//
//    public AddInputForm(Context context) {
//        super(context);
//    }
//
//
//
//    @RequiresApi(api = Build.VERSION_CODES.Q)
//    public void createinputform(){
//     linearLayout=new LinearLayout(getContext());
//     linearLayout.setOrientation(LinearLayout.HORIZONTAL);
//     LinearLayout.LayoutParams layoutParams1=new LinearLayout.LayoutParams
//             (LinearLayout.LayoutParams.MATCH_PARENT,
//                     LinearLayout.LayoutParams.WRAP_CONTENT);
//     //layoutParams1.setMargins(0,(int) convertDp2Px(8,getContext()),0,0);
//     linearLayout.setLayoutParams(layoutParams1);
//     linearLayout.setVerticalGravity(Gravity.CENTER);
//     linearLayout.setWeightSum(1);
//
//
//
//     ImageView imageView=new ImageView(getContext());
//     LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams
//             ((int) convertDp2Px(15,getContext()),
//                     (int) convertDp2Px(15,getContext()),
//                     (float) 0.1);
//     layoutParams.setMargins((int) convertDp2Px(8,getContext()),0,0,0);
//     imageView.setLayoutParams(layoutParams);
//
//     imageView.setImageResource(R.drawable.baseline_circle_black_24);
//     imageView.setColorFilter(Color.rgb(127,255,212));
//     linearLayout.addView(imageView);
//
//     EditText editText=new EditText(getContext());
//     editText.setTextColor(Color.BLACK);
//     editText.setText("");
//     editText.setTextSize(15);
//     editText.setGravity(Gravity.CENTER);
//     //editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
//     editText.setLayoutParams(new LinearLayout.LayoutParams
//             (0,
//                     LinearLayout.LayoutParams.WRAP_CONTENT,
//                     (float) 0.3));
//     linearLayout.addView(editText);
//
//
//
//     EditText editText2=new EditText(getContext());
//     editText2.setTextColor(Color.BLACK);
//     editText2.setGravity(Gravity.CENTER);
//     editText2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
//     editText2.setBackgroundResource(R.drawable.edittext_shape);
//     editText2.setPadding(0,0,0,0);
//     //editText2.setTextSize(15);
//     LinearLayout.LayoutParams layoutParams2=new LinearLayout.LayoutParams
//             (0, LinearLayout.LayoutParams.WRAP_CONTENT, (float) 0.3);
//     layoutParams2.setMargins((int) convertDp2Px(8,getContext()),0,0,0);
//     editText2.setLayoutParams(layoutParams2);
//     linearLayout.addView(editText2);
//
//
//
//     EditText editText3=new EditText(getContext());
//     editText3.setGravity(Gravity.CENTER);
//     editText3.setLayoutParams(new LinearLayout.LayoutParams
//             (0, LinearLayout.LayoutParams.WRAP_CONTENT, (float) 0.15));
//     linearLayout.addView(editText3);
//
//
//
//
//
//    }
//
//    public float convertDp2Px(float dp, Context context) {
//        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
//        return dp * metrics.density;
//    }
//
//
//}



//class AddEventListener implements TextWatcher{
//
//    private DatabaseSQLite _helper;
//    private String _category="size";
//    int etId;
//    String strInput="null";
//
//
//
//    @Override
//    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////        etId=editText.getId();
////        EditText etInput;
////        etInput=findViewById(etId);
//
//        try{
//            strInput=s.toString();
//        }catch (NumberFormatException e){
//            strInput="null";
//        }
//
//        SQLiteDatabase db=_helper.getWritableDatabase();
//        String sqlInsert="INSERT INTO zibunmemo(_id,category,number) VALUES(?,?,?)";
//        SQLiteStatement statement=db.compileStatement(sqlInsert);
//        statement.bindLong(1,etId);
//        statement.bindString(2,_category);
//        statement.bindString(3,strInput);
//        statement.executeInsert();
//    }
//
//    @Override
//    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//    }
//
//    @Override
//    public void afterTextChanged(Editable s) {
//
//    }
//}


