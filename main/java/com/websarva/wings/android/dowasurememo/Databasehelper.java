package com.websarva.wings.android.dowasurememo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Databasehelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME="zibunmemo";
    private static int DATABASE_VERSION=6;

    public Databasehelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder builder=new StringBuilder();
        builder.append
                ("CREATE TABLE zibunmemo " +
                        "(_id INTEGER,category TEXT,memo TEXT)");
        String sqlcreate=builder.toString();
        db.execSQL(sqlcreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion > oldVersion) {
            //db.execSQL("ALTER TABLE zibunmemo ADD bodypart TEXT");
            db.execSQL("ALTER TABLE zibunmemo ADD records TEXT");
            //db.execSQL("ALTER TABLE zibunmemo ADD unit TEXT");
        }
    }
}
