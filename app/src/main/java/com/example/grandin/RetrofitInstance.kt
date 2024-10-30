package com.example.grandin

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://192.168.1.4:7000/"  // Ensure this ends with a '/'

    val api: ExcelApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExcelApi::class.java)
    }

}


object RetrofitLoginInstance {
    private const val BASE_URL = "http://192.168.1.4:7000/"  // Ensure this ends with a '/'

//    private const val BASE_URL = "http://10.0.2.2:7000/"


    val api: LoginApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginApi::class.java)
    }

}


object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.4:7000/"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}


