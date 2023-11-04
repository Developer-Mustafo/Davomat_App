package uz.coder.davomatapp.domain.student

import androidx.lifecycle.LiveData


interface StudentRepository {
    suspend fun add(student: Student)
    suspend fun update(student: Student)
    suspend fun delete(student: Student)
    fun getAllStudentList():LiveData<List<Student>>
    suspend fun getByStudentId(id: Int):Student
    fun getAllCourse():LiveData<List<String>>
}
