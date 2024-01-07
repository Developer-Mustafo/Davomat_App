package uz.coder.davomatapp.presentation.viewmodel


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import uz.coder.davomatapp.domain.coure.DeleteCourseUseCase
import uz.coder.davomatapp.domain.coure.GetCourseListUseCase
import javax.inject.Inject

class CourseViewModel @Inject constructor(
    private val getCourseListUseCase : GetCourseListUseCase,
    private val deleteCourseUseCase : DeleteCourseUseCase
): ViewModel() {
    fun list(id: Int) = getCourseListUseCase(id)
    private val scope = CoroutineScope(Dispatchers.Default)
    fun deleteCourse(id:Int){
        scope.launch {
            deleteCourseUseCase(id)
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}