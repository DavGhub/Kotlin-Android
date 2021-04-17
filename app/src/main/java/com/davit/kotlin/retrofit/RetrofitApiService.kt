package com.davit.kotlin.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL_1 = "https://gorest.co.in/"
const val BASE_URL_2 = "https://reqres.in/"

object RetrofitApiService{
    private var retrofit: Retrofit? = null

    fun getService(baseUrl: String): Retrofit? {
//        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
//        }
        return retrofit
    }
}