package uz.coder.davomatapp.data.mapper

import uz.coder.davomatapp.data.admin.AdminDbModel
import uz.coder.davomatapp.domain.admin.Admin

class AdminMapper {
    fun getAdminToAdminDbModel(admin: Admin): AdminDbModel {
        return AdminDbModel(
            id = admin.id,
            name = admin.name,
            email = admin.email,
            password = admin.password,
            phone = admin.phone,
            gender = admin.gender
        )
    }
    fun getAdminDbModelToAdmin(adminDbModel: AdminDbModel): Admin {
        return Admin(
            id = adminDbModel.id,
            name = adminDbModel.name,
            email = adminDbModel.email,
            password = adminDbModel.password,
            phone = adminDbModel.phone,
            gender = adminDbModel.gender
        )
    }

    fun getAdminList(list: List<AdminDbModel>) = list.map {
        getAdminDbModelToAdmin(it)
    }
}