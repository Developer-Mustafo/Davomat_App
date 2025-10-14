package uz.coder.davomatapp.network.dto

import com.google.gson.annotations.SerializedName

data class CreateGroupRequest(
    @SerializedName("title")
    val title:String? = null,
    @SerializedName("courseId")
    val courseId:Long? = null
)