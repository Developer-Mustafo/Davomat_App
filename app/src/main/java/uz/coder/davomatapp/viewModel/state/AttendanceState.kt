package uz.coder.davomatapp.viewModel.state

import uz.coder.davomatapp.model.Attendance
import uz.coder.davomatapp.model.Student

sealed class AttendanceState {
    data object Init: AttendanceState()
    data object Loading: AttendanceState()
    data class Success(val student: Student, val data: List<Attendance>): AttendanceState()
    data class Error(val message:String): AttendanceState()
}