package uz.coder.davomatapp.usecase.student

import uz.coder.davomatapp.model.CreateStudent
import uz.coder.davomatapp.repository.StudentRepository

class CreateStudentUseCase(private val repository: StudentRepository) {
    operator fun invoke(createStudent: CreateStudent) = repository.createStudent(createStudent)
}