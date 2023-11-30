package uz.coder.davomatapp.domain.admin

interface AdminRepository {
    suspend fun addAdmin(admin: Admin)
    suspend fun editAdmin(admin: Admin)
    suspend fun deleteAdmin(id:Int)
    suspend fun getAdminId(id: Int):Admin

}