package uz.coder.davomatapp.domain.coure

import androidx.lifecycle.LiveData

interface CourseRepository {
    suspend fun addCourse(course: Course)
    suspend fun deleteCourse(id:Int)
    suspend fun getByIdCourse(id: Int):Course
    fun getCourseList(id: Int):LiveData<List<Course>>
    suspend fun editCourse(course: Course)
}