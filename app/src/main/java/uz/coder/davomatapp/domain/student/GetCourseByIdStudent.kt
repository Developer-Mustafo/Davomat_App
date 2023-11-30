package uz.coder.davomatapp.domain.student

import androidx.lifecycle.LiveData

class GetCourseByIdStudent(private val repository: StudentRepository) {
    operator fun invoke(id:Int):LiveData<List<Student>> = repository.getCourseByIdStudents(id)
}