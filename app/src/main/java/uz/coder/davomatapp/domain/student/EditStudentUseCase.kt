package uz.coder.davomatapp.domain.student

import javax.inject.Inject

class EditStudentUseCase @Inject constructor(private val repository: StudentRepository) {
    suspend operator fun invoke(student: Student){
        repository.update(student)
    }
}