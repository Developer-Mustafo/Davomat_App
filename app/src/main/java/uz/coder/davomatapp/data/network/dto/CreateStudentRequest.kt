package uz.coder.davomatapp.data.network.dto

import com.google.gson.annotations.SerializedName

data class CreateStudentRequest(
    @SerializedName("fullName")
    val fullName:String? = null,
    @SerializedName("phoneNumber")
    val phoneNumber:String? = null,
    @SerializedName("userId")
    val userId:Long? = null,
    @SerializedName("groupId")
    val groupId:Long? = null

)
