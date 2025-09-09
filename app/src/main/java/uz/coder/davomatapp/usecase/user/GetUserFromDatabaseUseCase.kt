package uz.coder.davomatapp.usecase.user

import uz.coder.davomatapp.repository.UserRepository

data class GetUserFromDatabaseUseCase(private val userRepository: UserRepository) {
    operator fun invoke(id:Long) = userRepository.getUserFromDatabase(id)
}