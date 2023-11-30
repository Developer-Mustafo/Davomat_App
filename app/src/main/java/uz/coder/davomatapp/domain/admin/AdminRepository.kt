package uz.coder.davomatapp.domain.admin

interface AdminRepository {
    suspend fun addAdmin(admin: Admin)
    suspend fun edit(admin: Admin)
    suspend fun delete(id:Int)
    suspend fun getAdminId(id: Int):Admin

}