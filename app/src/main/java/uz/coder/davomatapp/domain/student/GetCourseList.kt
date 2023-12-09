package uz.coder.davomatapp.domain.student

import androidx.lifecycle.LiveData
import uz.coder.davomatapp.domain.coure.Course

class GetCourseList(private val repository: StudentRepository) {
    operator fun invoke(id:Int):LiveData<List<Course>>{
        return repository.getAllCourse(id)
    }
}