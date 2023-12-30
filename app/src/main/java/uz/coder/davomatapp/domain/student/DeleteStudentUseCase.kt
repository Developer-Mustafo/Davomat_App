package uz.coder.davomatapp.domain.student

class DeleteStudentUseCase(private val repository: StudentRepository) {
    suspend operator fun invoke(id:Int){
        repository.delete(id)
    }
}