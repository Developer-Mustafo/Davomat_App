package uz.coder.davomatapp.repository

import kotlinx.coroutines.flow.Flow
import uz.coder.davomatapp.model.Student
import uz.coder.davomatapp.model.StudentCourses

interface StudentRepository {
    fun seeCourses(userId: Long): Flow<List<StudentCourses>>
    fun findByGroupIdAndUserId(userId: Long, groupId: Long): Flow<Student>
}