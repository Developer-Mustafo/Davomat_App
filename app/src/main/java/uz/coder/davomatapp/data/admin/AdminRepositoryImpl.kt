package uz.coder.davomatapp.data.admin

import android.app.Application
import android.widget.Toast
import uz.coder.davomatapp.data.db.MyDatabase
import uz.coder.davomatapp.data.mapper.AdminMapper
import uz.coder.davomatapp.domain.admin.Admin
import uz.coder.davomatapp.domain.admin.AdminRepository

class AdminRepositoryImpl(val application: Application):AdminRepository{
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

    override suspend fun getLoginOrSign(email: String, password: String): Admin {
        return mapper.getAdminDbModelToAdmin(database.getLoginOrSign(email, password))
    }
}