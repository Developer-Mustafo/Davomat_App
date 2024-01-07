package uz.coder.davomatapp.domain.coure

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetCourseListUseCase @Inject constructor(private val repository: CourseRepository) {
    operator fun invoke(id:Int):LiveData<List<Course>>{
        return repository.getCourseList(id)
    }
}