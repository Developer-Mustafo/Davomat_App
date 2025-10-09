package uz.coder.davomatapp.map

import uz.coder.davomatapp.model.Course
import uz.coder.davomatapp.model.Group
import uz.coder.davomatapp.model.Student
import uz.coder.davomatapp.model.StudentCourses
import uz.coder.davomatapp.network.dto.StudentCoursesDTO
import uz.coder.davomatapp.network.dto.StudentResponse
import java.time.LocalDate

class StudentMap{
    fun toStudentCourse(studentCoursesDTO: StudentCoursesDTO?) = StudentCourses(
            course = Course(
                description = studentCoursesDTO?.courseDTO?.description?:"",
                id = studentCoursesDTO?.courseDTO?.id?:0L,
                title = studentCoursesDTO?.courseDTO?.title?:"",
                userId = studentCoursesDTO?.courseDTO?.userId?:0L
            ),
            group = studentCoursesDTO?.groupDTO?.map {
                Group(
                    courseId = it.courseId?:0L,
                    id = it.id?:0L,
                    title = it.title?:""
                )
            }?:emptyList()
        )

    fun toStudentCourses(data: List<StudentCoursesDTO>?) = data?.map { toStudentCourse(it) }?:emptyList()
    fun toStudent(data: StudentResponse?) = Student( fullName = data?.fullName?:"",  groupId = data?.groupId?:0L,  id = data?.id?:0L,  phoneNumber = data?.phoneNumber?:"",  userId = data?.userId?:0L, createdDate = data?.createdDate?: LocalDate.now())
}
