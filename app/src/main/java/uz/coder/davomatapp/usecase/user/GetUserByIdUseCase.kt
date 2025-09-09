package uz.coder.davomatapp.usecase.user

import uz.coder.davomatapp.repository.UserRepository

data class GetUserByIdUseCase(private val userRepository: UserRepository) {
    operator fun invoke(id:Long) = userRepository.getUser(id)
}