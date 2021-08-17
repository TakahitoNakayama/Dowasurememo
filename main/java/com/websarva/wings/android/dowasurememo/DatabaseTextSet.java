package com.websarva.wings.android.dowasurememo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class DatabaseTextSet {

    public EditText[] viewIdSetter
            (Context context,String table,LinearLayout llBaseLayout,LinearLayout llAddLayout){

        LinearLayout llAddressFrame=llAddLayout.findViewById(R.id.ll_address_frame);
        LinearLayout llPostNumberinputform=llAddLayout.findViewById(R.id.ll_postnumber_inputform);

        EditText etAddressTitle = llAddressFrame.findViewById(R.id.et_address_title);
        EditText etPostNumber1 = llPostNumberinputform.findViewById(R.id.et_postnumber1);
        EditText etPostNumber2 = llPostNumberinputform.findViewById(R.id.et_postnumber2);
        EditText etAddressDetail = llAddressFrame.findViewById(R.id.et_addres_detail);
        ImageButton btDelete = llPostNumberinputform.findViewById(R.id.bt_delete);
        btDelete.setOnClickListener
                (new DeleteButton(context, llBaseLayout, llAddLayout,table));

        EditText[] editTexts={etAddressTitle,etPostNumber1,etPostNumber2,etAddressDetail};
        return editTexts;
    }

    public void textSetter(Cursor cursor, String[] columnNames, EditText[] editTexts){

        for(int index=0;index<columnNames.length;index++){
            int i = cursor.getColumnIndex(""+columnNames[index]+"");
            String str = cursor.getString(i);

            try {
                editTexts[index].setText(str);
            } catch (NullPointerException e) {
                str = "";
            }
        }

    }





}
