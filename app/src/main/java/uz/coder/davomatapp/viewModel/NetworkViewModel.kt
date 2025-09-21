package uz.coder.davomatapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NetworkViewModel: ViewModel() {
    private val _networkState = MutableLiveData<Boolean?>(null)
    val networkState: LiveData<Boolean?> get() =  _networkState
    fun updateNetworkState(state: Boolean) {
        _networkState.value = state
    }
}
