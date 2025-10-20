package uz.coder.davomatapp.presentation.viewModel.state

import uz.coder.davomatapp.domain.model.Course

sealed class HomeState {
    data object Init: HomeState()
    data object Loading: HomeState()
    data object Done: HomeState()
    data class Success(val date: List<Course>): HomeState()
    data class Error(val error: String): HomeState()
}