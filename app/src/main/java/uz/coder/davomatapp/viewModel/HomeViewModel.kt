package uz.coder.davomatapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.coder.davomatapp.repositoryImpl.CourseRepositoryImpl
import uz.coder.davomatapp.usecase.course.GetAllCoursesUseCase
import uz.coder.davomatapp.viewModel.state.HomeState

class HomeViewModel(application: Application): AndroidViewModel(application) {
    private val repository = CourseRepositoryImpl()
    private val getAllCoursesUseCase = GetAllCoursesUseCase(repository)
    private val _state = MutableStateFlow<HomeState>(HomeState.Init)
    val state = _state.asStateFlow()
    fun getAllCourses(userId: Long){
        viewModelScope.launch {
            _state.emit(HomeState.Loading)
            getAllCoursesUseCase(userId).catch {
                _state.emit(HomeState.Error(it.message?:""))
            }.collect {
                _state.emit(HomeState.Success(it))
            }
        }
    }
}