package uz.coder.davomatapp.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateCourseRequest(
    val title:String? = null,
    val description:String? = null
)