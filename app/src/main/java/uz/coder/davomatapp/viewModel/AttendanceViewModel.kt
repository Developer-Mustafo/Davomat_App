package uz.coder.davomatapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.coder.davomatapp.repositoryImpl.AttendanceRepositoryImpl
import uz.coder.davomatapp.repositoryImpl.StudentRepositoryImpl
import uz.coder.davomatapp.todo.userId
import uz.coder.davomatapp.usecase.attendance.AttendanceListUseCase
import uz.coder.davomatapp.usecase.student.FindByGroupIdAndUserIdUseCase
import uz.coder.davomatapp.viewModel.state.AttendanceState

class AttendanceViewModel(application: Application): AndroidViewModel(application) {
    private val studentRepository = StudentRepositoryImpl()
    private val attendanceRepository = AttendanceRepositoryImpl()
    private val findByGroupIdAndUserIdUseCase = FindByGroupIdAndUserIdUseCase(studentRepository)
    private val attendanceListUseCase = AttendanceListUseCase(attendanceRepository)
    private val _state = MutableStateFlow<AttendanceState>(AttendanceState.Init)
    val state = _state.asStateFlow()
    fun studentProfile(groupId: Long){
        viewModelScope.launch {
            _state.emit(AttendanceState.Loading)
            findByGroupIdAndUserIdUseCase(userId, groupId).catch {
                _state.emit(AttendanceState.Error(it.message?:""))
            }.collect {student->
                attendanceListUseCase(student.id).catch {
                    _state.emit(AttendanceState.Error(it.message?:""))
                }.collect {
                    Log.d(TAG, "studentProfile: $it")
                    _state.emit(AttendanceState.Success(student, it))
                }
            }
        }
    }
}

private const val TAG = "AttendanceViewModel"