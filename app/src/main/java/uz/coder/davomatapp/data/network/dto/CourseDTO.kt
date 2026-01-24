package uz.coder.davomatapp.data.network.dto


import kotlinx.serialization.Serializable

@Serializable
data class CourseDTO(
    val description: String? = null,
    val id: Long? = null,
    val title: String? = null,
    val userId: Long? = null
)