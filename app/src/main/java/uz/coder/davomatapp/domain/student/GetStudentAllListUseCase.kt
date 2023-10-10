package uz.coder.davomatapp.domain.student

import androidx.lifecycle.LiveData

class GetStudentAllListUseCase(private val repository: StudentRepository) {
    operator fun invoke():LiveData<List<Student>>{
        return repository.getAllStudentList()
    }
}