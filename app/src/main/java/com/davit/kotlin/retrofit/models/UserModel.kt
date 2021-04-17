package com.davit.kotlin.retrofit.models

class UserModel {

    var page:Int? = null
    var per_page:Int? = null
    var total:Int? = null
    var total_pages:Int? = null
    var data:MutableList<User>? = null

    data class User(
        var id:Int,
        var email:String,
        var first_name:String,
        var last_name:String,
        var avatar:String
        )
}