package uz.coder.davomatapp.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.coder.davomatapp.domain.model.Course
import uz.coder.davomatapp.domain.model.CreateCourse

interface CourseRepository {
    fun getAllCourses(userId: Long): Flow<List<Course>>
    fun createCourse(createCourse: CreateCourse): Flow<Course>
}