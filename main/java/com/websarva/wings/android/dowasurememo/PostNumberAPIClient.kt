package com.websarva.wings.android.dowasurememo

import android.content.Context
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
     * httpGetメソッド
     * HTTP接続をしてJSONデータを持ってくる
     * @param url　API接続をするためのURI
     * @return JSONデータ
     */
    fun httpGet(url: String): String? {
        val request = Request.Builder()
                .url(url)
                .build()

        val response = HttpClient.instance.newCall(request).execute()
        val body = response.body?.string()
        return body
    }


    /**
     * receivePostNumberInfoメソッド
     * JSONデータから県、市、町の住所を文字列として取得する
     * @param postNumber1　郵便番号の最初の３桁
     * @param postNumber2　郵便番号の最後の４桁
     * @return 郵便番号検索をして取得した住所の文字列
     */
    fun receivePostNumberInfo(postNumber1: String, postNumber2: String): String = runBlocking {


        val POSTNUMBER_URI = "https://zipcloud.ibsnet.co.jp/api/search?zipcode=${postNumber1}${postNumber2}"

        var addressText = ""

        if (postNumber1.length == 3 && postNumber2.length == 4) {
            try {
                GlobalScope.async(Dispatchers.Default) {
                    httpGet(POSTNUMBER_URI)
                }.await().let {
                    val result = Json.parse(it).asObject()

                    val prefecture = result.get("results").asArray()[0].asObject().get("address1").asString()
                    val city = result.get("results").asArray()[0].asObject().get("address2").asString()
                    val town = result.get("results").asArray()[0].asObject().get("address3").asString()

                    addressText = "${prefecture}${city}${town}"
                }

            } catch (e: UnsupportedOperationException) {
                Toast.makeText(context, R.string.postnumber_search_failed, Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(context, R.string.postnumber_inputform_incorrect, Toast.LENGTH_SHORT).show()
        }




        return@runBlocking addressText

    }
}