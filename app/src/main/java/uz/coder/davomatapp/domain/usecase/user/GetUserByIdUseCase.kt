package uz.coder.davomatapp.domain.usecase.user

import uz.coder.davomatapp.domain.repository.UserRepository
import javax.inject.Inject

data class GetUserByIdUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(id:Long) = userRepository.getUser(id)
}