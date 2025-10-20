package uz.coder.davomatapp.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import uz.coder.davomatapp.domain.usecase.user.GetUserByIdUseCase
import uz.coder.davomatapp.domain.usecase.user.GetUserFromDatabaseUseCase
import uz.coder.davomatapp.presentation.viewModel.state.ProfileState
import uz.coder.davomatapp.todo.isConnected
import uz.coder.davomatapp.todo.logOut
import uz.coder.davomatapp.todo.role
import uz.coder.davomatapp.todo.userId
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor
    (private val application: Application,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getUserFromDatabaseUseCase: GetUserFromDatabaseUseCase): AndroidViewModel(application) {
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