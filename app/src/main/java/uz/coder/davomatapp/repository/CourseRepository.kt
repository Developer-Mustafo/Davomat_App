package uz.coder.davomatapp.repository

import kotlinx.coroutines.flow.Flow
import uz.coder.davomatapp.model.Course
import uz.coder.davomatapp.model.CreateCourse

interface CourseRepository {
    fun getAllCourses(userId: Long): Flow<List<Course>>
    fun createCourse(createCourse: CreateCourse): Flow<Course>
}