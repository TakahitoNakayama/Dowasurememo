package com.websarva.wings.android.dowasurememo;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class DatabaseControl {

    Context context;
    Databasehelper _helper;
    String table;
    int tagId;
    String _category;
    String str;
    String inputform;

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

    public DatabaseControl(Context c,String ta,int tagid,String category,String st,String input){
        context=c;
        table=ta;
        tagId=tagid;
        _category=category;
        str=st;
        inputform=input;
    }

    public void DatabaseDelete(int tagId){
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlDelete="DELETE FROM "+table+" WHERE tag = ?";
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

    public void DatabaseInsertCar(String column1,String column2,String column3){
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
        statement.bindString(4,str);
        statement.bindString(5,inputform);
        statement.executeInsert();
    }

    public void DatabaseInsertSubsc(String column1,String column2,String column3){
        String sqlInsert=
                "INSERT INTO "+table+" " +
                        "(tag,category,"+column1+","+column2+","+column3+") " +
                        "VALUES(?,?,?,?,?)";
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        SQLiteStatement statement=db.compileStatement(sqlInsert);
        statement.bindLong(1,tagId);
        statement.bindString(2,_category);
        statement.bindString(3,str);
        statement.bindString(4,str);
        statement.bindString(5,inputform);
        statement.executeInsert();
    }


    public void DatabaseInsert(String column1,String column2){
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
        statement.bindString(4,str);
        statement.executeInsert();
    }

    public void DatabaseInsertCar(String column1,String column2){
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
        statement.bindString(4,inputform);
        statement.executeInsert();
    }

    public void DatabaseInsert(String column1){
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



    public void IndexCounterUpdate(int index){
        String sqlCount = "UPDATE "+table+" SET memo = ? ";
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        SQLiteStatement statement=db.compileStatement(sqlCount);
        statement.bindString(1,String.valueOf(index));
        statement.executeUpdateDelete();
    }

    public void SpinnerIndexUpdate(String index,int tagId){
        String sqlCount = "UPDATE "+table+" SET subscinterbal = ? WHERE tag = ?";
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        SQLiteStatement statement=db.compileStatement(sqlCount);
        statement.bindString(1,index);
        statement.bindLong(2,tagId);
        statement.executeUpdateDelete();
    }

    public int GetIndexCounter(){
        int num;
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlIndex="SELECT memo FROM "+table+"";
        Cursor cursor =db.rawQuery(sqlIndex,null);
        cursor.moveToFirst();
        int i=cursor.getColumnIndex("memo");
        try {
            num=Integer.valueOf(cursor.getString(i));
        } catch (NumberFormatException e) {
            num=1;
        }catch (CursorIndexOutOfBoundsException s) {
            num=1;
        }
        return num;
    }

    public void TextChangeUpdate(String column,String text,int tagid){
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlUpdate = "UPDATE "+table+" SET "+column+" = ? WHERE tag = ?";
        SQLiteStatement statement=db.compileStatement(sqlUpdate);
        statement.bindString(1,text);
        statement.bindLong(2,tagid);
        statement.executeUpdateDelete();
    }

    public void IdChangeUpdate(int newtag,int oldtag){
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlUpdate = "UPDATE "+table+" SET _id = ? WHERE _id = ?";
        SQLiteStatement statement=db.compileStatement(sqlUpdate);
        statement.bindLong(1,newtag);
        statement.bindLong(2,oldtag);
        statement.executeUpdateDelete();
    }

    public void IdAllChangeUpdate(int newid,int oldid){
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlUpdate = "UPDATE subsc SET tag = ? WHERE tag = ?";
        SQLiteStatement statement=db.compileStatement(sqlUpdate);
        statement.bindLong(1,newid);
        statement.bindLong(2,oldid);
        statement.executeUpdateDelete();
    }

}
