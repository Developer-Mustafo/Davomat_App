package uz.coder.davomatapp.domain.usecase.user

import uz.coder.davomatapp.domain.repository.UserRepository
import javax.inject.Inject

data class GetUserFromDatabaseUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(id:Long) = userRepository.getUserFromDatabase(id)
}