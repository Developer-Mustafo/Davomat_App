package uz.coder.davomatapp.domain.admin

class EditAdminUseCase (private val repository:AdminRepository) {
    suspend operator fun invoke(admin: Admin) {
        repository.edit(admin)
    }
}