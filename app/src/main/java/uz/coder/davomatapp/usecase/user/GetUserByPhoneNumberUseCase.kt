package uz.coder.davomatapp.usecase.user

import uz.coder.davomatapp.repository.UserRepository

data class GetUserByPhoneNumberUseCase(private val userRepository: UserRepository) {
    operator fun invoke(phoneNumber:String) = userRepository.getUserByPhoneNumber(phoneNumber)
}