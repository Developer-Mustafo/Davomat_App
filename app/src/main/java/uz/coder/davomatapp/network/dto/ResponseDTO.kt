package uz.coder.davomatapp.network.dto

import com.google.gson.annotations.SerializedName

data class ResponseDTO <T>(
    @SerializedName("code")
    val code: Int? = null,
    @SerializedName("data")
    val `data`: T? = null,
    @SerializedName("message")
    val message: String? = null
)