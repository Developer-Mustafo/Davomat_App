package uz.coder.davomatapp.usecase.course

import uz.coder.davomatapp.repository.CourseRepository

class GetAllCoursesUseCase(private val repository: CourseRepository) {
    operator fun invoke(userId: Long) = repository.getAllCourses(userId)
}