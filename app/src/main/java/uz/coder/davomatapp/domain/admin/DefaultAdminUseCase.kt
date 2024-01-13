package uz.coder.davomatapp.domain.admin

import javax.inject.Inject

class DefaultAdminUseCase @Inject constructor(private val repository: AdminRepository) {
    suspend operator fun invoke(defaultAdmin:Admin) = repository.defaultAdmin(defaultAdmin)
}