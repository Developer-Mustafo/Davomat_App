package uz.coder.davomatapp.domain.student

class AddStudentUseCase(private val repository: StudentRepository) {
    suspend operator fun invoke(student: Student){
        repository.add(student)
    }
}