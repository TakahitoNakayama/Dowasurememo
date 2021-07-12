package com.websarva.wings.android.dowasurememo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseSQLite extends SQLiteOpenHelper {

    private static String DATABASE_NAME="zibunmemo";
    private static int DATABASE_VERSION=1;

    public DatabaseSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder builder=new StringBuilder();
        builder.append
                ("CREATE TABLE zibunmemo " +
                        "(_id INTEGER,category TEXT,bodypart TEXT,record TEXT,unit TEXT,memo TEXT)");
        String sqlcreate=builder.toString();
        db.execSQL(sqlcreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

