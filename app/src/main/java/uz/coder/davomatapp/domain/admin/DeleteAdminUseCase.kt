package uz.coder.davomatapp.domain.admin

class DeleteAdminUseCase (private val repository:AdminRepository) {
    suspend operator fun invoke(id: Int) {
        repository.delete(id)
    }
}