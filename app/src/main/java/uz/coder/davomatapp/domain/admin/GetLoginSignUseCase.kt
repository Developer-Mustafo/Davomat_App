package uz.coder.davomatapp.domain.admin

import javax.inject.Inject

class GetLoginSignUseCase @Inject constructor(private val repository: AdminRepository) {
    suspend operator fun invoke(email:String,password:String):Admin{
        return repository.getLoginOrSign(email, password)
    }
}