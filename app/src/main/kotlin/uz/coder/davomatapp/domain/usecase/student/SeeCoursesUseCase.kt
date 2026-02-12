package uz.coder.davomatapp.domain.usecase.student

import uz.coder.davomatapp.domain.repository.StudentRepository
import javax.inject.Inject

class SeeCoursesUseCase @Inject constructor(private val studentRepository: StudentRepository) {
    operator fun invoke() = studentRepository.seeCourses()
}