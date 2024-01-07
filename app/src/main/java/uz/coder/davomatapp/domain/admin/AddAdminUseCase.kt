package uz.coder.davomatapp.domain.admin

import javax.inject.Inject

class AddAdminUseCase @Inject constructor(private val repository:AdminRepository) {
    suspend operator fun invoke(admin: Admin){
        repository.addAdmin(admin)
    }
}