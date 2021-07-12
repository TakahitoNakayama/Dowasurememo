package com.websarva.wings.android.dowasurememo;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class EditEventListener implements TextWatcher {

    private DatabaseSQLite _helper;
    private String _category = "size";

    private EditText et_bodypart;
    private EditText et_record;
    private EditText et_unit;
    String str;

    class BpEventListener implements TextWatcher{
        public BpEventListener(EditText e){
            et_bodypart=e;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            int id= et_bodypart.getId();
            Log.d("main",""+s);
            String str = et_bodypart.getTag().toString();
            Log.d("maind",""+str);

            int tagId=Integer.valueOf(str);

            SQLiteDatabase db=_helper.getWritableDatabase();
            String sqlDelete="DELETE bodypart FROM zibunmemo WHERE _id = ?";
            SQLiteStatement statement=db.compileStatement(sqlDelete);
            statement.bindLong(1,tagId);
            statement.executeUpdateDelete();

            String sqlInsert="INSERT INTO zibunmemo(_id,category,bodypart,record,unit) VALUES(?,?,?,?,?)";
            statement=db.compileStatement(sqlInsert);
            statement.bindLong(1,tagId);
            statement.bindString(2,_category);
            statement.bindString(3,s.toString());
            statement.executeInsert();

            //String str=et_bodypart.getTag().toString();
            //Log.d("maina",""+et_bodypart.getTag().toString());
        }
    }

    }




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