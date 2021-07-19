package com.websarva.wings.android.dowasurememo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class WishlistMemo extends AppCompatActivity {

    LayoutInflater inflater;
    LinearLayout linearLayout;
    LinearLayout llWishlistLayout;
    LinearLayout llWishlistInputform;
    LinearLayout llWishlistTitle;

    EditText etWishlistTitle;

    int tagId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist_memo);

        Intent intent = getIntent();

        llWishlistLayout = findViewById(R.id.ll_wishlist_layout);
        inflater = LayoutInflater.from(getApplicationContext());
        llWishlistInputform=(LinearLayout)inflater.inflate(R.layout.wishlist_inputform,null);
        llWishlistLayout.addView(llWishlistInputform,0);

        LinearLayout llWishlistTitle=llWishlistInputform.findViewById(R.id.ll_wishlist_title);
        ImageButton btDelete=llWishlistTitle.findViewById(R.id.bt_delete);
        btDelete.setOnClickListener
                (new DeleteButton(WishlistMemo.this,llWishlistLayout,llWishlistInputform));
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
                llWishlistInputform=(LinearLayout)inflater.inflate(R.layout.wishlist_inputform,null);
                llWishlistLayout.addView(llWishlistInputform);

                llWishlistTitle=llWishlistInputform.findViewById(R.id.ll_wishlist_title);
                ImageButton btDelete=llWishlistTitle.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(WishlistMemo.this,llWishlistLayout,llWishlistInputform));

                etWishlistTitle=llWishlistTitle.findViewById(R.id.et_wishlist_title);

                EditEventListener etListener=new EditEventListener(etWishlistTitle,WishlistMemo.this);
                etWishlistTitle.addTextChangedListener(etListener);


        }
        return super.onOptionsItemSelected(item);
    }
}