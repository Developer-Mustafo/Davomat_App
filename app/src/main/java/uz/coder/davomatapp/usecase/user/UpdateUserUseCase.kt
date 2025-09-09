package uz.coder.davomatapp.usecase.user

import uz.coder.davomatapp.repository.UserRepository

data class UpdateUserUseCase(private val userRepository: UserRepository) {
    operator fun invoke(email: String, firstName:String, id:Long, lastName:String, password: String, phoneNumber: String, role: String) = userRepository.updateUser(email, firstName, id, lastName, password, phoneNumber, role)
}