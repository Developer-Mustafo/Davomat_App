package uz.coder.davomatapp.domain.coure

import androidx.lifecycle.LiveData

class GetCourseListUseCase(private val repository: CourseRepository) {
    operator fun invoke():LiveData<List<Course>>{
        return repository.getCourseList()
    }
}