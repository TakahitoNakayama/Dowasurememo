package com.websarva.wings.android.dowasurememo

import android.content.*
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

/**
 * パスワードを入力するPasswordメモのクラス
 *
 * @author nakayama
 * @version 1.0
 */
class PasswordMemo : AppCompatActivity() {
    private val context: Context = this@PasswordMemo
    private var inflater: LayoutInflater? = null
    private var llPasswordLayout: LinearLayout? = null
    private var llPasswordInputform: LinearLayout? = null
    private var llPasswordFrame: LinearLayout? = null
    private var llPasswordTitle: LinearLayout? = null
    private var llPasswordContents: LinearLayout? = null
    private var etPasswordTitle: EditText? = null
    private var etPasswordContents: EditText? = null
    private var btDelete: ImageButton? = null
    private var btClip: ImageButton? = null
    private var strPasswordTitle: String? = null
    private var strPasswordContents: String? = null
    var cm: ClipboardManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_memo)
        inflater = LayoutInflater.from(applicationContext)
        llPasswordLayout = findViewById(R.id.ll_password_layout)
        llPasswordInputform = inflater.inflate(R.layout.password_inputform, null) as LinearLayout
        /**
         * データベースの列名の配列
         */
        val columnNames = listOf("passwordtitle", "passwordcontents")
        /**
         * Clipbordを実装するためのClipboardManager型の変数
         */
        cm = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

        //データベースからデータを取り出して、レイアウトを作成する処理
        val control = DatabaseControl(context, TABLE, columnNames, cm)
        control.selectDatabase(llPasswordLayout)
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
                llPasswordInputform = inflater.inflate(R.layout.password_inputform, null) as LinearLayout
                llPasswordLayout!!.addView(llPasswordInputform)
                llPasswordFrame = llPasswordInputform!!.findViewById(R.id.ll_password_frame)
                llPasswordTitle = llPasswordFrame.findViewById(R.id.ll_password_title)
                llPasswordContents = llPasswordFrame.findViewById(R.id.ll_password_contents)
                etPasswordContents = llPasswordContents.findViewById(R.id.et_password_contents)
                btDelete = llPasswordTitle.findViewById(R.id.bt_delete)
                btDelete.setOnClickListener(DeleteButton(this@PasswordMemo, llPasswordLayout, llPasswordInputform, TABLE))
                btClip = llPasswordContents.findViewById(R.id.bt_clip)
                btClip.setOnClickListener(ClipButtonListener(context, etPasswordContents, cm))
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
        for (i in 0 until llPasswordLayout!!.childCount) {
            val llPasswordLayout = findViewById<LinearLayout>(R.id.ll_password_layout)
            val linearLayout = llPasswordLayout.getChildAt(i) as LinearLayout
            etPasswordTitle = linearLayout.findViewById(R.id.et_password_title)
            etPasswordContents = linearLayout.findViewById(R.id.et_password_contents)
            strPasswordTitle = etPasswordTitle.getText().toString()
            strPasswordContents = etPasswordContents.getText().toString()
            val control2 = DatabaseControl(context, TABLE, i, _CATEGORY, strPasswordTitle, strPasswordContents)
            control2.insertDatabaseTwoColumns("passwordtitle", "passwordcontents")
        }
        finish()
    }

    companion object {
        private const val _CATEGORY = "PASSWORD"

        /**
         * データベースのテーブル名
         */
        private const val TABLE = "password"
    }
}

internal class CopyClipbord(label: CharSequence?, mimeTypes: Array<String?>?, item: Item?) : ClipData(label, mimeTypes, item)

/**
 * コピーアイコンのボタンが押されたときに、テキストを取得してクリップボードに保存するクラス
 *
 * @author nakayama
 * @version 1.0
 */
internal class ClipButtonListener(var context: Context, var editText: EditText?, var cm: ClipboardManager?) : View.OnClickListener {
    override fun onClick(v: View) {
        val item = ClipData.Item(editText!!.text)
        val mimeType = arrayOfNulls<String>(1)
        mimeType[0] = ClipDescription.MIMETYPE_TEXT_PLAIN
        val copy = CopyClipbord("password", mimeType, item)
        cm!!.setPrimaryClip(copy)
        Toast.makeText(context, "クリップボードにコピーしました", Toast.LENGTH_SHORT).show()
    }
}