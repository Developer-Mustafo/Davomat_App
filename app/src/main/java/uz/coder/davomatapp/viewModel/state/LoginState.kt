package uz.coder.davomatapp.viewModel.state

import uz.coder.davomatapp.model.User

sealed class LoginState {
    data object Init:LoginState()
    data object Loading:LoginState()
    data class Success(val data: User):LoginState()
    data class ErrorEmail(val message:String? = null):LoginState()
    data class ErrorPassword(val message:String? = null):LoginState()
    data class Error(val message:String? = null):LoginState()
}