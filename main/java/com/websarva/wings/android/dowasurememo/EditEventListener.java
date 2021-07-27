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

        String str = editText.getTag().toString();
        int tagId = Integer.valueOf(str);
        String text=s.toString();

        String table;
        String column;
        DatabaseControl control;

        switch (editText.getId()){
            case R.id.et_bodypart:

                db=_helper.getWritableDatabase();
                sqlUpdate = "UPDATE zibunmemo SET bodypart = ? WHERE _id = ?";
                statement=db.compileStatement(sqlUpdate);
                statement.bindString(1,s.toString());
                statement.bindLong(2,tagId);
                statement.executeUpdateDelete();
                break;


            case R.id.et_record:

                db=_helper.getWritableDatabase();
                sqlUpdate = "UPDATE zibunmemo SET records = ? WHERE _id = ?";
                statement=db.compileStatement(sqlUpdate);
                statement.bindString(1,s.toString());
                statement.bindLong(2,tagId);
                statement.executeUpdateDelete();
                break;


            case R.id.et_unit:

                db=_helper.getWritableDatabase();
                sqlUpdate = "UPDATE zibunmemo SET unit = ? WHERE _id = ?";
                statement=db.compileStatement(sqlUpdate);
                statement.bindString(1,s.toString());
                statement.bindLong(2,tagId);
                statement.executeUpdateDelete();
                break;

            case R.id.et_date_title:
                table="date";
                column="datetitle";
                control=new DatabaseControl(context,table);
                control.TextChangeUpdate(column,text,tagId);
                break;

            case R.id.et_date_year:
                table="date";
                column="dateyear";
                control=new DatabaseControl(context,table);
                control.TextChangeUpdate(column,text,tagId);
                break;

            case R.id.et_date_month:
                table="date";
                column="datemonth";
                control=new DatabaseControl(context,table);
                control.TextChangeUpdate(column,text,tagId);
                break;

            case R.id.et_date_day:
                table="date";
                column="dateday";
                control=new DatabaseControl(context,table);
                control.TextChangeUpdate(column,text,tagId);
                break;

            case R.id.et_address_title:
                table="address";
                column="addresstitle";
                control=new DatabaseControl(context,table);
                control.TextChangeUpdate(column,text,tagId);
                break;

            case R.id.et_postnumber1:
                table="address";
                column="postnumber1";
                control=new DatabaseControl(context,table);
                control.TextChangeUpdate(column,text,tagId);
                break;

            case R.id.et_postnumber2:
                table="address";
                column="postnumber2";
                control=new DatabaseControl(context,table);
                control.TextChangeUpdate(column,text,tagId);
                break;

            case R.id.et_addres_detail:
                table="address";
                column="addressdetail";
                control=new DatabaseControl(context,table);
                control.TextChangeUpdate(column,text,tagId);
                break;

            case R.id.et_car_name:
                table="car";
                column="carname";
                control=new DatabaseControl(context,table);
                control.TextChangeUpdate(column,text,tagId);
                break;

            case R.id.et_car_memo_title:
                table="car";
                column="carmemotitle";
                control=new DatabaseControl(context,table);
                control.TextChangeUpdate(column,text,tagId);
                break;

            case R.id.et_car_memo_contents:
                table="car";
                column="carmemocontents";
                control=new DatabaseControl(context,table);
                control.TextChangeUpdate(column,text,tagId);
                break;

            case R.id.et_update_title:
                table="update1";
                column="updatetitle";
                control=new DatabaseControl(context,table);
                control.TextChangeUpdate(column,text,tagId);
                break;

            case R.id.et_update_year:
                Log.d("main","year");
                table="update1";
                column="updateyear";
                control=new DatabaseControl(context,table);
                control.TextChangeUpdate(column,text,tagId);
                break;

            case R.id.et_update_month:
                Log.d("main","month");
                table="update1";
                column="updatemonth";
                control=new DatabaseControl(context,table);
                control.TextChangeUpdate(column,text,tagId);
                break;

            case R.id.et_update_day:
                Log.d("main","day");
                table="update1";
                column="updateday";
                control=new DatabaseControl(context,table);
                control.TextChangeUpdate(column,text,tagId);
                break;

            case R.id.et_password_title:
                table="password";
                column="passwordtitle";
                control=new DatabaseControl(context,table);
                control.TextChangeUpdate(column,text,tagId);
                break;

            case R.id.et_password_contents:
                table="password";
                column="passwordcontents";
                control=new DatabaseControl(context,table);
                control.TextChangeUpdate(column,text,tagId);
                break;

            case R.id.et_subsc_title:
                table="subsc";
                column="subsctitle";
                control=new DatabaseControl(context,table);
                control.TextChangeUpdate(column,text,tagId);
                break;

            case R.id.et_subsc_price:
                table="subsc";
                column="subscprice";
                control=new DatabaseControl(context,table);
                control.TextChangeUpdate(column,text,tagId);

//                MonthPayment payment=new MonthPayment();
//                payment.culcMonthPayment();
                break;

            case R.id.et_wishlist_title:
                table="wishlist";
                column="wishlisttitle";
                control=new DatabaseControl(context,table);
                control.TextChangeUpdate(column,text,tagId);
                break;

            case R.id.et_memo_title:
                table="memo";
                column="memotitle";
                control=new DatabaseControl(context,table);
                control.TextChangeUpdate(column,text,tagId);
                break;

            case R.id.et_memo_contents:
                table="memo";
                column="memocontents";
                control=new DatabaseControl(context,table);
                control.TextChangeUpdate(column,text,tagId);
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