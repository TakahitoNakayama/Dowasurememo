package com.websarva.wings.android.dowasurememo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

public class WishlistMemo extends AppCompatActivity {

    private Databasehelper _helper;
    private String _category = "wishlist";

    private int indexCounter=2;
    int tagId;
    String table="wishlist";
    Context context=WishlistMemo.this;

    LayoutInflater inflater;
    LinearLayout llWishlistLayout;
    LinearLayout llWishlistInputform;
    LinearLayout llWishlistTitle;

    EditText etWishlistTitle;
    ImageButton btDelete;

    String strWishlistTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist_memo);

        Intent intent = getIntent();
        llWishlistLayout = findViewById(R.id.ll_wishlist_layout);

//        llWishlistLayout.removeAllViews();
//        DatabaseControl control4=new DatabaseControl(context,table);
//        control4.DatabaseAllDelete();

        _helper=new Databasehelper(getApplicationContext());
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlSelect="SELECT * FROM wishlist";
        Cursor cursor=db.rawQuery(sqlSelect,null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int i = cursor.getColumnIndex("_id");
            tagId = cursor.getInt(i);

            inflater = LayoutInflater.from(getApplicationContext());
            llWishlistLayout = findViewById(R.id.ll_wishlist_layout);
            llWishlistInputform=(LinearLayout)inflater.inflate(R.layout.wishlist_inputform,null);
            llWishlistLayout.addView(llWishlistInputform);

            llWishlistTitle=llWishlistInputform.findViewById(R.id.ll_wishlist_title);

            etWishlistTitle=llWishlistTitle.findViewById(R.id.et_wishlist_title);
            btDelete=llWishlistTitle.findViewById(R.id.bt_delete);
            btDelete.setOnClickListener
                    (new DeleteButton(WishlistMemo.this,llWishlistLayout,llWishlistInputform,table));

            i = cursor.getColumnIndex("wishlisttitle");
            strWishlistTitle = cursor.getString(i);

            try {
                etWishlistTitle.setText(strWishlistTitle);
            } catch (NullPointerException e) {
                strWishlistTitle = "";
            }

        }

        if(llWishlistLayout.getChildCount()!=0){
            LinearLayout firstView= (LinearLayout) llWishlistLayout.getChildAt(0);
            firstView.setVisibility(View.GONE);
        }else {
        }
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
                if(llWishlistLayout.getChildCount()==0){
                    inflater = LayoutInflater.from(getApplicationContext());
                    llWishlistLayout = findViewById(R.id.ll_wishlist_layout);
                    llWishlistInputform=(LinearLayout)inflater.inflate(R.layout.wishlist_inputform,null);
                    llWishlistLayout.addView(llWishlistInputform);
                    llWishlistInputform.setVisibility(View.GONE);

                    String str="";
                    DatabaseControl control = new DatabaseControl(context, table);
                    control.deleteDatabase(1);

                    String column1="wishlisttitle";

                    DatabaseControl control2 = new DatabaseControl
                            (context, table,1, _category, str);
                    control2.insertDatabaseOneColumns(column1);
                }
                inflater = LayoutInflater.from(getApplicationContext());
                llWishlistInputform=(LinearLayout)inflater.inflate(R.layout.wishlist_inputform,null);
                llWishlistLayout.addView(llWishlistInputform);

                llWishlistTitle=llWishlistInputform.findViewById(R.id.ll_wishlist_title);

                etWishlistTitle=llWishlistTitle.findViewById(R.id.et_wishlist_title);
                btDelete=llWishlistTitle.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(WishlistMemo.this,llWishlistLayout,llWishlistInputform,table));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        indexCounter = 2;

        for (int i = 0; i < llWishlistLayout.getChildCount(); i++) {
            LinearLayout linearLayout = (LinearLayout) llWishlistLayout.getChildAt(i);
            etWishlistTitle = linearLayout.findViewById(R.id.et_wishlist_title);

            strWishlistTitle = etWishlistTitle.getText().toString();


            DatabaseControl control = new DatabaseControl(context, table);
            control.deleteDatabase(indexCounter);

            String column1="wishlisttitle";


            DatabaseControl control2=new DatabaseControl
                    (context,table,indexCounter,_category,strWishlistTitle);
            control2.insertDatabaseOneColumns(column1);

            Log.d("pause358", "" + indexCounter);
            indexCounter++;

        }
    }
}