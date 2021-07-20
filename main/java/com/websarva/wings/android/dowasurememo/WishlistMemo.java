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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class WishlistMemo extends AppCompatActivity {

    private Databasehelper _helper;
    private String _category = "wishlist";

    private int indexCounter=1;
    int tagId;
    String table="wishlist";
    Context context=WishlistMemo.this;

    LayoutInflater inflater;
    LinearLayout linearLayout;
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

        _helper=new Databasehelper(getApplicationContext());
        SQLiteDatabase db=_helper.getWritableDatabase();
        String sqlSelect="SELECT * FROM wishlist";
        Cursor cursor=db.rawQuery(sqlSelect,null);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int i = cursor.getColumnIndex("_id");
            tagId = cursor.getInt(i);
            llWishlistLayout = findViewById(R.id.ll_wishlist_layout);
            inflater = LayoutInflater.from(getApplicationContext());
            llWishlistInputform=(LinearLayout)inflater.inflate(R.layout.wishlist_inputform,null);
            llWishlistLayout.addView(llWishlistInputform);

            LinearLayout llWishlistTitle=llWishlistInputform.findViewById(R.id.ll_wishlist_title);

            etWishlistTitle=llWishlistTitle.findViewById(R.id.et_wishlist_title);
            btDelete=llWishlistTitle.findViewById(R.id.bt_delete);
            btDelete.setOnClickListener
                    (new DeleteButton(WishlistMemo.this,llWishlistLayout,llWishlistInputform,table));

            etWishlistTitle.setTag(tagId);
            btDelete.setTag(tagId);

            i = cursor.getColumnIndex("wishlisttitle");
            strWishlistTitle = cursor.getString(i);

            try {
                etWishlistTitle.setText(strWishlistTitle);
                EditEventListener etListener=new EditEventListener(etWishlistTitle,WishlistMemo.this);
                etWishlistTitle.addTextChangedListener(etListener);
            } catch (NullPointerException e) {
                strWishlistTitle = "";
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
                llWishlistInputform=(LinearLayout)inflater.inflate(R.layout.wishlist_inputform,null);
                llWishlistLayout.addView(llWishlistInputform);

                llWishlistTitle=llWishlistInputform.findViewById(R.id.ll_wishlist_title);

                etWishlistTitle=llWishlistTitle.findViewById(R.id.et_wishlist_title);
                btDelete=llWishlistTitle.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(WishlistMemo.this,llWishlistLayout,llWishlistInputform,table));

                etWishlistTitle.setTag(indexCounter);
                btDelete.setTag(indexCounter);

                EditEventListener etListener=new EditEventListener(etWishlistTitle,WishlistMemo.this);
                etWishlistTitle.addTextChangedListener(etListener);

                tagId=indexCounter;
                String str="";

                DatabaseControl control=new DatabaseControl(context,table);
                control.DatabaseDelete(tagId);

                String column1="wishlisttitle";


                DatabaseControl control2=new DatabaseControl
                        (context,table,tagId,_category,str);
                control2.DatabaseInsert(column1);

                indexCounter++;
                control.IndexCounterUpdate(indexCounter);
        }
        return super.onOptionsItemSelected(item);
    }
}