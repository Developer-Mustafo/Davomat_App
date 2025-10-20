package uz.coder.davomatapp.data.map

import uz.coder.davomatapp.domain.model.Course
import uz.coder.davomatapp.domain.model.CreateStudent
import uz.coder.davomatapp.domain.model.Group
import uz.coder.davomatapp.domain.model.Student
import uz.coder.davomatapp.domain.model.StudentCourses
import uz.coder.davomatapp.data.network.dto.CreateStudentRequest
import uz.coder.davomatapp.data.network.dto.StudentCoursesDTO
import uz.coder.davomatapp.data.network.dto.StudentResponse
import java.time.LocalDate
import javax.inject.Inject

class StudentMap @Inject constructor() {
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
    fun toCreateStudentRequest(createStudent: CreateStudent) = CreateStudentRequest(fullName = createStudent.fullName, phoneNumber = createStudent.phoneNumber, userId = createStudent.userId, groupId = createStudent.groupId)
}
