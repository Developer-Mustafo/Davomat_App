package uz.coder.davomatapp.presentation.viewModel.state

import uz.coder.davomatapp.domain.model.Attendance
import uz.coder.davomatapp.domain.model.Student

sealed class AttendanceState {
    data object Init: AttendanceState()
    data object Loading: AttendanceState()
    data class Success(val student: Student, val data: List<Attendance>): AttendanceState()
    data class Error(val message:String): AttendanceState()
}