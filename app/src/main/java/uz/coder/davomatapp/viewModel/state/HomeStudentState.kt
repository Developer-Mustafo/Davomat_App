package uz.coder.davomatapp.viewModel.state

import uz.coder.davomatapp.model.StudentCourses

sealed class HomeStudentState {
    data object Init : HomeStudentState()
    data object Loading : HomeStudentState()
    data class Success(val data: List<StudentCourses>) : HomeStudentState()
    data class Error(val message: String) : HomeStudentState()
}