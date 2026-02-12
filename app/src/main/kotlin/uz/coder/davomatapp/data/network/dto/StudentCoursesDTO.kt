package uz.coder.davomatapp.data.network.dto


import kotlinx.serialization.Serializable

@Serializable
data class StudentCoursesDTO(
    val courseDTO: CourseDTO? = null,
    val groupDTO: List<GroupDTO>? = null
)