package com.websarva.wings.android.dowasurememo

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.eclipsesource.json.Json
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * 郵便番号検索APIをたたいて、取得した住所を表示するクラス
 *
 * @author nakayama
 * @version 1.0
 */
class PostNumberAPIClient(_context: Context) {

    object HttpClient {

        val instance = OkHttpClient()
    }

    val context = _context

    /**
     * getPostNumberメソッド
     * EditTextに入力された郵便番号を取得して、URIに付け足す
     * @param etPostNumber1　郵便番号の最初の３桁を入力するEditText
     * @param etPostNumber2　郵便番号の後半の４桁を入力するEditText
     * @param etAddressDetail　郵便番号検索APIで取得してきた住所を表示するEditText
     */
//    fun getPostNumber(postNumber1: String, etPostNumber2: EditText, etAddressDetail: EditText) {
//        val POSTNUMBER = "${etPostNumber1.text}${etPostNumber2.text}"
//        val POSTNUMBER_URI = "https://zipcloud.ibsnet.co.jp/api/search?zipcode=${POSTNUMBER}"
//
//        receivePostNumberInfo(POSTNUMBER_URI, etAddressDetail)
//
//    }


    fun httpGet(url: String): String? {
        val request = Request.Builder()
                .url(url)
                .build()

        val response = HttpClient.instance.newCall(request).execute()
        val body = response.body?.string()
        return body
    }


    fun receivePostNumberInfo(postNumber1: String, postNumber2: String): String = runBlocking {


        val POSTNUMBER_URI = "https://zipcloud.ibsnet.co.jp/api/search?zipcode=${postNumber1}${postNumber2}"

        var addressText = ""

        try {
            GlobalScope.async(Dispatchers.Default) {
                httpGet(POSTNUMBER_URI)
            }.await().let {
                val result = Json.parse(it).asObject()

                val prefecture = result.get("results").asArray()[0].asObject().get("address1").asString()
                val city = result.get("results").asArray()[0].asObject().get("address2").asString()
                val town = result.get("results").asArray()[0].asObject().get("address3").asString()

                addressText = "${prefecture}${city}${town}"
                Log.d("main", "$addressText")
            }

        } catch (e: UnsupportedOperationException) {
            Toast.makeText(context, "この郵便番号に該当する住所はありません", Toast.LENGTH_SHORT).show()
        }
        
        return@runBlocking addressText

    }
}