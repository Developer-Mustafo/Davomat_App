package uz.coder.davomatapp.domain.student

class EditStudentUseCase(private val repository: StudentRepository) {
    operator fun invoke(student: Student){
        repository.update(student)
    }
}