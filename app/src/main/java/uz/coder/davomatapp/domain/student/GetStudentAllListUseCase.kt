package uz.coder.davomatapp.domain.student

class GetStudentAllListUseCase(private val repository: StudentRepository) {
    operator fun invoke():List<Student>{
        return repository.getAllStudentList()
    }
}