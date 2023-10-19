package uz.coder.davomatapp.data.student

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import uz.coder.davomatapp.data.MyMapper
import uz.coder.davomatapp.data.db.MyDatabase
import uz.coder.davomatapp.domain.student.Student
import uz.coder.davomatapp.domain.student.StudentRepository

class StudentRepositoryImpl(application: Application) : StudentRepository {
    private val db = MyDatabase.myDatabase(application).studentDao()
    private val mapper = MyMapper()
    override suspend fun add(student: Student) {
        db.add(mapper.getStudentToStudentDbModel(student))
    }

    override suspend fun update(student: Student) {
        db.add(mapper.getStudentToStudentDbModel(student))
    }

    override suspend fun delete(student: Student) {
        db.delete(mapper.getStudentToStudentDbModel(student))
    }

    override fun getAllStudentList(): LiveData<List<Student>> {
        return MediatorLiveData<List<Student>>().apply {
            addSource(db.getAllStudentList()){
                value = mapper.getStudentList(it)
            }
        }
    }
    override suspend fun getByStudentId(id: Int): Student {
        return mapper.getStudentDbModelToStudent(db.getByStudentId(id))
    }
}