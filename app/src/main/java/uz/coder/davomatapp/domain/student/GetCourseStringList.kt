package uz.coder.davomatapp.domain.student

import androidx.lifecycle.LiveData

class GetCourseStringList(private val repository: StudentRepository) {
    operator fun invoke():LiveData<List<String>>{
        return repository.getAllCourse()
    }
}