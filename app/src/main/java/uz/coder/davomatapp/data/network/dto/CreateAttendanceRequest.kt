package uz.coder.davomatapp.data.network.dto

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class CreateAttendanceRequest(
    @SerializedName("studentId")
    val studentId:Long? = null,
    @SerializedName("date")
    val date: LocalDate?=null,
    @SerializedName("status")
    val status:String?=null
)
