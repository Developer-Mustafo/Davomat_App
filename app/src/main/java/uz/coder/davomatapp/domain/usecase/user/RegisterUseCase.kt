package uz.coder.davomatapp.domain.usecase.user

import uz.coder.davomatapp.domain.repository.UserRepository
import javax.inject.Inject

data class RegisterUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(firstName:String, lastName:String, email: String, password: String, phoneNumber: String, role: String) = userRepository.registerUser(email, firstName, lastName, password, phoneNumber, role)
}
