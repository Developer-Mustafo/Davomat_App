package uz.coder.davomatapp.presentation.viewModel.state

sealed class CreateGroupState {
    object Init : CreateGroupState()
    object Loading : CreateGroupState()
    object Success : CreateGroupState()
    data class Error(val error: String) : CreateGroupState()
}