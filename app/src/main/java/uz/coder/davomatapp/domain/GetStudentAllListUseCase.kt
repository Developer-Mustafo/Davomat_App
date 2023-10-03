package uz.coder.davomatapp.domain

class GetStudentAllListUseCase(private val repository:StudentRepository) {
    operator fun invoke():List<Student>{
        return repository.getAllStudentList()
    }
}