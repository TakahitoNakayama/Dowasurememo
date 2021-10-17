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
 * 目標をメモするWishlistメモのクラス
 *
 * @author nakayama
 * @version 1.0
 */
class WishlistMemo : AppCompatActivity() {
    private val context: Context = this@WishlistMemo
    private var inflater: LayoutInflater? = null
    private var llWishlistLayout: LinearLayout? = null
    private var llWishlistInputform: LinearLayout? = null
    private var llWishlistTitle: LinearLayout? = null
    private var etWishlistTitle: EditText? = null
    private var btDelete: ImageButton? = null
    private var strWishlistTitle: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist_memo)
        inflater = LayoutInflater.from(applicationContext)
        llWishlistLayout = findViewById(R.id.ll_wishlist_layout)
        llWishlistInputform = inflater.inflate(R.layout.wishlist_inputform, null) as LinearLayout
        /**
         * データベースの列名の配列
         */
        val columnNames = listOf("wishlisttitle")

        //データベースからデータを取り出して、レイアウトを作成する処理
        val control = DatabaseControl(context, TABLE, columnNames)
        control.selectDatabase(llWishlistLayout)
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
                llWishlistInputform = inflater.inflate(R.layout.wishlist_inputform, null) as LinearLayout
                llWishlistLayout!!.addView(llWishlistInputform)
                llWishlistTitle = llWishlistInputform!!.findViewById(R.id.ll_wishlist_title)
                etWishlistTitle = llWishlistTitle.findViewById(R.id.et_wishlist_title)
                btDelete = llWishlistTitle.findViewById(R.id.bt_delete)
                btDelete.setOnClickListener(DeleteButton(this@WishlistMemo, llWishlistLayout, llWishlistInputform, TABLE))
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
        for (i in 0 until llWishlistLayout!!.childCount) {
            val linearLayout = llWishlistLayout!!.getChildAt(i) as LinearLayout
            etWishlistTitle = linearLayout.findViewById(R.id.et_wishlist_title)
            strWishlistTitle = etWishlistTitle.getText().toString()
            val control2 = DatabaseControl(context, TABLE, i, _CATEGORY, strWishlistTitle)
            control2.insertDatabaseOneColumns("wishlisttitle")
        }
        finish()
    }

    companion object {
        private const val _CATEGORY = "WISHLIST"

        /**
         * データベースのテーブル名
         */
        private const val TABLE = "wishlist"
    }
}