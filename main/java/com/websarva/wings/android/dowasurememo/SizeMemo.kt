package com.websarva.wings.android.dowasurememo

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

/**
 * 体のサイズを入力するSizeメモのクラス
 *
 * @author nakayama
 * @version 1.0
 */
class SizeMemo : AppCompatActivity() {
    private val context: Context = this@SizeMemo
    private var inflater: LayoutInflater? = null
    private var llSizeLayout: LinearLayout? = null
    private var llSizeInputform: LinearLayout? = null
    private var etBodyPart: EditText? = null
    private var etRecord: EditText? = null
    private var etUnit: EditText? = null
    private var btDelete: ImageButton? = null
    private var strBodyPart: String? = null
    private var strRecord: String? = null
    private var strUnit: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_size_memo)
        inflater = LayoutInflater.from(applicationContext)
        llSizeLayout = findViewById(R.id.ll_size_layout)
        llSizeInputform = inflater.inflate(R.layout.size_inputform, null) as LinearLayout
        /**
         * データベースの列名の配列
         */
        val columnNames = listOf("bodypart", "records", "unit")

        //データベースからデータを取り出して、レイアウトを作成する処理
        val control = DatabaseControl(context, TABLE, columnNames)
        control.selectDatabase(llSizeLayout)
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
                llSizeInputform = inflater.inflate(R.layout.size_inputform, null) as LinearLayout
                llSizeLayout!!.addView(llSizeInputform)
                val circle = llSizeInputform!!.findViewById<ImageView>(R.id.circle)
                circle.setColorFilter(Color.rgb(127, 255, 212))
                btDelete = llSizeInputform!!.findViewById(R.id.bt_delete)
                btDelete.setOnClickListener(DeleteButton(context, llSizeLayout, llSizeInputform, TABLE))
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
        for (i in 0 until llSizeLayout!!.childCount) {
            val linearLayout = llSizeLayout!!.getChildAt(i) as LinearLayout
            etBodyPart = linearLayout.findViewById(R.id.et_bodypart)
            etRecord = linearLayout.findViewById(R.id.et_record)
            etUnit = linearLayout.findViewById(R.id.et_unit)
            strBodyPart = etBodyPart.getText().toString()
            strRecord = etRecord.getText().toString()
            strUnit = etUnit.getText().toString()
            val control2 = DatabaseControl(context, TABLE, i, _CATEGORY, strBodyPart, strRecord, strUnit)
            control2.insertDatabaseThreeColumns("bodypart", "records", "unit")
        }
        finish()
    }

    companion object {
        private const val _CATEGORY = "SIZE"

        /**
         * データベースのテーブル名
         */
        private const val TABLE = "size"
    }
}