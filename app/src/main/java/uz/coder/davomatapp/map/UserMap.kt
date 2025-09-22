package uz.coder.davomatapp.map

import uz.coder.davomatapp.db.model.UserDbModel
import uz.coder.davomatapp.model.User
import uz.coder.davomatapp.network.dto.LoginResponse
import uz.coder.davomatapp.network.dto.RegisterRequest
import uz.coder.davomatapp.network.dto.RegisterResponse
import uz.coder.davomatapp.network.dto.UserRequest
import uz.coder.davomatapp.network.dto.UserResponse
import java.time.LocalDate

class UserMap {
    fun toUser(response: LoginResponse?) = User(id=response?.id?:0L, firstName=response?.firstName?: "", lastName=response?.lastName?: "", email=response?.email?: "", password=response?.password?: "", phoneNumber=response?.phoneNumber?: "", payedDate=response?.payedDate?: LocalDate.now(), role=response?.role?: "")
    fun toRegisterRequest(
        email: String,
        firstName: String,
        lastName: String,
        password: String,
        phoneNumber: String,
        role: String
    ) = RegisterRequest(email = email, firstName = firstName, lastName = lastName, password = password, phoneNumber = phoneNumber, role = role)

    fun toUser(response: RegisterResponse?) = User(id=response?.id?:0L, firstName=response?.firstName?: "", lastName=response?.lastName?: "", email=response?.email?: "", password=response?.password?: "", phoneNumber=response?.phoneNumber?: "", payedDate=response?.payedDate?: LocalDate.now(), role=response?.role?: "")
    fun toUser(response: UserResponse?) = User(id=response?.id?:0L, firstName=response?.firstName?: "", lastName=response?.lastName?: "", email=response?.email?: "", password=response?.password?: "", phoneNumber=response?.phoneNumber?: "", payedDate=response?.payedDate?: LocalDate.now(), role=response?.role?: "")
    fun toUser(model: UserDbModel) = User(id= model.id, firstName= model.firstName, lastName=model.lastName, email=model.email, password=model.password, phoneNumber=model.phoneNumber, payedDate=model.payedDate, role=model.role)
    fun toUserRequest(
        email: String,
        firstName: String,
        id: Long,
        lastName: String,
        password: String,
        phoneNumber: String,
        role: String
    ) = UserRequest(email = email, id = id, firstName = firstName, lastName = lastName, password = password, phoneNumber = phoneNumber, role = role)
    fun toUserEntity(data: LoginResponse?) = UserDbModel(email = data?.email?:"", firstName = data?.firstName?:"", lastName = data?.lastName?:"", password = data?.password?:"", phoneNumber = data?.phoneNumber?:"", role = data?.role?:"", payedDate = data?.payedDate?: LocalDate.now(), id = data?.id?:0L)
    fun toUserEntity(data: UserResponse?) = UserDbModel(email = data?.email?:"", id = data?.id?:0L, firstName = data?.firstName?:"", lastName = data?.lastName?:"", password = data?.password?:"", phoneNumber = data?.phoneNumber?:"", role = data?.role?:"", payedDate = data?.payedDate?: LocalDate.now())
}