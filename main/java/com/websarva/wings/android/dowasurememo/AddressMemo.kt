package com.websarva.wings.android.dowasurememo

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
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
    private lateinit var inflater: LayoutInflater

//    private lateinit var llAddressLayout: LinearLayout
//    private lateinit var llAddressInputform: LinearLayout

//    private var llAddressFrame: LinearLayout? = null
//    private var llPostNumberinputform: LinearLayout? = null
//    private var etAddressTitle: EditText? = null
//    private var etPostNumber1: EditText? = null
//    private var etPostNumber2: EditText? = null
//    private var etAddressDetail: EditText? = null
//    private var btDelete: ImageButton? = null
//    private var strAddressTitle: String? = null
//    private var strPostNumber1: String? = null
//    private var strPostNumber2: String? = null
//    private var strAddressDetail: String? = null

    private lateinit var binding: ActivityAddressMemoBinding
    private lateinit var binding2: AddressInputformBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressMemoBinding.inflate(layoutInflater)
        binding2 = AddressInputformBinding.inflate(layoutInflater)
        setContentView(binding.root)


        inflater = LayoutInflater.from(applicationContext)
        //llAddressLayout = findViewById(R.id.ll_address_layout)
        //llAddressInputform = inflater.inflate(R.layout.address_inputform, null) as LinearLayout

        /**
         * データベースの列名の配列
         */
        val columnNames = listOf("addresstitle", "postnumber1", "postnumber2", "addressdetail")

        //データベースからデータを取り出して、レイアウトを作成する処理
        val control = DatabaseControl(context, TABLE, columnNames)
        control.selectDatabase(binding.llAddressLayout)
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
                //llAddressLayout = findViewById(R.id.ll_address_layout)
                //var llAddressInputform = inflater.inflate(R.layout.address_inputform, null) as LinearLayout
                binding.llAddressLayout.addView(binding2.llAddressInputform)
//                var llAddressFrame = llAddressInputform.findViewById(R.id.ll_address_frame)
//                var llPostNumberinputform = llAddressFrame.findViewById(R.id.ll_postnumber_inputform)
//                var btDelete = llPostNumberinputform.findViewById(R.id.bt_delete)
                binding2.btDelete.setOnClickListener {
                    DeleteButton(this@AddressMemo, binding.llAddressLayout, binding2.llAddressInputform, TABLE)
                }

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
        for (i in 0..binding.llAddressLayout.childCount - 1) {
            var linearLayout = binding.llAddressLayout.getChildAt(i) as LinearLayout

//            var etAddressTitle = linearLayout.findViewById(R.id.et_address_title)
//            var etPostNumber1 = linearLayout.findViewById(R.id.et_postnumber1)
//            var etPostNumber2 = linearLayout.findViewById(R.id.et_postnumber2)
//            var etAddressDetail = linearLayout.findViewById(R.id.et_addres_detail)
            var etAddressTitle = linearLayout.findViewById<EditText>(R.id.et_address_title)
            var etPostNumber1 = linearLayout.findViewById<EditText>(R.id.et_postnumber1)
            var etPostNumber2 = linearLayout.findViewById<EditText>(R.id.et_postnumber2)
            var etAddressDetail = linearLayout.findViewById<EditText>(R.id.et_address_detail)

            var strAddressTitle = etAddressTitle.getText().toString()
            var strPostNumber1 = etPostNumber1.getText().toString()
            var strPostNumber2 = etPostNumber2.getText().toString()
            var strAddressDetail = etAddressDetail.getText().toString()
            val control2 = DatabaseControl(context, TABLE, i, _CATEGORY, strAddressTitle, strPostNumber1, strPostNumber2, strAddressDetail)
            control2.insertDatabaseFourColumns("addresstitle", "postnumber1", "postnumber2", "addressdetail")
        }
        finish()
    }


}