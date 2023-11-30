package uz.coder.davomatapp.domain.admin

class AddAdminUseCase(private val repository:AdminRepository) {
    suspend operator fun invoke(admin: Admin){
        repository.addAdmin(admin)
    }
}