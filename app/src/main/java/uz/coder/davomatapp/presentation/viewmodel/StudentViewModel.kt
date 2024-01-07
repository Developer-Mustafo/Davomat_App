package uz.coder.davomatapp.presentation.viewmodel


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import uz.coder.davomatapp.domain.student.DeleteStudentUseCase
import uz.coder.davomatapp.domain.student.GetCourseByIdStudent
import javax.inject.Inject

class StudentViewModel @Inject constructor(
    private val deleteStudentUseCase : DeleteStudentUseCase,
    private val getCourseByIdStudent : GetCourseByIdStudent
) : ViewModel() {
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