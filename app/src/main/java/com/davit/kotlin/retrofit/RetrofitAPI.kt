package com.davit.kotlin.retrofit

import com.davit.kotlin.retrofit.models.ProductModel
import com.davit.kotlin.retrofit.models.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("public-api/products/{id}")
    fun getProducts(@Path("id") id: Int): Call<ProductModel>

    @GET("api/users")
    fun getUsers(@Query("page")page:Int):Call<UserModel>
}