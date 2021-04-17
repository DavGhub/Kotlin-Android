package com.davit.kotlin.retrofit.models

class ProductModel {

    var code:Int? = null
    var meta:String? = null
    var data: Data? = null

    data class Data(
        var id:Int,
        var name:String,
        var description:String,
        var image:String,
        var price:String,
        var discount_amount:String,
        var status:Boolean,
        var categories:MutableList<Category>
        ){

        data class Category(var id:Int,var name:String)
    }
}