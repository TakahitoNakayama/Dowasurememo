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
import androidx.fragment.app.FragmentManager

/**
 * 住所を入力するDateメモのクラス
 *
 * @author nakayama
 * @version 1.0
 */
class DateMemo : AppCompatActivity() {
    private val context: Context = this@DateMemo
    private var etDateTitle: EditText? = null
    private var etDateYear: EditText? = null
    private var etDateMonth: EditText? = null
    private var etDateDay: EditText? = null
    private var btDelete: ImageButton? = null
    private var btDateSelect: ImageButton? = null
    private var inflater: LayoutInflater? = null
    private var llDateLayout: LinearLayout? = null
    private var llDateInputform: LinearLayout? = null
    private var llDateSelect: LinearLayout? = null
    private var strDateTitle: String? = null
    private var strYear: String? = null
    private var strMonth: String? = null
    private var strDay: String? = null
    var manager: FragmentManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_memo)
        inflater = LayoutInflater.from(applicationContext)
        llDateLayout = findViewById(R.id.ll_date_layout)
        llDateInputform = inflater.inflate(R.layout.date_inputform, null) as LinearLayout
        /**
         * データベースの列名の配列
         */
        val columnNames = listOf("datetitle", "dateyear", "datemonth", "dateday")
        /**
         * カレンダーによる日付選択用のFragmentManager型の変数
         */
        manager = supportFragmentManager

        //データベースからデータを取り出して、レイアウトを作成する処理
        val control = DatabaseControl(context, TABLE, columnNames, manager)
        control.selectDatabase(llDateLayout)
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
                llDateInputform = inflater.inflate(R.layout.date_inputform, null) as LinearLayout
                llDateLayout!!.addView(llDateInputform)
                llDateSelect = llDateInputform!!.findViewById(R.id.ll_date_select)
                btDelete = llDateSelect.findViewById(R.id.bt_delete)
                btDelete.setOnClickListener(DeleteButton(this@DateMemo, llDateLayout, llDateInputform, TABLE))
                btDateSelect = llDateSelect.findViewById(R.id.bt_date_select)
                btDateSelect.setOnClickListener(DatePickerListener(context, manager, TABLE))
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
        for (i in 0 until llDateLayout!!.childCount) {
            val linearLayout = llDateLayout!!.getChildAt(i) as LinearLayout
            etDateTitle = linearLayout.findViewById(R.id.et_date_title)
            etDateYear = linearLayout.findViewById(R.id.et_date_year)
            etDateMonth = linearLayout.findViewById(R.id.et_date_month)
            etDateDay = linearLayout.findViewById(R.id.et_date_day)
            strDateTitle = etDateTitle.getText().toString()
            strYear = etDateYear.getText().toString()
            strMonth = etDateMonth.getText().toString()
            strDay = etDateDay.getText().toString()
            val control2 = DatabaseControl(context, TABLE, i, _CATEGORY, strDateTitle, strYear, strMonth, strDay)
            control2.insertDatabaseFourColumns("datetitle", "dateyear", "datemonth", "dateday")
        }
        finish()
    }

    companion object {
        private const val _CATEGORY = "DATE1"

        /**
         * データベースのテーブル名
         */
        private const val TABLE = "date1"
    }
}