package uz.coder.davomatapp.presentation.viewModel.state

import uz.coder.davomatapp.domain.model.User

sealed class RegisterState {
    data object Init : RegisterState()
    data object Loading : RegisterState()
    data class Success(val data: User) : RegisterState()
    data class Error(val message: String? = null) : RegisterState()
    data class ErrorFirstName(val message: String? = null) : RegisterState()
    data class ErrorLastName(val message: String? = null) : RegisterState()
    data class ErrorEmail(val message: String? = null) : RegisterState()
    data class ErrorPassword(val message: String? = null) : RegisterState()
    data class ErrorPhoneNumber(val message: String? = null) : RegisterState()
    data class ErrorRole(val message: String? = null) : RegisterState()
}
