package com.websarva.wings.android.dowasurememo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class WishlistMemo extends AppCompatActivity {

    LayoutInflater inflater;
    LinearLayout linearLayout;
    LinearLayout llWishlistLayout;
    LinearLayout llWishlistInputform;

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
}