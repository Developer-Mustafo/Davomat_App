package uz.coder.davomatapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import uz.coder.davomatapp.data.course.CourseRepositoryImpl
import uz.coder.davomatapp.domain.coure.AddCourseUseCase
import uz.coder.davomatapp.domain.coure.DeleteCourseUseCase
import uz.coder.davomatapp.domain.coure.GetCourseListUseCase

class CourseViewModel(application: Application):AndroidViewModel(application) {
    private val repository = CourseRepositoryImpl(application)
    private val getCourseListUseCase = GetCourseListUseCase(repository)
    private val deleteCourseUseCase = DeleteCourseUseCase(repository)
    val list = getCourseListUseCase()
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