package uz.coder.davomatapp.domain.student

class DeleteStudentUseCase(private val repository: StudentRepository) {
    operator fun invoke(student: Student){
        repository.delete(student)
    }
}