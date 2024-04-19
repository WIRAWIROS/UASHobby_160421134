package com.ubaya.hobbyuts_160421134.model

import com.google.gson.annotations.SerializedName

data class User(
    val id:String?,
    @SerializedName("namaDepan")
    val namaDepan:String?,
    @SerializedName("namaBelakang")
    val namaBelakang:String?,
    @SerializedName("email")
    val email:String?,
    @SerializedName("username")
    val username:String?,
    @SerializedName("password")
    val password:String?)
