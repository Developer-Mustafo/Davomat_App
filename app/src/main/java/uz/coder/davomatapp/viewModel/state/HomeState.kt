package uz.coder.davomatapp.viewModel.state

import uz.coder.davomatapp.model.Course

sealed class HomeState {
    data object Init: HomeState()
    data object Loading: HomeState()
    data class Success(val date: List<Course>): HomeState()
    data class Error(val error: String): HomeState()
}