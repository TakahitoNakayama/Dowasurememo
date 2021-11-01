package com.websarva.wings.android.dowasurememo

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.websarva.wings.android.dowasurememo.databinding.ActivityAddressMemoBinding
import com.websarva.wings.android.dowasurememo.databinding.AddressInputformBinding

/**
 * 住所を入力するAddressメモのクラス
 *
 * @author nakayama
 * @version 1.0
 */
class AddressMemo : AppCompatActivity() {

    companion object {
        private const val _CATEGORY = "ADDRESS"

        /**
         * データベースのテーブル名
         */
        private const val TABLE = "address"
    }

    private val context: Context = this@AddressMemo
    private lateinit var binding: ActivityAddressMemoBinding
    private lateinit var inputformBinding: AddressInputformBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressMemoBinding.inflate(layoutInflater)
        inputformBinding = AddressInputformBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val llAddressLayout = findViewById<LinearLayout>(R.id.ll_address_layout)

        /**
         * データベースの列名の配列
         */
        val columnNames = listOf("addresstitle", "postnumber1", "postnumber2", "addressdetail")

        //データベースからデータを取り出して、レイアウトを作成する処理
        DatabaseControl(context, TABLE, columnNames)
                .selectDatabase(llAddressLayout)
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
                val inflater = LayoutInflater.from(applicationContext)
                val llAddressLayout = findViewById<LinearLayout>(R.id.ll_address_layout)
                val llAddressInputform = inflater.inflate(R.layout.address_inputform, null) as LinearLayout
                val llAddressFrame = llAddressInputform.findViewById<LinearLayout>(R.id.ll_address_frame)
                val llAddressTitle = llAddressFrame.findViewById<LinearLayout>(R.id.ll_address_title)
                val btDelete = llAddressTitle.findViewById<ImageButton>(R.id.bt_delete)
                btDelete.setOnClickListener {
                    DeleteButton(this@AddressMemo, llAddressLayout, llAddressInputform, TABLE)
                }

                val llPostNumberinputform = llAddressFrame.findViewById<LinearLayout>(R.id.ll_postnumber_inputform)
                val btPostNumberSearch = llPostNumberinputform.findViewById<Button>(R.id.bt_postnumber_search)
                val etPostNumber1 = llPostNumberinputform.findViewById<EditText>(R.id.et_postnumber1)
                val etPostNumber2 = llPostNumberinputform.findViewById<EditText>(R.id.et_postnumber2)
                val etAddressDetail = llAddressFrame.findViewById<EditText>(R.id.et_address_detail)
                btPostNumberSearch.setOnClickListener {
                    val addressText = PostNumberAPIClient(this)
                            .receivePostNumberInfo(etPostNumber1.text.toString(), etPostNumber2.text.toString())
                    Log.d("button", "$addressText")
                    etAddressDetail.setText(addressText)
                }
                llAddressLayout.addView(llAddressInputform)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onPause() {
        super.onPause()

        //データベースにある全てのデータを削除
        DatabaseControl(context, TABLE).deleteAllDatabase()

        //メモの文字列を取得してデータベースにインサートする
        for (i in 0..binding.llAddressLayout.childCount - 1) {
            val linearLayout = binding.llAddressLayout.getChildAt(i) as LinearLayout

            val etAddressTitle = linearLayout.findViewById<EditText>(R.id.et_address_title)
            val etPostNumber1 = linearLayout.findViewById<EditText>(R.id.et_postnumber1)
            val etPostNumber2 = linearLayout.findViewById<EditText>(R.id.et_postnumber2)
            val etAddressDetail = linearLayout.findViewById<EditText>(R.id.et_address_detail)

            val strAddressTitle = etAddressTitle.getText().toString()
            val strPostNumber1 = etPostNumber1.getText().toString()
            val strPostNumber2 = etPostNumber2.getText().toString()
            val strAddressDetail = etAddressDetail.getText().toString()
            val control2 = DatabaseControl(context, TABLE, i, _CATEGORY, strAddressTitle, strPostNumber1, strPostNumber2, strAddressDetail)
            control2.insertDatabaseFourColumns("addresstitle", "postnumber1", "postnumber2", "addressdetail")
        }
        finish()
    }


}


