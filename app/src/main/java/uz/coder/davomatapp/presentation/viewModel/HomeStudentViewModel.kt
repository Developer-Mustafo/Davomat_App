package uz.coder.davomatapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.coder.davomatapp.domain.model.Group
import uz.coder.davomatapp.domain.usecase.student.SeeCoursesUseCase
import uz.coder.davomatapp.presentation.viewModel.state.HomeStudentState
import javax.inject.Inject

@HiltViewModel
class HomeStudentViewModel @Inject constructor(
    private val seeCoursesUseCase:SeeCoursesUseCase
): ViewModel() {
    private val _state = MutableStateFlow<HomeStudentState>(HomeStudentState.Init)
    val state = _state.asStateFlow()

    fun seeCourses(){
        viewModelScope.launch {
            _state.emit(HomeStudentState.Loading)
            seeCoursesUseCase().catch {
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