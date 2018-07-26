package com.verygoodsecurity.samples.http

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface HttpbinService {
    @POST("post")
    fun post(@Body data: PiiData): Call<ResponseBody>

    @POST("post")
    fun post(@Body data: CardData): Call<ResponseBody>

    @POST("post")
    fun post(@Body data: BankData): Call<ResponseBody>
}