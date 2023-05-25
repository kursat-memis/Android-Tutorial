package com.kursatmemis.retrofit.models

import com.google.gson.annotations.SerializedName

data class UserModel(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    @SerializedName("body")
    val comment: String
)
