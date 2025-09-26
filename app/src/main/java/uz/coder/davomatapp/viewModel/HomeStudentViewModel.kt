package uz.coder.davomatapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.coder.davomatapp.model.Group
import uz.coder.davomatapp.repositoryImpl.StudentRepositoryImpl
import uz.coder.davomatapp.usecase.student.SeeCoursesUseCase
import uz.coder.davomatapp.viewModel.state.HomeStudentState

class HomeStudentViewModel(application: Application): AndroidViewModel(application) {
    private val repository = StudentRepositoryImpl()
    private val seeCoursesUseCase = SeeCoursesUseCase(repository)
    private val _state = MutableStateFlow<HomeStudentState>(HomeStudentState.Init)
    val state = _state.asStateFlow()

    fun seeCourses(userId: Long){
        viewModelScope.launch {
            _state.emit(HomeStudentState.Loading)
            seeCoursesUseCase(userId).catch {
                _state.emit(HomeStudentState.Error(it.message.toString()))
            }.collect{
                _state.emit(HomeStudentState.Success(it))
            }
        }
    }

    fun clicked(item: List<Group>) {
        viewModelScope.launch{
            _state.emit(HomeStudentState.GroupsClicked(item))
        }
    }
}