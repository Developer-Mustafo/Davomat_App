package uz.coder.davomatapp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.coder.davomatapp.domain.usecase.attendance.UploadAttendanceExcelUseCase
import uz.coder.davomatapp.domain.usecase.course.GetAllCoursesUseCase
import uz.coder.davomatapp.domain.usecase.student.UploadStudentExcelUseCase
import uz.coder.davomatapp.presentation.viewModel.state.HomeState
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllCoursesUseCase : GetAllCoursesUseCase,
    private val uploadStudentExcelUseCase : UploadStudentExcelUseCase,
    private val uploadAttendanceExcelUseCase : UploadAttendanceExcelUseCase
): ViewModel() {
    private val _state = MutableStateFlow<HomeState>(HomeState.Init)
    val state = _state.asStateFlow()
    fun getAllCourses(){
        viewModelScope.launch {
            _state.emit(HomeState.Loading)
            getAllCoursesUseCase().catch {
                _state.emit(HomeState.Error(it.message?:""))
            }.collect {
                _state.emit(HomeState.Success(it))
            }
        }
    }

    fun uploadStudentExcel(file: File) {
        viewModelScope.launch {
            _state.emit(HomeState.Loading)
            uploadStudentExcelUseCase(file).catch {
                _state.emit(HomeState.Error(it.message?:""))
            }.collect {
                _state.emit(HomeState.Done)
            }
        }
    }

    fun uploadAttendanceExcel(file: File) {
        viewModelScope.launch {
            _state.emit(HomeState.Loading)
            uploadAttendanceExcelUseCase(file).catch {
                _state.emit(HomeState.Error(it.message?:""))
            }.collect {
                _state.emit(HomeState.Done)
            }
        }
    }
}