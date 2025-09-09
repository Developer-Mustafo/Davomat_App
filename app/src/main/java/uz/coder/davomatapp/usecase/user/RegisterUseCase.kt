package uz.coder.davomatapp.usecase.user

import uz.coder.davomatapp.repository.UserRepository

data class RegisterUseCase(private val userRepository: UserRepository) {
    operator fun invoke(firstName:String, lastName:String, email: String, password: String, phoneNumber: String, role: String) = userRepository.registerUser(email, firstName, lastName, password, phoneNumber, role)
}
