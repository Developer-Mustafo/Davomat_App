package uz.coder.davomatapp.domain.model

data class StudentCourses(
    val course: Course,
    val group: List<Group>
)