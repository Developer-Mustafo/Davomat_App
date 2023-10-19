package uz.coder.davomatapp.domain.student

class GetStudentByIdUseCase(private val repository: StudentRepository) {
    suspend operator fun invoke(id:Int): Student? {
        return repository.getByStudentId(id)
    }
}