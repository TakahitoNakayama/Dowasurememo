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
 * 更新期限を入力するUpdateメモのクラス
 *
 * @author nakayama
 * @version 1.0
 */
class UpdateMemo : AppCompatActivity() {
    private val context: Context = this@UpdateMemo
    private var inflater: LayoutInflater? = null
    private var llUpdateLayout: LinearLayout? = null
    private var llUpdateInputform: LinearLayout? = null
    private var llUpdateTitle: LinearLayout? = null
    private var llUpdateDeadline: LinearLayout? = null
    private var etUpdateTitle: EditText? = null
    private var etUpdateYear: EditText? = null
    private var etUpdateMonth: EditText? = null
    private var etUpdateDay: EditText? = null
    private var btDelete: ImageButton? = null
    private var btDateSelect: ImageButton? = null
    private var strUpdateTitle: String? = null
    private var strUpdateYear: String? = null
    private var strUpdateMonth: String? = null
    private var strUpdateDay: String? = null
    var manager: FragmentManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_memo)
        inflater = LayoutInflater.from(applicationContext)
        llUpdateLayout = findViewById(R.id.ll_update_layout)
        llUpdateInputform = inflater.inflate(R.layout.update_inputform, null) as LinearLayout
        /**
         * データベースの列名の配列
         */
        val columnNames = listOf("updatetitle", "updateyear", "updatemonth", "updateday")
        /**
         * カレンダーによる日付選択用のFragmentManager型の変数
         */
        manager = supportFragmentManager

        //データベースからデータを取り出して、レイアウトを作成する処理
        val control = DatabaseControl(context, TABLE, columnNames, manager)
        control.selectDatabase(llUpdateLayout)
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
                inflater = LayoutInflater.from(applicationContext)
                llUpdateInputform = inflater.inflate(R.layout.update_inputform, null) as LinearLayout
                llUpdateLayout!!.addView(llUpdateInputform)
                llUpdateTitle = llUpdateInputform!!.findViewById(R.id.ll_update_title)
                llUpdateDeadline = llUpdateTitle.findViewById(R.id.ll_update_deadline)
                btDelete = llUpdateDeadline.findViewById(R.id.bt_delete)
                btDelete.setOnClickListener(DeleteButton(this@UpdateMemo, llUpdateLayout, llUpdateInputform, TABLE))
                btDateSelect = llUpdateDeadline.findViewById(R.id.bt_date_select)
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
        for (i in 0 until llUpdateLayout!!.childCount) {
            val linearLayout = llUpdateLayout!!.getChildAt(i) as LinearLayout
            etUpdateTitle = linearLayout.findViewById(R.id.et_update_title)
            etUpdateYear = linearLayout.findViewById(R.id.et_update_year)
            etUpdateMonth = linearLayout.findViewById(R.id.et_update_month)
            etUpdateDay = linearLayout.findViewById(R.id.et_update_day)
            strUpdateTitle = etUpdateTitle.getText().toString()
            strUpdateYear = etUpdateYear.getText().toString()
            strUpdateMonth = etUpdateMonth.getText().toString()
            strUpdateDay = etUpdateDay.getText().toString()
            val control2 = DatabaseControl(context, TABLE, i, _CATEGORY, strUpdateTitle, strUpdateYear, strUpdateMonth, strUpdateDay)
            control2.insertDatabaseFourColumns("updatetitle", "updateyear", "updatemonth", "updateday")
        }
        finish()
    }

    companion object {
        private const val _CATEGORY = "UPDATE1"

        /**
         * データベースのテーブル名
         */
        private const val TABLE = "update1"
    }
}