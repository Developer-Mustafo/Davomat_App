package uz.coder.davomatapp.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(): ViewModel() {
    private val _networkState = MutableLiveData<Boolean?>(null)
    val networkState: LiveData<Boolean?> get() =  _networkState
    fun updateNetworkState(state: Boolean) {
        _networkState.value = state
    }
}
