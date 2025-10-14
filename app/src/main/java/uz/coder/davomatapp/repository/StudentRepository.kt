package uz.coder.davomatapp.repository

import kotlinx.coroutines.flow.Flow
import uz.coder.davomatapp.model.CreateStudent
import uz.coder.davomatapp.model.Student
import uz.coder.davomatapp.model.StudentCourses
import java.io.File

interface StudentRepository {
    fun seeCourses(userId: Long): Flow<List<StudentCourses>>
    fun findByGroupIdAndUserId(userId: Long, groupId: Long): Flow<Student>
    fun createStudent(createStudent: CreateStudent): Flow<Student>
    fun uploadStudentExcel(file: File, userId: Long): Flow<String>
}