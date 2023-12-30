package uz.coder.davomatapp.domain.student

import androidx.lifecycle.LiveData
import uz.coder.davomatapp.domain.coure.Course


interface StudentRepository {
    suspend fun add(student: Student)
    suspend fun update(student: Student)
    suspend fun delete(id: Int)
    fun getAllStudentList():LiveData<List<Student>>
    suspend fun getByStudentId(id: Int):Student
    fun getAllCourse(id: Int):LiveData<List<Course>>
    fun getCourseByIdStudents(id: Int):LiveData<List<Student>>
}
