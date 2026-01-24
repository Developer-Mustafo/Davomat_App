package uz.coder.davomatapp.data.network.dto


import kotlinx.serialization.Serializable

@Serializable
data class GroupDTO(
    val courseId: Long? = null,
    val id: Long? = null,
    val title: String? = null
)