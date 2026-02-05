package uz.coder.davomatapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.coder.davomatapp.domain.model.CreateCourse
import uz.coder.davomatapp.domain.usecase.course.CreateCourseUseCase
import uz.coder.davomatapp.presentation.viewModel.state.CreateCourseState
import javax.inject.Inject

@HiltViewModel
class CreateCourseViewModel @Inject constructor(
    private val createCourseUseCase: CreateCourseUseCase
) : ViewModel() {
    
    private val _state = MutableStateFlow<CreateCourseState>(CreateCourseState.Init)
    val state = _state.asStateFlow()

    fun createCourse(title: String, description: String?) {
        viewModelScope.launch {
            _state.emit(CreateCourseState.Loading)
            createCourseUseCase(CreateCourse(title, description?:"")).catch { exception ->
                _state.emit(CreateCourseState.Error(exception.message ?: "Unknown error"))
            }.collect {
                _state.emit(CreateCourseState.Success)
            }
        }
    }
}