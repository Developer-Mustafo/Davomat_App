package uz.coder.davomatapp.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateStudentRequest(
    val fullName:String? = null,
    val phoneNumber:String? = null,
    val groupId:Long? = null
)
