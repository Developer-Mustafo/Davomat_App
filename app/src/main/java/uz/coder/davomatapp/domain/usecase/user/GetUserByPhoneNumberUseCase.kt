package uz.coder.davomatapp.domain.usecase.user

import uz.coder.davomatapp.domain.repository.UserRepository
import javax.inject.Inject

data class GetUserByPhoneNumberUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(phoneNumber:String) = userRepository.getUserByPhoneNumber(phoneNumber)
}