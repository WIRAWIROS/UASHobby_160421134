package com.ubaya.hobbyuts_160421134.model
import com.google.gson.annotations.SerializedName
data class Hobby(
    val id:String?,
    @SerializedName("judul")
    val judul:String?,
    @SerializedName("nama")
    val nama:String?,
    @SerializedName("desc")
    val desc:String?,
    @SerializedName("paragraf")
    val paragraf:String?,
    @SerializedName("photo_url")
    val photoUrl:String?)
