package uz.coder.davomatapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class CreateCourseRequest(
    @SerializedName("title")
    val title:String? = null,
    @SerializedName("description")
    val description:String? = null,
    @SerializedName("userId")
    val userId:Long? = null
)