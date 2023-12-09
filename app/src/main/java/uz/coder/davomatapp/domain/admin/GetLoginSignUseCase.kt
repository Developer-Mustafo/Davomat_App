package uz.coder.davomatapp.domain.admin

class GetLoginSignUseCase(private val repository: AdminRepository) {
    suspend operator fun invoke(email:String,password:String):Admin{
        return repository.getLoginOrSign(email, password)
    }
}