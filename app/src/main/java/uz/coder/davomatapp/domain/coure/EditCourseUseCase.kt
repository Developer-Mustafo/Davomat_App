package uz.coder.davomatapp.domain.coure

import javax.inject.Inject

class EditCourseUseCase @Inject constructor(private val repository: CourseRepository) {
    suspend operator fun invoke(course: Course){
        repository.editCourse(course)
    }
}