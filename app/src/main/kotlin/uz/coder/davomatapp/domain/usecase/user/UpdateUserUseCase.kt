package uz.coder.davomatapp.domain.usecase.user

import uz.coder.davomatapp.domain.repository.UserRepository
import javax.inject.Inject

data class UpdateUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(email: String, firstName:String, id:Long, lastName:String, password: String, phoneNumber: String, role: String) = userRepository.updateUser(email, firstName, id, lastName, password, phoneNumber, role)
}