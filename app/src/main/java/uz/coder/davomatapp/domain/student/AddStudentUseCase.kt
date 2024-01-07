package uz.coder.davomatapp.domain.student

import javax.inject.Inject

class AddStudentUseCase @Inject constructor(private val repository: StudentRepository) {
    suspend operator fun invoke(student: Student){
        repository.add(student)
    }
}