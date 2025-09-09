package uz.coder.davomatapp.repository

import kotlinx.coroutines.flow.Flow
import uz.coder.davomatapp.model.StudentCourses

interface StudentRepository {
    fun seeCourses(userId: Long): Flow<List<StudentCourses>>
}