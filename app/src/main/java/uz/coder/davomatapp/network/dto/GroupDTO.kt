package uz.coder.davomatapp.network.dto


import com.google.gson.annotations.SerializedName

data class GroupDTO(
    @SerializedName("courseId")
    val courseId: Long? = null,
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("title")
    val title: String? = null
)