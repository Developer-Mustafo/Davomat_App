package uz.coder.davomatapp.usecase

import uz.coder.davomatapp.repository.StudentRepository

class SeeCoursesUseCase(private val studentRepository: StudentRepository) {
    operator fun invoke(userId:Long) = studentRepository.seeCourses(userId)
}