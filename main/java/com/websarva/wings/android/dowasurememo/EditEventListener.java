package com.websarva.wings.android.dowasurememo;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class EditEventListener implements TextWatcher {

    private EditText editText;
    Context context;

    public EditEventListener(EditText e, Context c){
        editText=e;
        context=c;
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
    @Override
    public void afterTextChanged(Editable s) {
        Databasehelper _helper=new Databasehelper(context);
        SQLiteDatabase db;
        String sqlUpdate;
        SQLiteStatement statement;

//        String str = editText.getTag().toString();
//        int tagId = Integer.valueOf(str);

        switch (editText.getId()){
//            case R.id.et_bodypart:
//
//                db=_helper.getWritableDatabase();
//                sqlUpdate = "UPDATE zibunmemo SET bodypart = ? WHERE _id = ?";
//                statement=db.compileStatement(sqlUpdate);
//                statement.bindString(1,s.toString());
//                statement.bindLong(2,tagId);
//                statement.executeUpdateDelete();
//                break;
//
//
//            case R.id.et_record:
//
//                db=_helper.getWritableDatabase();
//                sqlUpdate = "UPDATE zibunmemo SET records = ? WHERE _id = ?";
//                statement=db.compileStatement(sqlUpdate);
//                statement.bindString(1,s.toString());
//                statement.bindLong(2,tagId);
//                statement.executeUpdateDelete();
//                break;
//
//
//            case R.id.et_unit:
//
//                db=_helper.getWritableDatabase();
//                sqlUpdate = "UPDATE zibunmemo SET unit = ? WHERE _id = ?";
//                statement=db.compileStatement(sqlUpdate);
//                statement.bindString(1,s.toString());
//                statement.bindLong(2,tagId);
//                statement.executeUpdateDelete();
//                break;

            case R.id.et_date_title:
                Log.d("main","datetitle");
                break;

            case R.id.et_date_year:
                Log.d("main","dateyear");
                break;

            case R.id.et_date_month:
                Log.d("main","datemonth");
                break;

            case R.id.et_date_day:
                Log.d("main","dateday");
                break;

            case R.id.et_address_title:
                Log.d("main","addresstitle");
                break;

            case R.id.et_postnumber1:
                Log.d("main","post1");
                break;

            case R.id.et_postnumber2:
                Log.d("main","post2");
                break;

            case R.id.et_addres_detail:
                Log.d("main","addressdetail");
                break;

            case R.id.et_car_name:
                Log.d("main","carname");
                break;

            case R.id.et_car_memo_title:
                Log.d("main","cartitle");
                break;

            case R.id.et_car_memo_contents:
                Log.d("main","carcontents");
                break;

            case R.id.et_update_title:
                Log.d("main","updatetitle");
                break;

            case R.id.et_update_year:
                Log.d("main","year");
                break;

            case R.id.et_update_month:
                Log.d("main","month");
                break;

            case R.id.et_update_day:
                Log.d("main","day");
                break;

            case R.id.et_password_title:
                Log.d("main","passtitle");
                break;

            case R.id.et_password_contents:
                Log.d("main","passcontents");
                break;

            case R.id.et_subsc_title:
                Log.d("main","subsctitle");
                break;

            case R.id.et_subsc_price:
                Log.d("main","subscprice");
                break;

            case R.id.et_wishlist_title:
                Log.d("main","wishlisttitle");
                break;

            case R.id.et_memo_title:
                Log.d("main","memotitle");
                break;

            case R.id.et_memo_contents:
                Log.d("main","memocontents");
                break;
        }
    }
}

//        _helper=new Databasehelper(context);
//        SQLiteDatabase db=_helper.getWritableDatabase();
//        String sqlUpdate = "UPDATE zibunmemo SET"
//        SQLiteStatement statement=db.compileStatement(sqlDelete);
//        statement.bindLong(1,tagId);
//        statement.executeUpdateDelete();
//
//        String sqlInsert="INSERT INTO zibunmemo(_id,category,part,record,unit) VALUES(?,?,?,?,?)";
//        statement=db.compileStatement(sqlInsert);
//        statement.bindLong(1,tagId);
//        statement.bindString(2,_category);
//        statement.bindString(3,s.toString());
//        statement.executeInsert();

        //String str=et_bodypart.getTag().toString();
        //Log.d("maina",""+et_bodypart.getTag().toString());







//    public EditEventListener(EditText e1,EditText e2,EditText e3){
//        et_bodypart=e1;
//        et_record=e2;
//        et_unit=e3;
//    }


//        str=et_bodypart.getText().toString();
//        Log.d("ma",""+str);
//            lvId=sizeList.indexOf(sizeMap);
//
//
////            switch (){
////                case et_bodypart:
////                    strBodyPart=s.toString();
////                    break;
////                case et_record:
////                    strRecord=s.toString();
////                    break;
////                case et_unit:
////                    strUnit=s.toString();
////                    break;
////
////            }
////            Log.d("main",""+strBodyPart);
//            strBodyPart=s.toString();
//            Log.d("main",""+strBodyPart);