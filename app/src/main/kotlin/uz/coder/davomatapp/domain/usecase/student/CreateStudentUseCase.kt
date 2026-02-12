package uz.coder.davomatapp.domain.usecase.student

import uz.coder.davomatapp.domain.model.CreateStudent
import uz.coder.davomatapp.domain.repository.StudentRepository
import javax.inject.Inject

class CreateStudentUseCase @Inject constructor(private val repository: StudentRepository) {
    operator fun invoke(createStudent: CreateStudent) = repository.createStudent(createStudent)
}