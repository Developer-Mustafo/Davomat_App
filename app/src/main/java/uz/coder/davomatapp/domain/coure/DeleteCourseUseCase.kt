package uz.coder.davomatapp.domain.coure

import javax.inject.Inject

class DeleteCourseUseCase @Inject constructor(private val repository: CourseRepository) {
    suspend operator fun invoke(id:Int){
        repository.deleteCourse(id)
    }
}