package uz.coder.davomatapp.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResponseDTO <T>(
    val code: Int? = null,
    val `data`: T? = null,
    val message: String? = null
)