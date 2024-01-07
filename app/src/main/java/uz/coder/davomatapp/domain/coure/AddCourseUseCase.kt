package uz.coder.davomatapp.domain.coure

import javax.inject.Inject

class AddCourseUseCase @Inject constructor(private val repository: CourseRepository) {
    suspend operator fun invoke(course: Course){
        repository.addCourse(course)
    }
}