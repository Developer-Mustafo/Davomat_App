package uz.coder.davomatapp.domain.student

import javax.inject.Inject

class GetStudentByIdUseCase @Inject constructor(private val repository: StudentRepository) {
    suspend operator fun invoke(id:Int): Student {
        return repository.getByStudentId(id)
    }
}