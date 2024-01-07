package uz.coder.davomatapp.domain.coure

import javax.inject.Inject

class GetCourseByIdUseCase @Inject constructor(private val repository: CourseRepository) {
    suspend operator fun invoke(id:Int):Course{
        return repository.getByIdCourse(id)
    }
}