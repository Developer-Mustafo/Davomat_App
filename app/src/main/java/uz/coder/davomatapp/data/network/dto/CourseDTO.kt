package uz.coder.davomatapp.data.network.dto


import com.google.gson.annotations.SerializedName

data class CourseDTO(
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("userId")
    val userId: Long? = null
)