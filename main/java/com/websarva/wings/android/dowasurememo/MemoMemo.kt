package com.websarva.wings.android.dowasurememo

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

/**
 * 普通のメモを入力するMemoメモのクラス
 *
 * @author nakayama
 * @version 1.0
 */
class MemoMemo : AppCompatActivity() {
    var context: Context = this@MemoMemo
    var inflater: LayoutInflater? = null
    var llMemoLayout: LinearLayout? = null
    var llMemoInputform: LinearLayout? = null
    var llMemoFrame: LinearLayout? = null
    var llMemoTitle: LinearLayout? = null
    var etMemoTitle: EditText? = null
    var etMemoContents: EditText? = null
    var btDelete: ImageButton? = null
    var strMemoTitle: String? = null
    var strMemoContents: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo_memo)
        inflater = LayoutInflater.from(applicationContext)
        llMemoLayout = findViewById(R.id.ll_memo_layout)
        llMemoInputform = inflater.inflate(R.layout.memo_inputform, null) as LinearLayout
        /**
         * データベースの列名の配列
         */
        val columnNames = listOf("memotitle", "memocontents")

        //データベースからデータを取り出して、レイアウトを作成する処理
        val control = DatabaseControl(context, TABLE, columnNames)
        control.selectDatabase(llMemoLayout)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.optionmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.option_add -> {
                //オプションメニューの＋ボタンを押すと、動的にビューを追加する処理
                inflater = LayoutInflater.from(applicationContext)
                llMemoInputform = inflater.inflate(R.layout.memo_inputform, null) as LinearLayout
                llMemoLayout!!.addView(llMemoInputform)
                llMemoFrame = llMemoInputform!!.findViewById(R.id.ll_memo_frame)
                llMemoTitle = llMemoFrame.findViewById(R.id.ll_memo_title)
                btDelete = llMemoTitle.findViewById(R.id.bt_delete)
                btDelete.setOnClickListener(DeleteButton(this@MemoMemo, llMemoLayout, llMemoInputform, TABLE))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()

        //データベースにある全てのデータを削除
        val control = DatabaseControl(context, TABLE)
        control.deleteAllDatabase()

        //メモの文字列を取得してデータベースにインサートする
        for (i in 0 until llMemoLayout!!.childCount) {
            val linearLayout = llMemoLayout!!.getChildAt(i) as LinearLayout
            etMemoTitle = linearLayout.findViewById(R.id.et_memo_title)
            etMemoContents = linearLayout.findViewById(R.id.et_memo_contents)
            strMemoTitle = etMemoTitle.getText().toString()
            strMemoContents = etMemoContents.getText().toString()
            val control2 = DatabaseControl(context, TABLE, i, _CATEGORY, strMemoTitle, strMemoContents)
            control2.insertDatabaseTwoColumns("memotitle", "memocontents")
        }
        finish()
    }

    companion object {
        private const val _CATEGORY = "MEMO"

        /**
         * データベースのテーブル名
         */
        private const val TABLE = "memo"
    }
}