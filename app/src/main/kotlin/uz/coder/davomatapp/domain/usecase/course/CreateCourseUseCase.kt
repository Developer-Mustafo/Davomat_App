package uz.coder.davomatapp.domain.usecase.course

import uz.coder.davomatapp.domain.model.CreateCourse
import uz.coder.davomatapp.domain.repository.CourseRepository
import javax.inject.Inject

class CreateCourseUseCase @Inject constructor(private val response: CourseRepository) {
    operator fun invoke(createCourse: CreateCourse) = response.createCourse(createCourse)
}