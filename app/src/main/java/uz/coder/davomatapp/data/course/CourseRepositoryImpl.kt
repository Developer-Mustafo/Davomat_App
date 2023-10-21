package uz.coder.davomatapp.data.course

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import uz.coder.davomatapp.data.db.MyDatabase
import uz.coder.davomatapp.data.mapper.CourseMapper
import uz.coder.davomatapp.domain.coure.Course
import uz.coder.davomatapp.domain.coure.CourseRepository

class CourseRepositoryImpl(application: Application):CourseRepository {
    private val mapper = CourseMapper()
    private val db = MyDatabase.myDatabase(application).courseDao()
    override suspend fun addCourse(course: Course) {
        db.addCourse(mapper.getCourseToCourseDbModel(course))
    }

    override suspend fun editCourse(course: Course) {
        db.addCourse(mapper.getCourseToCourseDbModel(course))
    }

    override suspend fun deleteCourse(id: Int) {
        db.deleteCourse(id)
    }

    override suspend fun getByIdCourse(id: Int): Course {
        return mapper.getCourseDbModelToCourse(db.getByIdCourse(id))
    }

    override fun getCourseList(): LiveData<List<Course>> {
        return MediatorLiveData<List<Course>>().apply {
            addSource(db.getCourseList()){
                mapper.getCourseList(it)
            }
        }
    }
}