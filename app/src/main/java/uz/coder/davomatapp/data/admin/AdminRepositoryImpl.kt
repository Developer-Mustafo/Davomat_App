package uz.coder.davomatapp.data.admin

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.viewmodel.viewModelFactory
import uz.coder.davomatapp.data.db.MyDatabase
import uz.coder.davomatapp.data.mapper.AdminMapper
import uz.coder.davomatapp.domain.admin.Admin
import uz.coder.davomatapp.domain.admin.AdminRepository
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(val application: Application):AdminRepository{
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
        return try{
            mapper.getAdminDbModelToAdmin(database.getLoginOrSign(email, password))
        }catch (e:Exception){
            Admin(name = "aa", email = "aa", password = "aa", phone = "aa", gender = "aa")
        }
    }
}