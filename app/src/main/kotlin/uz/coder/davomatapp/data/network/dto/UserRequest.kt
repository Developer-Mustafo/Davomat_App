package uz.coder.davomatapp.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val email: String? = null,
    val firstName: String? = null,
    val id: Long? = null,
    val lastName: String? = null,
    val password: String? = null,
    val phoneNumber: String? = null,
    val role: String? = null
)