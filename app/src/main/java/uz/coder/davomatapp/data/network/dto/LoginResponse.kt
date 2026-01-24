package uz.coder.davomatapp.data.network.dto

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class LoginResponse(
    val token:String? = null,
    val code:Int? = null,
    val message:String? = null
)