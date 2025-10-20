package uz.coder.davomatapp.domain.usecase.course

import uz.coder.davomatapp.domain.repository.CourseRepository
import javax.inject.Inject

class GetAllCoursesUseCase @Inject constructor(private val repository: CourseRepository) {
    operator fun invoke(userId: Long) = repository.getAllCourses(userId)
}