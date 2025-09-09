package uz.coder.davomatapp.usecase.user

import uz.coder.davomatapp.repository.UserRepository

data class DeleteUserUseCase(private val userRepository: UserRepository) {
    operator fun invoke(id:Long) = userRepository.deleteUser(id)
}