package com.websarva.wings.android.dowasurememo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MemoMemo extends AppCompatActivity {

    LayoutInflater inflater;
    LinearLayout linearLayout;
    LinearLayout llMemoLayout;
    LinearLayout llMemoInputform;

    int tagId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_memo);

        Intent intent = getIntent();

        llMemoLayout = findViewById(R.id.ll_memo_layout);
        inflater = LayoutInflater.from(getApplicationContext());
        llMemoInputform=(LinearLayout)inflater.inflate(R.layout.memo_inputform,null);
        llMemoLayout.addView(llMemoInputform,0);

        LinearLayout llMemoTitle=llMemoInputform.findViewById(R.id.ll_memo_title);
        ImageButton btDelete=llMemoTitle.findViewById(R.id.bt_delete);
        btDelete.setOnClickListener
                (new DeleteButton(MemoMemo.this,llMemoLayout,llMemoInputform));
    }
}