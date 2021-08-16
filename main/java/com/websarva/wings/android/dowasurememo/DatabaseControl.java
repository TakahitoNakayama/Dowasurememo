package com.websarva.wings.android.dowasurememo;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

//データベースの削除やインサートを行うクラス
public class DatabaseControl {

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

    //コンストラクタ
    public DatabaseControl(Context c,String ta) {
        context=c;
        table=ta;
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

//    public DatabaseControl(LinearLayout _llBaseLayout,LinearLayout _llAddLayout){
//        llBaseLayout=_llBaseLayout;
//        llAddLayout=_llAddLayout;
//    }

    public void DatabaseSelect(LinearLayout _llBaseLayout,LinearLayout _llAddLayout) {
        llBaseLayout=_llBaseLayout;
        llAddLayout=_llAddLayout;


        _helper = new Databasehelper(context);
        SQLiteDatabase db = _helper.getWritableDatabase();
        String sqlSelect = "SELECT * FROM "+table+"";
        Cursor cursor = db.rawQuery(sqlSelect, null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int i = cursor.getColumnIndex("_id");
            tagId = cursor.getInt(i);
            inflater= LayoutInflater.from(context);
            //llBaseLayout=findViewById(R.id.ll_address_layout);
            llAddLayout= (LinearLayout) inflater.inflate(R.layout.address_inputform,null);
            llBaseLayout.addView(llAddLayout);

            LinearLayout llAddressFrame=llAddLayout.findViewById(R.id.ll_address_frame);
            LinearLayout llPostNumberinputform=llAddLayout.findViewById(R.id.ll_postnumber_inputform);

            EditText etAddressTitle = llAddressFrame.findViewById(R.id.et_address_title);
            EditText etPostNumber1 = llPostNumberinputform.findViewById(R.id.et_postnumber1);
            EditText etPostNumber2 = llPostNumberinputform.findViewById(R.id.et_postnumber2);
            EditText etAddressDetail = llAddressFrame.findViewById(R.id.et_addres_detail);
            ImageButton btDelete = llPostNumberinputform.findViewById(R.id.bt_delete);
            btDelete.setOnClickListener
                    (new DeleteButton(context, llBaseLayout, llAddLayout, table));

            i = cursor.getColumnIndex("addresstitle");
            String strAddressTitle = cursor.getString(i);

            i = cursor.getColumnIndex("postnumber1");
            String strPostNumber1 = cursor.getString(i);

            i = cursor.getColumnIndex("postnumber2");
            String strPostNumber2 = cursor.getString(i);

            i = cursor.getColumnIndex("addressdetail");
            String strAddressDetail = cursor.getString(i);

            try {
                etAddressTitle.setText(strAddressTitle);
            } catch (NullPointerException e) {
                strAddressTitle = "";
            }

            try {
                etPostNumber1.setText(strPostNumber1);
            } catch (NullPointerException e) {
                strPostNumber1 = "";
            }

            try {
                etPostNumber2.setText(strPostNumber2);
            } catch (NullPointerException e) {
                strPostNumber2 = "";
            }

            try {
                etAddressDetail.setText(strAddressDetail);
            } catch (NullPointerException e) {
                strAddressDetail = "";
            }
        }
    }




    public void DatabaseDelete(int tagId){
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlDelete="DELETE FROM "+table+" WHERE _id = ?";
        SQLiteStatement statement=db.compileStatement(sqlDelete);
        statement.bindLong(1,tagId);
        statement.executeUpdateDelete();
    }

    public void DatabaseAllDelete(){
        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlDelete="DELETE FROM "+table+"";
        SQLiteStatement statement=db.compileStatement(sqlDelete);
        statement.executeUpdateDelete();
    }

    public void DatabaseInsertFourColumns(String column1,String column2,String column3,String column4){
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


    public void DatabaseInsertThreeColumns(String column1,String column2,String column3){
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

    public void DatabaseInsertTwoColumns(String column1,String column2){
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

    public void DatabaseInsertOneColumns(String column1){
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
