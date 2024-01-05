package uz.coder.davomatapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import uz.coder.davomatapp.data.student.StudentRepositoryImpl
import uz.coder.davomatapp.domain.student.DeleteStudentUseCase
import uz.coder.davomatapp.domain.student.GetCourseByIdStudent

class StudentViewModel(application: Application) :AndroidViewModel(application) {
    private val repository = StudentRepositoryImpl(application)
    private val deleteStudentUseCase = DeleteStudentUseCase(repository)
    private val getCourseByIdStudent = GetCourseByIdStudent(repository)
    private val scope = CoroutineScope(Dispatchers.Default)

    fun delete(id: Int){
        scope.launch {
            deleteStudentUseCase(id)
        }
    }
    fun getCourseByIdStudents(id:Int) = getCourseByIdStudent(id)

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}