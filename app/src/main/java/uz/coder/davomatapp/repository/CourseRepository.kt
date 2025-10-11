package uz.coder.davomatapp.repository

import kotlinx.coroutines.flow.Flow
import uz.coder.davomatapp.model.Course

interface CourseRepository {
    fun getAllCourses(userId: Long): Flow<List<Course>>
}