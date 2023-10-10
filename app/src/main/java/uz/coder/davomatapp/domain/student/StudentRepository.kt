package uz.coder.davomatapp.domain.student

import androidx.lifecycle.LiveData


interface StudentRepository {
    fun add(student: Student)
    fun update(student: Student)
    fun delete(id: Int)
    fun getAllStudentList():LiveData<List<Student>>
    fun getByStudentId(id: Int):Student?
}
