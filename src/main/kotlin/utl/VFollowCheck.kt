package org.echoosx.mirai.plugin.utl

import okhttp3.OkHttpClient
import okhttp3.Request
import org.echoosx.mirai.plugin.NickChange
import org.json.JSONObject
import java.io.IOException

fun getFollow(unameOrUid:String): String?{
    try{
        val msg = buildString {
            val url = "https://api.asoulfan.com/cfj/?name=${unameOrUid}"
            val response = httpGET(url)
            val code = JSONObject(response).getInt("code")
            if(code == 22115) return null
            val total = JSONObject(response).getJSONObject("data").getInt("total")
            if(total == 0) return null

            appendLine("B站用户【${unameOrUid}】关注的Vup共${total}位：")
            val vupList = JSONObject(response).getJSONObject("data").getJSONArray("list")
            for(vup in vupList){
                append((vup as JSONObject).getString("uname"))
                append("、")
            }
            deleteAt(length-1)
            if(vupList.length() < total) append("……")
        }
        return msg
    }catch (e:Exception){
        NickChange.logger.error(e)
        return null
    }
}

fun httpGET(url: String):String{
    var tempString = ""
    val client = OkHttpClient()
    val request = Request.Builder()
        .url(url)
        .get()
        .build()
    //同步处理
    val call = client.newCall(request)
    try {
        val response = call.execute()
        tempString = response.body?.string().toString()
    } catch (e: IOException) {
        NickChange.logger.error(e)
    }
    return tempString
}