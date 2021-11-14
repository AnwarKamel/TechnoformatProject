package com.ouailanwarkamel.technoformatproject.api

import com.ouailanwarkamel.technoformatproject.data.ResponseLogin
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @FormUrlEncoded
    @POST("login.php")
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseLogin>


    @FormUrlEncoded
    @POST("register.php")
    fun createUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseLogin>



    companion object {
        val URL: String = "http://192.168.0.115/api_android/"
    }


}