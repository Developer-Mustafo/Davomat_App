package uz.coder.davomatapp.data.student

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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

    override fun delete(id: Int) {
        db.delete(id)
    }

    override fun getAllStudentList(): LiveData<List<Student>> {
        return MediatorLiveData<List<Student>>().apply {
            addSource(db.getAllStudentList()){
                value = it
            }
        }
    }
    override fun getByStudentId(id: Int): Student? {
        return db.getByStudentId(id)
    }
}