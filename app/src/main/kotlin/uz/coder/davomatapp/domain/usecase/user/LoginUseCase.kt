package uz.coder.davomatapp.domain.usecase.user

import uz.coder.davomatapp.domain.repository.UserRepository
import javax.inject.Inject

data class LoginUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(email: String, password: String) = userRepository.loginUser(email, password)
}