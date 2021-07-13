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

    Databasehelper _helper;

    private String _category = "size";

    private EditText et_bodypart;
    private EditText et_record;
    private EditText et_unit;
    private EditText editText;
    String str;
    Context context;

    String st;


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
//        int id= et_bodypart.getId();
//        Log.d("main",""+id);
//        String str = et_bodypart.getTag().toString();
//        Log.d("maind",""+str);

        //int tagId=Integer.valueOf(str);

        _helper=new Databasehelper(context);
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlUpdate;
        SQLiteStatement statement;

        String str = editText.getTag().toString();
        int tag = Integer.valueOf(str);



        switch (editText.getId()){
            case R.id.et_bodypart:
                int id= editText.getId();
                Log.d("main",""+id);


                db=_helper.getWritableDatabase();
                sqlUpdate = "UPDATE zibunmemo SET bodypart = ? WHERE _id = ?";
                statement=db.compileStatement(sqlUpdate);
                statement.bindString(1,s.toString());
                statement.bindLong(2,tag);
                statement.executeUpdateDelete();
                break;

            case R.id.et_record:
                id= editText.getId();
                Log.d("main",""+id);

                st=s.toString();
//                if(st==null || st==""){
//                    st="0";
//                }
//                Log.d("maind",""+st);



                db=_helper.getWritableDatabase();
                sqlUpdate = "UPDATE zibunmemo SET record = ? WHERE _id = ?";
                statement=db.compileStatement(sqlUpdate);
                try {
                    statement.bindLong(1, Long.parseLong(st));
                } catch (NumberFormatException e) {
                    st = "0";
                    statement.bindLong(1, Long.parseLong(st));
                }
                statement.bindLong(2,tag);
                statement.executeUpdateDelete();
                break;

            case R.id.et_unit:
                id= editText.getId();
                Log.d("main",""+id);

                db=_helper.getWritableDatabase();
                sqlUpdate = "UPDATE zibunmemo SET unit = ? WHERE _id = ?";
                statement=db.compileStatement(sqlUpdate);
                statement.bindString(1,s.toString());
                statement.bindLong(2,tag);
                statement.executeUpdateDelete();
                break;
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