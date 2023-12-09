package uz.coder.davomatapp.domain.admin

class GetAdminByIdUseCase (private val repository:AdminRepository) {
    suspend operator fun invoke(id:Int):Admin{
        return repository.getAdminId(id)
    }
}