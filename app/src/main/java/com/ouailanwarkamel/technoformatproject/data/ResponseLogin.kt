package com.ouailanwarkamel.technoformatproject.data


import com.google.gson.annotations.SerializedName

data class ResponseLogin(

    @SerializedName("email")
    val email: String,
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("updated_at")
    val updatedAt: Any,
    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("error_msg")
    var error_msg: String,

    @SerializedName("error_code")
    var errorCode: Int
)