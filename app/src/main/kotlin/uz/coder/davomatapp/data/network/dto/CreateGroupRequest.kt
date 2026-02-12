package uz.coder.davomatapp.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateGroupRequest(
    val title:String? = null,
    val courseId:Long? = null
)