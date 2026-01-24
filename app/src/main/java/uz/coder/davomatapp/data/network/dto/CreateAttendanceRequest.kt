package uz.coder.davomatapp.data.network.dto

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class CreateAttendanceRequest(
    val studentId:Long? = null,
    val date: LocalDate?=null,
    val status:String?=null
)
