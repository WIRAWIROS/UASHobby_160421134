package com.ubaya.hobbyuts_160421134.model

data class Hobby(
    val id:String?,
    @SerializedName("name")
    val name:String?,
    @SerializedName("birth_of_date")
    val bod:String?,
    @SerializedName("phone_number")
    val phone:String?,
    @SerializedName("photo_url")
    val photoUrl:String
