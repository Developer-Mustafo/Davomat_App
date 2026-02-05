package uz.coder.davomatapp.presentation.viewModel.state

sealed class CreateCourseState {
    object Init : CreateCourseState()
    object Loading : CreateCourseState()
    object Success : CreateCourseState()
    data class Error(val error: String) : CreateCourseState()
}