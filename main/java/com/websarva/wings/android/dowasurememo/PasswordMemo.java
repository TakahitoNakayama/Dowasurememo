package com.websarva.wings.android.dowasurememo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PasswordMemo extends AppCompatActivity {

    private Databasehelper _helper;
    private String _category = "password";

    private int indexCounter=1;
    int tagId;
    String table="password";
    Context context=PasswordMemo.this;


    LayoutInflater inflater;
    LinearLayout linearLayout;
    LinearLayout llPasswordLayout;
    LinearLayout llPasswordInputform;
    LinearLayout llPasswordFrame;
    LinearLayout llPasswordTitle;
    LinearLayout llPasswordContents;

    EditText etPasswordTitle;
    EditText etPasswordContents;
    ImageButton btDelete;
    ImageButton btClip;

    String strPasswordTitle;
    String strPasswordContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_memo);

        Intent intent = getIntent();
        llPasswordLayout = findViewById(R.id.ll_password_layout);

        _helper=new Databasehelper(getApplicationContext());
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlSelect="SELECT * FROM password";
        Cursor cursor=db.rawQuery(sqlSelect,null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int i = cursor.getColumnIndex("_id");
            tagId = cursor.getInt(i);
            llPasswordLayout = findViewById(R.id.ll_password_layout);
            inflater = LayoutInflater.from(getApplicationContext());
            llPasswordInputform=(LinearLayout)inflater.inflate(R.layout.password_inputform,null);
            llPasswordLayout.addView(llPasswordInputform);

            llPasswordFrame=llPasswordInputform.findViewById(R.id.ll_password_frame);
            llPasswordTitle = llPasswordFrame.findViewById(R.id.ll_password_title);
            llPasswordContents = llPasswordFrame.findViewById(R.id.ll_password_contents);

            etPasswordTitle = llPasswordTitle.findViewById(R.id.et_password_title);
            etPasswordContents = llPasswordContents.findViewById(R.id.et_password_contents);
            btDelete=llPasswordTitle.findViewById(R.id.bt_delete);
            btDelete.setOnClickListener
                    (new DeleteButton(PasswordMemo.this,llPasswordLayout,llPasswordInputform,table));
            btClip=llPasswordContents.findViewById(R.id.bt_clip);
            btClip.setOnClickListener(new ClipButtonListener(etPasswordContents));

            etPasswordTitle.setTag(tagId);
            etPasswordContents.setTag(tagId);
            btDelete.setTag(tagId);

            i = cursor.getColumnIndex("passwordtitle");
            strPasswordTitle = cursor.getString(i);

            i = cursor.getColumnIndex("passwordcontents");
            strPasswordContents = cursor.getString(i);



            try {
                etPasswordTitle.setText(strPasswordTitle);
                EditEventListener etListener=new EditEventListener(etPasswordTitle,PasswordMemo.this);
                etPasswordTitle.addTextChangedListener(etListener);
            } catch (NullPointerException e) {
                strPasswordTitle = "";
            }

            try {
                etPasswordContents.setText(strPasswordContents);
                EditEventListener etListener2=new EditEventListener(etPasswordContents,PasswordMemo.this);
                etPasswordContents.addTextChangedListener(etListener2);
            } catch (NullPointerException e) {
                strPasswordContents = "";
            }

        }

        DatabaseControl control=new DatabaseControl(context,table);
        indexCounter=control.GetIndexCounter();
        Log.d("main",""+indexCounter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.optionmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_add:
                inflater = LayoutInflater.from(getApplicationContext());
                llPasswordInputform=(LinearLayout)inflater.inflate(R.layout.password_inputform,null);
                llPasswordLayout.addView(llPasswordInputform);

                llPasswordFrame=llPasswordInputform.findViewById(R.id.ll_password_frame);
                llPasswordTitle=llPasswordFrame.findViewById(R.id.ll_password_title);
                llPasswordContents=llPasswordFrame.findViewById(R.id.ll_password_contents);

                etPasswordTitle=llPasswordTitle.findViewById(R.id.et_password_title);
                etPasswordContents=llPasswordContents.findViewById(R.id.et_password_contents);
                btDelete=llPasswordTitle.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(PasswordMemo.this,llPasswordLayout,llPasswordInputform,table));
                btClip=llPasswordContents.findViewById(R.id.bt_clip);
                btClip.setOnClickListener(new ClipButtonListener(etPasswordContents));

                etPasswordTitle.setTag(indexCounter);
                etPasswordContents.setTag(indexCounter);
                btDelete.setTag(indexCounter);

                EditEventListener etListener=new EditEventListener(etPasswordTitle,PasswordMemo.this);
                etPasswordTitle.addTextChangedListener(etListener);
                EditEventListener etListener2=new EditEventListener(etPasswordContents,PasswordMemo.this);
                etPasswordContents.addTextChangedListener(etListener2);

                tagId=indexCounter;
                String str="";

                DatabaseControl control=new DatabaseControl(context,table);
                control.DatabaseDelete(tagId);

                String column1="passwordtitle";
                String column2="passwordcontents";


                DatabaseControl control2=new DatabaseControl
                        (context,table,tagId,_category,str);
                control2.DatabaseInsert(column1,column2);

                indexCounter++;
                control.IndexCounterUpdate(indexCounter);


        }
        return super.onOptionsItemSelected(item);
    }

    class CopyClipbord extends ClipData {

        public CopyClipbord(CharSequence label, String[] mimeTypes, Item item) {
            super(label, mimeTypes, item);
        }


    }

    class ClipButtonListener implements View.OnClickListener{

        EditText editText;

        public ClipButtonListener(EditText e){
            editText=e;
        }


        @Override
        public void onClick(View v) {
            ClipData.Item item=new ClipData.Item(editText.getText());
            String[] mimeType=new String[1];
            mimeType[0]= ClipDescription.MIMETYPE_TEXT_PLAIN;
            CopyClipbord copy=new CopyClipbord("password",mimeType,item);
            ClipboardManager cm= (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            cm.setPrimaryClip(copy);
            Log.d("cliplistener204","リスナー起動");

            Toast.makeText
                    (PasswordMemo.this,"クリップボードにコピーしました",Toast.LENGTH_SHORT).show();
        }
    }

}