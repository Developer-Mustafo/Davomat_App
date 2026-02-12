package uz.coder.davomatapp.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token:String? = null,
    val code:Int? = null,
    val message:String? = null
)