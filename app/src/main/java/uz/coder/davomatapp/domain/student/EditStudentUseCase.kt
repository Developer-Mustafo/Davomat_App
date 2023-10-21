package uz.coder.davomatapp.domain.student

class EditStudentUseCase(private val repository: StudentRepository) {
    suspend operator fun invoke(student: Student){
        repository.update(student)
    }
}