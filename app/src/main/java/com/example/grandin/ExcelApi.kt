package com.example.grandin

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ExcelApi {
    @GET("excel")  // Make sure to use the correct endpoint
    fun getExcelData(): Call<ExcelResponse>
}

interface LoginApi {
    @POST("agent/login")  // Make sure to use the correct endpoint
    fun getLoginData(@Body loginRequest: AgentData): Call<LoginResponse>
}


interface ApiService {
    @POST("apk") // Adjust the endpoint as necessary
    fun getupdateData(@Body data: ApiData): Call<ApiData>
}