package uz.coder.davomatapp.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.coder.davomatapp.domain.model.CreateStudent
import uz.coder.davomatapp.domain.model.Student
import uz.coder.davomatapp.domain.model.StudentCourses
import java.io.File

interface StudentRepository {
    fun seeCourses(): Flow<List<StudentCourses>>
    fun findByGroupId(groupId: Long): Flow<Student>
    fun createStudent(createStudent: CreateStudent): Flow<Student>
    fun uploadStudentExcel(file: File): Flow<String>
}