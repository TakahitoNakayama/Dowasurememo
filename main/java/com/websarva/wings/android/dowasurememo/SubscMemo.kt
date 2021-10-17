package com.websarva.wings.android.dowasurememo

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

/**
 * サブスクサービスをメモするSubscメモのクラス
 *
 * @author nakayama
 * @version 1.0
 */
class SubscMemo : AppCompatActivity() {
    private val context: Context = this@SubscMemo
    private var inflater: LayoutInflater? = null
    private var linearLayout: LinearLayout? = null
    private var llSubscLayout: LinearLayout? = null
    private var llSubscInputform: LinearLayout? = null
    private var llSubscTitle: LinearLayout? = null
    private var llSubscPrice: LinearLayout? = null
    private var llSubscFrame: LinearLayout? = null
    private var etSubscTitle: EditText? = null
    private var etSubscPrice: EditText? = null
    private var btSubscCulc: Button? = null
    private var btDelete: ImageButton? = null
    private var spPaymentInterbal: Spinner? = null
    private var strSubscTitle: String? = null
    private var strSubscPrice: String? = null
    private var intSpinnerIndex = 0
    private var strSpinnerIndex: String? = null
    private var monthPaymentAmount = 0
    private var tvMonthPayment: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subsc_memo)
        inflater = LayoutInflater.from(applicationContext)
        llSubscLayout = findViewById(R.id.ll_subsc_layout)
        llSubscInputform = inflater.inflate(R.layout.subsc_inputform, null) as LinearLayout
        btSubscCulc = findViewById(R.id.bt_subsc_culc)
        btSubscCulc.setOnClickListener(CulcButtonListener())
        /**
         * データベースの列名の配列
         */
        val columnNames = listOf("subsctitle", "subscprice", "subscinterbal")

        //データベースからデータを取り出して、レイアウトを作成する処理
        val control = DatabaseControl(context, TABLE, columnNames)
        control.selectDatabase(llSubscLayout)
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
                llSubscLayout = findViewById(R.id.ll_subsc_layout)
                inflater = LayoutInflater.from(applicationContext)
                llSubscInputform = inflater.inflate(R.layout.subsc_inputform, null) as LinearLayout
                llSubscLayout.addView(llSubscInputform)
                llSubscFrame = llSubscInputform!!.findViewById(R.id.ll_subsc_frame)
                llSubscTitle = llSubscFrame.findViewById(R.id.ll_subsc_title)
                llSubscPrice = llSubscFrame.findViewById(R.id.ll_subsc_price)
                btDelete = llSubscTitle.findViewById(R.id.bt_delete)
                btDelete.setOnClickListener(DeleteButton(this@SubscMemo, llSubscLayout, llSubscInputform, TABLE))
                spPaymentInterbal = llSubscPrice.findViewById(R.id.sp_payment_interbal)
                btSubscCulc = findViewById(R.id.bt_subsc_culc)
                btSubscCulc.setOnClickListener(CulcButtonListener())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 金額を取得して、スピナーの支払い期間に応じた合計金額を出力するクラス
     *
     * @author nakayama
     * @version 1.0
     */
    internal inner class CulcButtonListener : View.OnClickListener {
        override fun onClick(v: View) {
            var price: Int
            var strprice: String
            monthPaymentAmount = 0
            for (i in 0 until llSubscLayout!!.childCount) {
                linearLayout = llSubscLayout!!.getChildAt(i) as LinearLayout
                etSubscPrice = linearLayout!!.findViewById(R.id.et_subsc_price)
                price = 0
                try {
                    strprice = etSubscPrice.getText().toString()
                    price = Integer.valueOf(strprice)
                } catch (e: NumberFormatException) {
                    strprice = "0"
                }
                spPaymentInterbal = linearLayout!!.findViewById(R.id.sp_payment_interbal)
                val strInterbal = spPaymentInterbal.getSelectedItem() as String
                when (strInterbal) {
                    "毎月" -> monthPaymentAmount += price
                    "2ヶ月" -> monthPaymentAmount += price / 2
                    "3ヶ月" -> monthPaymentAmount += price / 3
                    "4ヶ月" -> monthPaymentAmount += price / 4
                    "半年" -> monthPaymentAmount += price / 6
                    "1年" -> monthPaymentAmount += price / 12
                    "2年" -> monthPaymentAmount += price / 24
                }
            }
            tvMonthPayment = findViewById(R.id.tv_month_payment)
            tvMonthPayment.setText(String.format("%,d", monthPaymentAmount))
        }
    }

    override fun onPause() {
        super.onPause()

        //データベースにある全てのデータを削除
        val control = DatabaseControl(context, TABLE)
        control.deleteAllDatabase()

        //メモの文字列を取得してデータベースにインサートする
        for (i in 0 until llSubscLayout!!.childCount) {
            val linearLayout = llSubscLayout!!.getChildAt(i) as LinearLayout
            etSubscTitle = linearLayout.findViewById(R.id.et_subsc_title)
            etSubscPrice = linearLayout.findViewById(R.id.et_subsc_price)
            spPaymentInterbal = linearLayout.findViewById(R.id.sp_payment_interbal)
            strSubscTitle = etSubscTitle.getText().toString()
            strSubscPrice = etSubscPrice.getText().toString()
            intSpinnerIndex = spPaymentInterbal.getSelectedItemPosition()
            strSpinnerIndex = intSpinnerIndex.toString()
            val control2 = DatabaseControl(context, TABLE, i, _CATEGORY, strSubscTitle, strSubscPrice, strSpinnerIndex)
            control2.insertDatabaseThreeColumns("subsctitle", "subscprice", "subscinterbal")
        }
        finish()
    }

    companion object {
        private const val _CATEGORY = "SUBSC"

        /**
         * データベースのテーブル名
         */
        private const val TABLE = "subsc"
    }
}