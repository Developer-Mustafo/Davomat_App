package uz.coder.davomatapp.data.network.dto

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class UserResponse(
    val email: String? = null,
    val firstName: String? = null,
    val id: Long? = null,
    val lastName: String? = null,
    val password: String? = null,
    val phoneNumber: String? = null,
    val role: String? = null,
    val payedDate: LocalDate? = null
)