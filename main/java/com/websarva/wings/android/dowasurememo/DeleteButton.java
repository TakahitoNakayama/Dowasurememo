package com.websarva.wings.android.dowasurememo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

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
        //activityLinearLayout.removeView(linearLayout);
        Log.d("main35",""+tagId);
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlDelete="DELETE FROM "+table+" WHERE tag = ?";
        SQLiteStatement statement=db.compileStatement(sqlDelete);
        statement.bindLong(1,tagId);
        statement.executeUpdateDelete();

        ArrayList<Integer> tagIdList=new ArrayList<>();
        for(int t=1;t<=activityLinearLayout.getChildCount();t++) {
            //Log.d("main",""+activityLinearLayout.getChildCount());
            tagIdList.add(t);
        }
        tagIdList.remove(tagId-1);
        activityLinearLayout.removeView(linearLayout);
        for(int n=0;n<activityLinearLayout.getChildCount();n++) {

            int oldId=tagIdList.get(n);
            Log.d("main53",""+oldId);
            DatabaseControl control=new DatabaseControl(context,table);
            control.IdAllChangeUpdate(n+1,oldId);
        }



    }
}

