package com.websarva.wings.android.dowasurememo;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

public class SpinnerListener implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    Context context;
    String table="subsc";

    public SpinnerListener(Context c,Spinner s){
        context=c;
        spinner=s;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int tagId= (int) spinner.getTag();
        DatabaseControl control=new DatabaseControl(context,table);
        control.SpinnerIndexUpdate(position,tagId);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
