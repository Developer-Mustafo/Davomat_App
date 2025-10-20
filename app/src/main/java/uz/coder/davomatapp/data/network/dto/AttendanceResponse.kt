package uz.coder.davomatapp.data.network.dto

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class AttendanceResponse(
    @SerializedName("date")
    val date: LocalDate? = null,
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("studentId")
    val studentId: Long? = null
)