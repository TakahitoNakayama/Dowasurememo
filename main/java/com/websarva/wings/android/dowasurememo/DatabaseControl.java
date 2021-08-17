package com.websarva.wings.android.dowasurememo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

//データベースの削除やインサートを行うクラス
public class DatabaseControl extends DatabaseTextSet {

    Context context;
    Databasehelper _helper;
    String table;
    int tagId;
    String _category;
    String str;
    String str2;
    String str3;
    String str4;
    LinearLayout llBaseLayout;
    LinearLayout llAddLayout;
    LayoutInflater inflater;
    String[] columnNames;
    EditText[] editTexts;

    //コンストラクタ
    public DatabaseControl(Context c,String ta) {
        context=c;
        table=ta;
    }

    //コンストラクタ
    public DatabaseControl(Context c,String ta,String[] _columnNames) {
        context=c;
        table=ta;
        columnNames=_columnNames;
    }

    //コンストラクタ
    public DatabaseControl(Context c,String ta,int tagid,String category,String st){
        context=c;
        table=ta;
        tagId=tagid;
        _category=category;
        str=st;
    }

    //コンストラクタ
    public DatabaseControl(Context c,String ta,int tagid,String category,String st,String st2){
        context=c;
        table=ta;
        tagId=tagid;
        _category=category;
        str=st;
        str2=st2;
    }

    //コンストラクタ
    public DatabaseControl(Context c,String ta,int tagid,String category,String st,String st2,String st3){
        context=c;
        table=ta;
        tagId=tagid;
        _category=category;
        str=st;
        str2=st2;
        str3=st3;
    }

    //コンストラクタ
    public DatabaseControl(Context c,String ta,int tagid,String category,String st,String st2,String st3,String st4){
        context=c;
        table=ta;
        tagId=tagid;
        _category=category;
        str=st;
        str2=st2;
        str3=st3;
        str4=st4;
    }


    public void selectDatabase(LinearLayout _llBaseLayout,LinearLayout _llAddLayout) {
        llBaseLayout=_llBaseLayout;
        llAddLayout=_llAddLayout;

        _helper = new Databasehelper(context);
        SQLiteDatabase db = _helper.getWritableDatabase();
        String sqlSelect = "SELECT * FROM "+table+"";
        Cursor cursor = db.rawQuery(sqlSelect, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            inflater = LayoutInflater.from(context);
            llAddLayout = (LinearLayout) inflater.inflate(R.layout.address_inputform, null);
            llBaseLayout.addView(llAddLayout);

            editTexts = setViewIdAddress(context, table, llBaseLayout, llAddLayout);

            setDatabaseText(cursor, columnNames, editTexts);
        }
    }

    public void deleteDatabase(int tagId){
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlDelete="DELETE FROM "+table+" WHERE _id = ?";
        SQLiteStatement statement=db.compileStatement(sqlDelete);
        statement.bindLong(1,tagId);
        statement.executeUpdateDelete();
    }

    public void allDeleteDatabase(){
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlDelete="DELETE FROM "+table+"";
        SQLiteStatement statement=db.compileStatement(sqlDelete);
        statement.executeUpdateDelete();
    }

    public void insertDatabaseFourColumns(String column1, String column2, String column3, String column4){
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
        statement.bindString(4,str2);
        statement.bindString(5,str3);
        statement.bindString(6,str4);
        statement.executeInsert();
    }


    public void insertDatabaseThreeColumns(String column1, String column2, String column3){
        String sqlInsert=
                "INSERT INTO "+table+" " +
                        "(_id,category,"+column1+","+column2+","+column3+") " +
                        "VALUES(?,?,?,?,?)";
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        SQLiteStatement statement=db.compileStatement(sqlInsert);
        statement.bindLong(1,tagId);
        statement.bindString(2,_category);
        statement.bindString(3,str);
        statement.bindString(4,str2);
        statement.bindString(5,str3);
        statement.executeInsert();
    }

    public void insertDatabaseTwoColumns(String column1, String column2){
        String sqlInsert=
                "INSERT INTO "+table+" " +
                        "(_id,category,"+column1+","+column2+") " +
                        "VALUES(?,?,?,?)";
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        SQLiteStatement statement=db.compileStatement(sqlInsert);
        statement.bindLong(1,tagId);
        statement.bindString(2,_category);
        statement.bindString(3,str);
        statement.bindString(4,str2);
        statement.executeInsert();
    }

    public void insertDatabaseOneColumns(String column1){
        String sqlInsert=
                "INSERT INTO "+table+" " +
                        "(_id,category,"+column1+") " +
                        "VALUES(?,?,?)";
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        SQLiteStatement statement=db.compileStatement(sqlInsert);
        statement.bindLong(1,tagId);
        statement.bindString(2,_category);
        statement.bindString(3,str);
        statement.executeInsert();
    }

}
