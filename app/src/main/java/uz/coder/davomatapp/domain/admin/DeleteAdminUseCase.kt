package uz.coder.davomatapp.domain.admin

import javax.inject.Inject

class DeleteAdminUseCase @Inject constructor(private val repository:AdminRepository) {
    suspend operator fun invoke(id: Int) {
        repository.deleteAdmin(id)
    }
}