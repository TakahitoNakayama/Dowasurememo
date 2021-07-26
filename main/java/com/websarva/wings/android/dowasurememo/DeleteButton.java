package com.websarva.wings.android.dowasurememo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.view.View;
import android.widget.LinearLayout;

public class DeleteButton extends LinearLayout implements View.OnClickListener{

    LinearLayout activityLinearLayout;
    LinearLayout linearLayout;
    Databasehelper _helper;
    Context context;
    String table;
    int tagId;

    public DeleteButton
            (Context c,LinearLayout llactivity,LinearLayout parentview,String ta) {
        super(c);
        activityLinearLayout=llactivity;
        linearLayout=parentview;
        context=c;
        table=ta;
    }


    @Override
    public void onClick(View v) {
        tagId= (int) v.getTag();
        activityLinearLayout.removeView(linearLayout);

        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlDelete="DELETE FROM "+table+" WHERE _id = ?";
        SQLiteStatement statement=db.compileStatement(sqlDelete);
        statement.bindLong(1,tagId);
        statement.executeUpdateDelete();

        for(int n=0;n<activityLinearLayout.getChildCount();n++) {
            
        }
//
//            }


    }
}

