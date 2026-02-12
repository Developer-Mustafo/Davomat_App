package uz.coder.davomatapp.presentation.viewModel.state

import uz.coder.davomatapp.domain.model.User

sealed class ProfileState {
    data object Init: ProfileState()
    data object Loading: ProfileState()
    data class Error(val message: String): ProfileState()
    data class Success(val user: User): ProfileState()
}