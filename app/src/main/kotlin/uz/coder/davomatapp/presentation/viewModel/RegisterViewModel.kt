package uz.coder.davomatapp.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.coder.davomatapp.R
import uz.coder.davomatapp.domain.usecase.user.RegisterUseCase
import uz.coder.davomatapp.presentation.viewModel.state.RegisterState
import uz.coder.davomatapp.todo.EMAIL_NEEDS
import uz.coder.davomatapp.todo.EMPTY
import uz.coder.davomatapp.todo.ERROR_EMAIL
import uz.coder.davomatapp.todo.ERROR_PASSWORD
import uz.coder.davomatapp.todo.OK
import uz.coder.davomatapp.todo.SHORT
import uz.coder.davomatapp.todo.isEmail
import uz.coder.davomatapp.todo.isPassword
import uz.coder.davomatapp.todo.parseString
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor
    (private val application: Application,
    private val registerUseCase: RegisterUseCase
    ) : AndroidViewModel(application) {
    private val _state = MutableStateFlow<RegisterState>(RegisterState.Init)
    val state = _state.asStateFlow()

    fun register(inputFirstName: String?, inputLastName: String?, inputEmail: String?, inputPassword: String, inputPhoneNumber: String?, inputRole: String?){
        viewModelScope.launch {
            _state.emit(RegisterState.Loading)
            val firstName = parseString(inputFirstName)
            val lastName = parseString(inputLastName)
            val email = parseString(inputEmail)
            val password = parseString(inputPassword)
            val phoneNumber = parseString(inputPhoneNumber)
            val role = parseString(inputRole)
            if (isValidate(firstName, lastName, email, password, phoneNumber, role)) {
            registerUseCase(firstName, lastName, email, password, phoneNumber, role).catch {
                    _state.emit(RegisterState.Error(it.message.toString()))
                }.collect {
                    _state.emit(RegisterState.Success(it))
                }
            }
        }
    }

    private fun isValidate(firstName: String, lastName: String, email: String, password: String, phoneNumber: String, role: String): Boolean {
        var result = true
        if (firstName.isEmpty() || firstName.isBlank()){
            _state.value = RegisterState.ErrorFirstName(application.getString(R.string.emptyFirstName))
            result = false
        }
        if (lastName.isEmpty() || lastName.isBlank()){
            _state.value = RegisterState.ErrorLastName(application.getString(R.string.emptyLastName))
            result = false
        }
        if (email.isEmail()== OK && password.isPassword()==OK) result = true
        else if (email.isEmail() == EMAIL_NEEDS){
            _state.value = RegisterState.ErrorEmail(application.getString(R.string.emailNeeds))
            result = false
        }
        else if (email.isEmail() == ERROR_EMAIL){
            _state.value = RegisterState.ErrorEmail(application.getString(R.string.errorEmail))
            result = false
        }
        else if (email.isEmail() == SHORT){
            _state.value = RegisterState.ErrorEmail(application.getString(R.string.shortEmail))
            result = false
        }
        else if (email.isEmail() == EMPTY){
            _state.value = RegisterState.ErrorEmail(application.getString(R.string.emptyEmail))
            result = false
        }
        else if (password.isPassword() == ERROR_PASSWORD){
            _state.value = RegisterState.ErrorPassword(application.getString(R.string.errorPassword))
            result = false
        }
        else if (password.isPassword() == SHORT){
            _state.value = RegisterState.ErrorPassword(application.getString(R.string.shortPassword))
            result = false
        }
        else if (password.isPassword() == EMPTY){
            _state.value = RegisterState.ErrorPassword(application.getString(R.string.emptyPassword))
            result = false
        }
        if (phoneNumber.isEmpty() || phoneNumber.isBlank()){
            _state.value = RegisterState.ErrorPhoneNumber(application.getString(R.string.emptyPhoneNumber))
            result = false
        }
        if (role == "0"){
            _state.value = RegisterState.ErrorRole(application.getString(R.string.choose_role))
            result = false
        }
        return result
    }
    fun resetFirstName(){
        _state.value = RegisterState.ErrorFirstName()
    }
    fun resetLastName(){
        _state.value = RegisterState.ErrorLastName()
    }

    fun resetEmail(){
        _state.value = RegisterState.ErrorEmail()
    }
    fun resetPassword(){
        _state.value = RegisterState.ErrorPassword()
    }
    fun resetPhoneNumber(){
        _state.value = RegisterState.ErrorPhoneNumber()
    }
    fun resetRole(){
        _state.value = RegisterState.ErrorRole()
    }
}