package uz.coder.davomatapp.map

import uz.coder.davomatapp.model.Course
import uz.coder.davomatapp.model.Group
import uz.coder.davomatapp.model.StudentCourses
import uz.coder.davomatapp.network.dto.StudentCoursesDTO

class StudentMap{
    fun toStudentCourse(studentCoursesDTO: StudentCoursesDTO?) = StudentCourses(
            course = Course(
                description = studentCoursesDTO?.courseDTO?.description?:"",
                id = studentCoursesDTO?.courseDTO?.id?:0L,
                title = studentCoursesDTO?.courseDTO?.title?:"",
                userId = studentCoursesDTO?.courseDTO?.userId?:0L
            ),
            group = Group(
                courseId = studentCoursesDTO?.groupDTO?.courseId?:0L,
                id = studentCoursesDTO?.groupDTO?.id?:0L,
                title = studentCoursesDTO?.groupDTO?.title?:""
            )
        )

    fun toStudentCourses(data: List<StudentCoursesDTO>?) = data?.map { toStudentCourse(it) }?:emptyList()
}
