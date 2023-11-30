package uz.coder.davomatapp.data.admin

import android.app.Application
import uz.coder.davomatapp.data.db.MyDatabase
import uz.coder.davomatapp.data.mapper.AdminMapper
import uz.coder.davomatapp.domain.admin.Admin
import uz.coder.davomatapp.domain.admin.AdminRepository

class AdminRepositoryImpl(application: Application):AdminRepository{
    private val database = MyDatabase.myDatabase(application).adminDao()
    private val mapper = AdminMapper()
    override suspend fun addAdmin(admin: Admin) {
        database.addAdmin(mapper.getAdminToAdminDbModel(admin))
    }

    override suspend fun editAdmin(admin: Admin) {
        database.addAdmin(mapper.getAdminToAdminDbModel(admin))
    }

    override suspend fun deleteAdmin(id: Int) {
        database.deleteAdmin(id)
    }

    override suspend fun getAdminId(id: Int): Admin {
        return mapper.getAdminDbModelToAdmin(database.getAdminId(id))
    }
}