package uz.coder.davomatapp.usecase

import uz.coder.davomatapp.repository.UserRepository

data class LoginUseCase(private val userRepository: UserRepository) {
    operator fun invoke(email: String, password: String) = userRepository.loginUser(email, password)
}