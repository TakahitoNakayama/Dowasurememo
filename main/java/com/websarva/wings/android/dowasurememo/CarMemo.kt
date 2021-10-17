package com.websarva.wings.android.dowasurememo

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.websarva.wings.android.dowasurememo.databinding.*

class CarMemo : AppCompatActivity() {

    companion object {
        private const val _CATEGORY = "CAR"

        /**
         * データベースのテーブル名
         */
        private const val TABLE = "car"
    }

    private val context: Context = this@CarMemo
//    private lateinit var inflater: LayoutInflater

//    private var llCarLayout: LinearLayout? = null
//    private var llCarNameInputform: LinearLayout? = null
//    private var etCarName: EditText? = null
//    private var etCarMemoTitle: EditText? = null
//    private var etCarMemoContents: EditText? = null
//    private var btDelete: ImageButton? = null
//    private var btCarDetailAdd: ImageButton? = null
//    private var strCarName: String? = null
//    private var strCarMemoTitle: String? = null
//    private var strCarMemoContents: String? = null

    private lateinit var binding: ActivityCarMemoBinding
    private lateinit var carNameBinding: CarNameInputformBinding
    private lateinit var carDetailBinding: CarDetailInputformBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarMemoBinding.inflate(layoutInflater)
        carNameBinding = CarNameInputformBinding.inflate(layoutInflater)
        carDetailBinding = CarDetailInputformBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        llCarLayout = findViewById(R.id.ll_car_layout)
//        inflater = LayoutInflater.from(applicationContext)
//        llCarLayout = findViewById(R.id.ll_car_layout)

        //データベースからデータを取り出して、レイアウトを作成する処理
        DatabaseControl(context, TABLE).selectDatabase(binding.llCarLayout)
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
//                inflater = LayoutInflater.from(applicationContext)
                //llCarNameInputform = inflater?.inflate(R.layout.car_name_inputform, null) as LinearLayout
                binding.llCarLayout.addView(carNameBinding.llCarNameInputform)
//                btCarDetailAdd = llCarNameInputform!!.findViewById(R.id.bt_cardetail_add)
//                btCarDetailAdd?.setOnClickListener(AddCarDetail(this@CarMemo, llCarLayout))
//                btDelete = llCarNameInputform!!.findViewById(R.id.bt_delete)
                carNameBinding.btDelete.setOnClickListener(DeleteButton(this@CarMemo, binding.llCarLayout, carNameBinding.llCarNameInputform, TABLE))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()

        //データベースにある全てのデータを削除
        DatabaseControl(context, TABLE).deleteAllDatabase()

        //メモの文字列を取得してデータベースにインサートする
        for (i in 0..binding.llCarLayout.childCount) {
            val linearLayout = binding.llCarLayout.getChildAt(i) as LinearLayout
            when (linearLayout.id) {
                R.id.ll_car_name_inputform -> {
                    //carNameBinding.etCarName = linearLayout.findViewById(R.id.et_car_name)
                    var strCarName = carNameBinding.etCarName.text.toString()
                    val inputform = "name"
                    DatabaseControl(context, TABLE, i, _CATEGORY, strCarName, inputform)
                            .insertDatabaseTwoColumns("carname", "inputform")
                }
                R.id.ll_car_detail_inputform -> {
//                    etCarMemoTitle = linearLayout.findViewById(R.id.et_car_memo_title)
//                    etCarMemoContents = linearLayout.findViewById(R.id.et_car_memo_contents)
                    var strCarMemoTitle = carDetailBinding.etCarMemoTitle.text.toString()
                    var strCarMemoContents = carDetailBinding.etCarMemoContents.text.toString()
                    val memoinputform = "detail"
                    DatabaseControl(context, TABLE, i, _CATEGORY, strCarMemoTitle, strCarMemoContents, memoinputform)
                            .insertDatabaseThreeColumns("carmemotitle", "carmemocontents", "inputform")
                }
            }
        }
        finish()
    }


}

/**
 * 車の詳細を入力するviewを追加するためのクラス
 *
 * @author nakayama
 * @version 1.0
 */
internal class AddCarDetail(context: Context, private val llBaseLayout: LinearLayout?) : LinearLayout(context), View.OnClickListener {

    private lateinit var inflater: LayoutInflater
    private var llCarDetailInputform: LinearLayout? = null
    private val TABLE = "car"

    override fun onClick(v: View) {
        /*押されたボタンの親ビューとllBaseLayoutの子ビューが等しいときに、
        そのビューのインデックス＋１した場所に車の詳細を入力するビューを追加する*/
        inflater = LayoutInflater.from(context)
        llCarDetailInputform = inflater.inflate(R.layout.car_detail_inputform, null) as LinearLayout
        val linearLayout = v.parent as LinearLayout
        for (i in 0..llBaseLayout!!.childCount) {
            val sameLinearLayout = llBaseLayout!!.getChildAt(i) as LinearLayout
            if (linearLayout === sameLinearLayout) {
                llBaseLayout.addView(llCarDetailInputform, i + 1)
                val btDelete = llCarDetailInputform!!.findViewById<ImageButton>(R.id.bt_delete)
                btDelete.setOnClickListener(DeleteButton(context, llBaseLayout, llCarDetailInputform, TABLE))
            }
        }
    }


}