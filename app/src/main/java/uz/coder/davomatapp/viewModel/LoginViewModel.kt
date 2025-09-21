package uz.coder.davomatapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.coder.davomatapp.R
import uz.coder.davomatapp.repositoryImpl.UserRepositoryImpl
import uz.coder.davomatapp.todo.EMAIL_NEEDS
import uz.coder.davomatapp.todo.EMPTY
import uz.coder.davomatapp.todo.ERROR_EMAIL
import uz.coder.davomatapp.todo.ERROR_PASSWORD
import uz.coder.davomatapp.todo.OK
import uz.coder.davomatapp.todo.SHORT
import uz.coder.davomatapp.todo.isEmail
import uz.coder.davomatapp.todo.isPassword
import uz.coder.davomatapp.todo.parseString
import uz.coder.davomatapp.todo.role
import uz.coder.davomatapp.todo.userId
import uz.coder.davomatapp.usecase.user.LoginUseCase
import uz.coder.davomatapp.viewModel.state.LoginState

class LoginViewModel(private val application: Application) : AndroidViewModel(application) {
    private val repository = UserRepositoryImpl(application)
    private val loginUseCase = LoginUseCase(repository)
    private val _state = MutableStateFlow<LoginState>(LoginState.Init)
    val state = _state.asStateFlow()

    fun login(inputEmail: String?, inputPassword: String){
        viewModelScope.launch {
            _state.emit(LoginState.Loading)
            val email = parseString(inputEmail)
            val password = parseString(inputPassword)
            if (isValidate(email, password)){
                loginUseCase(email, password).catch {
                    _state.emit(LoginState.Error(it.message.toString()))
                }.collect {
                    _state.emit(LoginState.Success(it))
                    userId = it.id
                    role = it.role
                }
            }
        }
    }
    private fun isValidate(email: String, password: String): Boolean {
        var result = true
        if (email.isEmail()== OK && password.isPassword()==OK) result = true
        else if (email.isEmail() == EMAIL_NEEDS){
            _state.value = LoginState.ErrorEmail(application.getString(R.string.emailNeeds))
            result = false
        }
        else if (email.isEmail() == ERROR_EMAIL){
            _state.value = LoginState.ErrorEmail(application.getString(R.string.errorEmail))
            result = false
        }
        else if (email.isEmail() == SHORT){
            _state.value = LoginState.ErrorEmail(application.getString(R.string.shortEmail))
            result = false
        }
        else if (email.isEmail() == EMPTY){
            _state.value = LoginState.ErrorEmail(application.getString(R.string.emptyEmail))
            result = false
        }
        else if (password.isPassword() == ERROR_PASSWORD){
            _state.value = LoginState.ErrorPassword(application.getString(R.string.errorPassword))
            result = false
        }
        else if (password.isPassword() == SHORT){
            _state.value = LoginState.ErrorPassword(application.getString(R.string.shortPassword))
            result = false
        }
        else if (password.isPassword() == EMPTY){
            _state.value = LoginState.ErrorPassword(application.getString(R.string.emptyPassword))
            result = false
        }
        return result
    }

    fun resetEmail(){
        _state.value = LoginState.ErrorEmail()
    }
    fun resetPassword(){
        _state.value = LoginState.ErrorPassword()
    }
}