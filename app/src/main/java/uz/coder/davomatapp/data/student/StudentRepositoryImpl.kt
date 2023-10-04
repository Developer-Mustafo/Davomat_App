package uz.coder.davomatapp.data.student

import android.app.Application
import uz.coder.davomatapp.data.db.MyDatabase
import uz.coder.davomatapp.domain.student.Student
import uz.coder.davomatapp.domain.student.StudentRepository

class StudentRepositoryImpl(private val application: Application) : StudentRepository {
    private val db = MyDatabase.getInstanse(application).studentDao()
    override fun add(student: Student) {
        db.add(student)
    }

    override fun update(student: Student) {
        db.update(student)
    }

    override fun delete(student: Student) {
        db.delete(student)
    }

    override fun getAllStudentList(): List<Student> {
        return db.getAllStudentList()
    }

    override fun getByStudentId(id: Int): Student {
        return db.getByStudentId(id)
    }
}