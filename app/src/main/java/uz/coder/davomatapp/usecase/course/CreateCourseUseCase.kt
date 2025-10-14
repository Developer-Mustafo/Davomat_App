package uz.coder.davomatapp.usecase.course

import uz.coder.davomatapp.model.CreateCourse
import uz.coder.davomatapp.repository.CourseRepository

class CreateCourseUseCase(private val response: CourseRepository) {
    operator fun invoke(createCourse: CreateCourse) = response.createCourse(createCourse)
}