package uz.coder.davomatapp.data.network.dto

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate

@Serializable
data class AttendanceResponse(
    val date: LocalDate? = null,
    val id: Long? = null,
    val status: String? = null,
    val studentId: Long? = null
)