package uz.coder.davomatapp.network.dto


import com.google.gson.annotations.SerializedName

data class StudentCoursesDTO(
    @SerializedName("course")
    val courseDTO: CourseDTO? = null,
    @SerializedName("group")
    val groupDTO: GroupDTO? = null
)