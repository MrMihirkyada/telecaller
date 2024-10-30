package com.example.grandin.Api

import com.example.grandin.Api.ApiResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


object OkHttpHelperApi {
    private val client = OkHttpClient()

    fun fetchFilter(url: String, callback: (ApiResponse?) -> Unit) {

        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                e.printStackTrace()
                callback(null)
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.isSuccessful) {
                    response.body?.let { responseBody ->
                        val gson = Gson()
                        val type = object : TypeToken<ApiResponse>() {}.type
                        val FilterApi: ApiResponse = gson.fromJson(responseBody.charStream(), type)
                        callback(FilterApi)
                    } ?: callback(null)
                } else {
                    callback(null)
                }
            }
        })
    }
}