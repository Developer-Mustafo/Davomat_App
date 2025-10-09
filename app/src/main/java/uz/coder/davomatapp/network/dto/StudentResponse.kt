package uz.coder.davomatapp.network.dto

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class StudentResponse(
    @SerializedName("fullName")
    val fullName: String? = null,
    @SerializedName("groupId")
    val groupId: Long? = null,
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("phoneNumber")
    val phoneNumber: String? = null,
    @SerializedName("userId")
    val userId: Long? = null,
    @SerializedName("createdDate")
    val createdDate: LocalDate? = null
)