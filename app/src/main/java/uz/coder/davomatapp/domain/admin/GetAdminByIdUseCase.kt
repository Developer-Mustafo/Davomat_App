package uz.coder.davomatapp.domain.admin

import javax.inject.Inject

class GetAdminByIdUseCase @Inject constructor(private val repository:AdminRepository) {
    suspend operator fun invoke(id:Int):Admin{
        return repository.getAdminId(id)
    }
}