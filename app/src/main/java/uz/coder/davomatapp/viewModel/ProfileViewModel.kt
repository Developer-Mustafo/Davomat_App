package uz.coder.davomatapp.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.coder.davomatapp.repositoryImpl.UserRepositoryImpl
import uz.coder.davomatapp.todo.isConnected
import uz.coder.davomatapp.todo.logOut
import uz.coder.davomatapp.todo.role
import uz.coder.davomatapp.todo.userId
import uz.coder.davomatapp.usecase.user.GetUserByIdUseCase
import uz.coder.davomatapp.usecase.user.GetUserFromDatabaseUseCase
import uz.coder.davomatapp.viewModel.state.ProfileState

class ProfileViewModel(private val application: Application): AndroidViewModel(application) {
    private val repository = UserRepositoryImpl(application)
    private val getUserByIdUseCase = GetUserByIdUseCase(repository)
    private val getUserFromDatabaseUseCase = GetUserFromDatabaseUseCase(repository)
    private val _state = MutableStateFlow<ProfileState>(ProfileState.Init)
    val state = _state.asStateFlow()

    fun profile(){
        viewModelScope.launch {
            _state.value = ProfileState.Loading
            if (application.isConnected()){
                getUserByIdUseCase(userId).catch {
                    _state.value = ProfileState.Error(it.message.toString())
                }.collect {
                    _state.value = ProfileState.Success(it)
                }
            }else{
                getUserFromDatabaseUseCase(userId).collect {
                    _state.value = ProfileState.Success(it)
                }
            }
        }
    }

    fun logOut(onLogOut: () -> Unit) {
        userId = 0
        role = ""
        application.logOut()
        onLogOut()
    }
}