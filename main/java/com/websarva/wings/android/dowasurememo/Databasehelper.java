package com.websarva.wings.android.dowasurememo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Databasehelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME="zibunmemo";
    private static int DATABASE_VERSION=23;

    public Databasehelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder buildersize = new StringBuilder();
        buildersize.append
            ("CREATE TABLE size " +
                    "(_id INTEGER,category TEXT,bodypart TEXT,records TEXT" +
                    ",unit TEXT,memo TEXT)");
        String sqlcreate=buildersize.toString();
        db.execSQL(sqlcreate);

        StringBuilder builderdate = new StringBuilder();
        builderdate.append
                ("CREATE TABLE date1 " +
                        "(_id INTEGER,category TEXT,datetitle TEXT," +
                        "dateyear TEXT,datemonth TEXT,dateday TEXT,memo TEXT)");
        String sqldatecreate=builderdate.toString();
        db.execSQL(sqldatecreate);

        StringBuilder builderaddress = new StringBuilder();
        builderaddress.append
                ("CREATE TABLE address " +
                        "(_id INTEGER,category TEXT,addresstitle TEXT," +
                        "postnumber1 TEXT,postnumber2 TEXT,addressdetail TEXT,memo TEXT)");
        String sqladdresscreate=builderaddress.toString();
        db.execSQL(sqladdresscreate);


        StringBuilder buildercar = new StringBuilder();
        buildercar.append
                ("CREATE TABLE car " +
                        "(_id INTEGER,category TEXT,carname TEXT," +
                        "carmemotitle TEXT,carmemocontents TEXT,inputform TEXT,memo TEXT)");
        String sqlcarcreate=buildercar.toString();
        db.execSQL(sqlcarcreate);

        StringBuilder builderupdate = new StringBuilder();
        builderupdate.append
                ("CREATE TABLE update1 " +
                        "(_id INTEGER,category TEXT,updatetitle TEXT," +
                        "updateyear TEXT,updatemonth TEXT,updateday TEXT,memo TEXT)");
        String sqlupdatecreate=builderupdate.toString();
        db.execSQL(sqlupdatecreate);

        StringBuilder builderpassword = new StringBuilder();
        builderpassword.append
                ("CREATE TABLE password " +
                        "(_id INTEGER,category TEXT,passwordtitle TEXT," +
                        "passwordcontents TEXT,memo TEXT)");
        String sqlpasswordcreate=builderpassword.toString();
        db.execSQL(sqlpasswordcreate);

        StringBuilder buildersubsc = new StringBuilder();
        buildersubsc.append
                ("CREATE TABLE subsc " +
                        "(_id INTEGER,category TEXT,subsctitle TEXT,subscprice TEXT,subscinterbal TEXT,memo TEXT)");
        String sqlsubsccreate=buildersubsc.toString();
        db.execSQL(sqlsubsccreate);


        StringBuilder builderwishlist = new StringBuilder();
        builderwishlist.append
                ("CREATE TABLE wishlist " +
                        "(_id INTEGER,category TEXT,wishlisttitle TEXT," +
                        "memo TEXT)");
        String sqlwishlistcreate=builderwishlist.toString();
        db.execSQL(sqlwishlistcreate);

        StringBuilder buildermemo = new StringBuilder();
        buildermemo.append
                ("CREATE TABLE memo " +
                        "(_id INTEGER,category TEXT,memotitle TEXT," +
                        "memocontents TEXT,memo TEXT)");
        String sqlmemocreate=buildermemo.toString();
        db.execSQL(sqlmemocreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (newVersion > oldVersion) {
//            db.execSQL("ALTER TABLE zibunmemo ADD bodypart TEXT");
//            db.execSQL("ALTER TABLE zibunmemo ADD records TEXT");
//            db.execSQL("ALTER TABLE zibunmemo ADD unit TEXT");

//            db.execSQL("ALTER TABLE subsc ADD id  ");


//            StringBuilder buildersize = new StringBuilder();
//            buildersize.append
//                ("CREATE TABLE size " +
//                        "(_id INTEGER,category TEXT,bodypart TEXT,records TEXT" +
//                        ",unit TEXT,memo TEXT)");
//            String sqlcreate=buildersize.toString();
//            db.execSQL(sqlcreate);
//
//            StringBuilder builderdate = new StringBuilder();
//            builderdate.append
//                    ("CREATE TABLE date1 " +
//                            "(_id INTEGER,category TEXT,datetitle TEXT," +
//                            "dateyear TEXT,datemonth TEXT,dateday TEXT,memo TEXT)");
//            String sqldatecreate=builderdate.toString();
//            db.execSQL(sqldatecreate);

//            builder.append
//                    ("CREATE TABLE address " +
//                            "(_id INTEGER,category TEXT,addresstitle TEXT," +
//                            "postnumber1 TEXT,postnumber2 TEXT,addressdetail TEXT,memo TEXT)");
//            String sqladdresscreate=builder.toString();
//            db.execSQL(sqladdresscreate);
//
//            StringBuilder builder1 = new StringBuilder();
//            builder1.append
//                    ("CREATE TABLE car " +
//                            "(_id INTEGER,category TEXT,carname TEXT," +
//                            "carmemotitle TEXT,carmemocontents TEXT,inputform TEXT,memo TEXT)");
//            String sqlcarcreate=builder1.toString();
//            db.execSQL(sqlcarcreate);
//
//            StringBuilder builder2 = new StringBuilder();
//            builder2.append
//                    ("CREATE TABLE update1 " +
//                            "(_id INTEGER,category TEXT,updatetitle TEXT," +
//                            "updateyear TEXT,updatemonth TEXT,updateday TEXT,memo TEXT)");
//            String sqlupdatecreate=builder2.toString();
//            db.execSQL(sqlupdatecreate);
//
//            StringBuilder builder3 = new StringBuilder();
//            builder3.append
//                    ("CREATE TABLE password " +
//                            "(_id INTEGER,category TEXT,passwordtitle TEXT," +
//                            "passwordcontents TEXT,memo TEXT)");
//            String sqlpasswordcreate=builder3.toString();
//            db.execSQL(sqlpasswordcreate);
//
//            StringBuilder builder4 = new StringBuilder();
//            builder4.append
//                    ("CREATE TABLE subsc " +
//                            "(_id INTEGER,category TEXT,subsctitle TEXT," +
//                            "subscprice TEXT,memo TEXT)");
//            String sqlsubsccreate=builder4.toString();
//            db.execSQL(sqlsubsccreate);
//
//            db.execSQL("ALTER TABLE subsc ADD subscinterbal TEXT");
//            db.execSQL("ALTER TABLE subsc ADD tag INTEGER");
//
//            StringBuilder builder5 = new StringBuilder();
//            builder5.append
//                    ("CREATE TABLE wishlist " +
//                            "(_id INTEGER,category TEXT,wishlisttitle TEXT," +
//                            "memo TEXT)");
//            String sqlwishlistcreate=builder5.toString();
//            db.execSQL(sqlwishlistcreate);
//
//            StringBuilder builder6 = new StringBuilder();
//            builder6.append
//                    ("CREATE TABLE memo " +
//                            "(_id INTEGER,category TEXT,memotitle TEXT," +
//                            "memocontents TEXT,memo TEXT)");
//            String sqlmemocreate=builder6.toString();
//            db.execSQL(sqlmemocreate);

        }
    }
}
