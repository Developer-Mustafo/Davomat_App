package uz.coder.davomatapp.domain

class GetStudentByIdUseCase(private val repository:StudentRepository) {
    operator fun invoke(id:Int):Student{
        return repository.getByStudentId(id)
    }
}