package uz.coder.davomatapp.domain.student

class AddStudentUseCase(private val repository: StudentRepository) {
    operator fun invoke(student: Student){
        repository.add(student)
    }
}