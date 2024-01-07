package uz.coder.davomatapp.domain.student

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetCourseByIdStudent @Inject constructor(private val repository: StudentRepository) {
    operator fun invoke(id:Int):LiveData<List<Student>> = repository.getCourseByIdStudents(id)
}