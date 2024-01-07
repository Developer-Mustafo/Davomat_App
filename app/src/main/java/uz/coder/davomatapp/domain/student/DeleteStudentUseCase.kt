package uz.coder.davomatapp.domain.student

import javax.inject.Inject

class DeleteStudentUseCase @Inject constructor(private val repository: StudentRepository) {
    suspend operator fun invoke(id:Int){
        repository.delete(id)
    }
}