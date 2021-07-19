package com.websarva.wings.android.dowasurememo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class DatabaseControl {

    Context context;
    Databasehelper _helper;
    String table;
    int tagId;
    String _category;
    String str;

    public DatabaseControl(Context c,String ta) {
        context=c;
        table=ta;
    }

    public DatabaseControl(Context c,String ta,int tagid,String category,String st){
        context=c;
        table=ta;
        tagId=tagid;
        _category=category;
        str=st;
    }


    public void DatabaseDelete(int tagId){
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlDelete="DELETE FROM "+table+" WHERE _id = ?";
        SQLiteStatement statement=db.compileStatement(sqlDelete);
        //statement.bindString(1,table);
        statement.bindLong(1,tagId);
        statement.executeUpdateDelete();
    }

    public void DatabaseInsert(String column1,String column2,String column3,String column4){
        String sqlInsert=
                "INSERT INTO "+table+" " +
                        "(_id,category,"+column1+","+column2+","+column3+","+column4+") " +
                        "VALUES(?,?,?,?,?,?)";
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        SQLiteStatement statement=db.compileStatement(sqlInsert);
        statement.bindLong(1,tagId);
        statement.bindString(2,_category);
        statement.bindString(3,str);
        statement.bindString(4,str);
        statement.bindString(5,str);
        statement.bindString(6,str);
        statement.executeInsert();
    }

}
