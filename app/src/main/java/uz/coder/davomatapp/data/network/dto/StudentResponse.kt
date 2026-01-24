package uz.coder.davomatapp.data.network.dto

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class StudentResponse(
    val fullName: String? = null,
    val groupId: Long? = null,
    val id: Long? = null,
    val phoneNumber: String? = null,
    val userId: Long? = null,
    val createdDate: LocalDate? = null
)