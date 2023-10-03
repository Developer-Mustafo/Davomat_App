package uz.coder.davomatapp.domain

class EditStudentUseCase(private val repository:StudentRepository) {
    operator fun invoke(student: Student){
        repository.update(student)
    }
}