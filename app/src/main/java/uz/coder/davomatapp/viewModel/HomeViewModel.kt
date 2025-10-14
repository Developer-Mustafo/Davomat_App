package uz.coder.davomatapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.coder.davomatapp.repositoryImpl.AttendanceRepositoryImpl
import uz.coder.davomatapp.repositoryImpl.CourseRepositoryImpl
import uz.coder.davomatapp.repositoryImpl.StudentRepositoryImpl
import uz.coder.davomatapp.todo.userId
import uz.coder.davomatapp.usecase.attendance.UploadAttendanceExcelUseCase
import uz.coder.davomatapp.usecase.course.GetAllCoursesUseCase
import uz.coder.davomatapp.usecase.student.UploadStudentExcelUseCase
import uz.coder.davomatapp.viewModel.state.HomeState
import java.io.File

class HomeViewModel(application: Application): AndroidViewModel(application) {
    private val repository = CourseRepositoryImpl()
    private val studentRepository = StudentRepositoryImpl()
    private val attendanceRepository = AttendanceRepositoryImpl()
    private val getAllCoursesUseCase = GetAllCoursesUseCase(repository)
    private val uploadStudentExcelUseCase = UploadStudentExcelUseCase(studentRepository)
    private val uploadAttendanceExcelUseCase = UploadAttendanceExcelUseCase(attendanceRepository)
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

    fun uploadStudentExcel(file: File) {
        viewModelScope.launch {
            _state.emit(HomeState.Loading)
            uploadStudentExcelUseCase(file, userId).catch {
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