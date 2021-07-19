package com.websarva.wings.android.dowasurememo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Databasehelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME="zibunmemo";
    private static int DATABASE_VERSION=7;

    public Databasehelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder builder=new StringBuilder();
        builder.append
                ("CREATE TABLE zibunmemo " +
                        "(_id INTEGER,category TEXT,bodypart TEXT,records TEXT" +
                        ",unit TEXT,memo TEXT)");
        String sqlcreate=builder.toString();
        db.execSQL(sqlcreate);

        builder.append
                ("CREATE TABLE date " +
                        "(_id INTEGER,category TEXT,datetitle TEXT," +
                        "dateyear TEXT,datemonth TEXT,dateday TEXT,memo TEXT)");
        String sqldatecreate=builder.toString();
        db.execSQL(sqldatecreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion > oldVersion) {
            //db.execSQL("ALTER TABLE zibunmemo ADD bodypart TEXT");
            //db.execSQL("ALTER TABLE zibunmemo ADD records TEXT");
            //db.execSQL("ALTER TABLE zibunmemo ADD unit TEXT");
            StringBuilder builder=new StringBuilder();
            builder.append
                    ("CREATE TABLE date " +
                            "(_id INTEGER,category TEXT,datetitle TEXT," +
                            "dateyear TEXT,datemonth TEXT,dateday TEXT,memo TEXT)");
            String sqldatecreate=builder.toString();
            db.execSQL(sqldatecreate);
        }
    }
}
