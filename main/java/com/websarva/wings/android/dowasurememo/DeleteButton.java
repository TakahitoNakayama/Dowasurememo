package com.websarva.wings.android.dowasurememo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.view.View;
import android.widget.LinearLayout;

public class DeleteButton extends LinearLayout implements View.OnClickListener{

    LinearLayout activityLinearLayout;
    LinearLayout linearLayout;

    public DeleteButton(Context context,LinearLayout activitylinearlayout,LinearLayout parentview) {
        super(context);
        activityLinearLayout=activitylinearlayout;
        linearLayout=parentview;
    }


    @Override
    public void onClick(View v) {
//        tagId= (int) v.getTag();

        activityLinearLayout.removeView(linearLayout);

//        _helper=new Databasehelper(SizeMemo.this);
//        SQLiteDatabase db=_helper.getWritableDatabase();
//        String sqlDelete="DELETE FROM zibunmemo WHERE _id = ?";
//        SQLiteStatement statement=db.compileStatement(sqlDelete);
//        statement.bindLong(1,tagId);
//        statement.executeUpdateDelete();
    }
}

