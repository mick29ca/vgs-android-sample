package com.verygoodsecurity.samples.http

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface HttpbinService {
    @POST("post")
    fun post(@Body data: PiiData): Call<HttpbinResponse<PiiData>>

    @POST("post")
    fun post(@Body data: CardData): Call<HttpbinResponse<CardData>>

    @POST("post")
    fun post(@Body data: BankData): Call<HttpbinResponse<BankData>>
}