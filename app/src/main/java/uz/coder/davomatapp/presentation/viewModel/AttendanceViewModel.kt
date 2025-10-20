package uz.coder.davomatapp.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.coder.davomatapp.domain.usecase.attendance.AttendanceListUseCase
import uz.coder.davomatapp.domain.usecase.student.FindByGroupIdAndUserIdUseCase
import uz.coder.davomatapp.presentation.viewModel.state.AttendanceState
import uz.coder.davomatapp.todo.userId
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    private val findByGroupIdAndUserIdUseCase : FindByGroupIdAndUserIdUseCase,
    private val attendanceListUseCase : AttendanceListUseCase
): ViewModel() {
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