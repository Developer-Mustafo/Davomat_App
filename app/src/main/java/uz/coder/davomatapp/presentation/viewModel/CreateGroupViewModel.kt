package uz.coder.davomatapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.coder.davomatapp.domain.model.CreateGroup
import uz.coder.davomatapp.domain.usecase.course.GetAllCoursesUseCase
import uz.coder.davomatapp.domain.usecase.group.CreateGroupUseCase
import uz.coder.davomatapp.presentation.viewModel.state.CreateGroupState
import javax.inject.Inject

@HiltViewModel
class CreateGroupViewModel @Inject constructor(
    private val createGroupUseCase: CreateGroupUseCase,
    private val getAllCoursesUseCase: GetAllCoursesUseCase
) : ViewModel() {
    private val _courses = MutableStateFlow<List<Pair<Long, String>>>(emptyList())
    val courses: StateFlow<List<Pair<Long, String>>> = _courses
    private val _state = MutableStateFlow<CreateGroupState>(CreateGroupState.Init)
    val state: StateFlow<CreateGroupState> = _state

    init {
        getCourses()
    }

    private fun getCourses() {
        viewModelScope.launch {
            try {
                getAllCoursesUseCase().collect {result->
                    _courses.value = result.map { it.id to it.title }
                }
            } catch (e: Exception) {
                _courses.value = emptyList()
                _state.value = CreateGroupState.Error(e.message ?: "Unknown error while fetching courses")
            }
        }
    }

    fun createGroup(title: String, courseId: Long) {
        viewModelScope.launch {
            _state.value = CreateGroupState.Loading
            try {
                createGroupUseCase(CreateGroup(title, courseId)).catch {
                    _state.value = CreateGroupState.Error(it.message.toString())
                }.collect {
                    _state.value = CreateGroupState.Success
                }
            } catch (e: Exception) {
                _state.value = CreateGroupState.Error(e.message ?: "Unknown error while creating group")
            }
        }
    }
}