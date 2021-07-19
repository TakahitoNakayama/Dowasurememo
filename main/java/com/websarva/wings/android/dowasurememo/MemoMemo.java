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

public class MemoMemo extends AppCompatActivity {

    LayoutInflater inflater;
    LinearLayout linearLayout;
    LinearLayout llMemoLayout;
    LinearLayout llMemoInputform;
    LinearLayout llMemoFrame;
    LinearLayout llMemoTitle;

    EditText etMemoTitle;
    EditText etMemoContents;

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
                llMemoInputform=(LinearLayout)inflater.inflate(R.layout.memo_inputform,null);
                llMemoLayout.addView(llMemoInputform);

                llMemoFrame=llMemoInputform.findViewById(R.id.ll_memo_frame);
                llMemoTitle=llMemoFrame.findViewById(R.id.ll_memo_title);
                ImageButton btDelete=llMemoTitle.findViewById(R.id.bt_delete);
                btDelete.setOnClickListener
                        (new DeleteButton(MemoMemo.this,llMemoLayout,llMemoInputform));

                etMemoTitle=llMemoTitle.findViewById(R.id.et_memo_title);
                etMemoContents=llMemoFrame.findViewById(R.id.et_memo_contents);

                EditEventListener etListener=new EditEventListener(etMemoTitle,MemoMemo.this);
                etMemoTitle.addTextChangedListener(etListener);
                EditEventListener etListener2=new EditEventListener(etMemoContents,MemoMemo.this);
                etMemoContents.addTextChangedListener(etListener2);

        }
        return super.onOptionsItemSelected(item);
    }


}