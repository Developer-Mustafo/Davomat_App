package uz.coder.davomatapp.data.student

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import uz.coder.davomatapp.data.mapper.StudentMapper
import uz.coder.davomatapp.data.db.MyDatabase
import uz.coder.davomatapp.data.mapper.CourseMapper
import uz.coder.davomatapp.domain.coure.Course
import uz.coder.davomatapp.domain.student.Student
import uz.coder.davomatapp.domain.student.StudentRepository

class StudentRepositoryImpl(application: Application) : StudentRepository {
    private val db = MyDatabase.myDatabase(application).studentDao()
    private val dbCourse = MyDatabase.myDatabase(application).courseDao()
    private val mapper = StudentMapper()
    private val courseMapper = CourseMapper()
    override suspend fun add(student: Student) {
        db.add(mapper.getStudentToStudentDbModel(student))
    }

    override suspend fun update(student: Student) {
        db.add(mapper.getStudentToStudentDbModel(student))
    }

    override suspend fun delete(student: Student) {
        val id = student.id
        db.delete(id)
    }

    override fun getAllStudentList(): LiveData<List<Student>> {
        return MediatorLiveData<List<Student>>().apply {
            addSource(db.getAllStudentList()){
                value = mapper.getStudentList(it)
            }
        }
    }
    override suspend fun getByStudentId(id: Int): Student {
        return try {
            mapper.getStudentDbModelToStudent(db.getByStudentId(id))
        }catch (e:Exception){
            Student(name="", surname = "", age = 0, phone = "", gender = "", course = "")
        }
    }

    override fun getAllCourse(id: Int): LiveData<List<Course>> {
        return MediatorLiveData<List<Course>>().apply {
            addSource(dbCourse.getCourseList(id)){
                value = courseMapper.getCourseList(it)
            }
        }
    }

    override fun getCourseByIdStudents(id: Int): LiveData<List<Student>> {
        return MediatorLiveData<List<Student>>().apply {
            addSource(db.getCourseByIdStudents(id)){
                value = mapper.getStudentList(it)
            }
        }
    }
}