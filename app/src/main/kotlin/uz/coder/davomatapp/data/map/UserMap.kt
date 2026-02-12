package uz.coder.davomatapp.data.map

import uz.coder.davomatapp.data.db.model.UserDbModel
import uz.coder.davomatapp.domain.model.User
import uz.coder.davomatapp.data.network.dto.RegisterRequest
import uz.coder.davomatapp.data.network.dto.RegisterResponse
import uz.coder.davomatapp.data.network.dto.UserRequest
import uz.coder.davomatapp.data.network.dto.UserResponse
import kotlinx.datetime.LocalDate
import uz.coder.davomatapp.todo.orToday
import javax.inject.Inject

class UserMap @Inject constructor() {
    fun toRegisterRequest(
        email: String,
        firstName: String,
        lastName: String,
        password: String,
        phoneNumber: String,
        role: String
    ) = RegisterRequest(email = email, firstName = firstName, lastName = lastName, password = password, phoneNumber = phoneNumber, role = role)

    fun toUser(response: RegisterResponse?) = User(id=response?.id?:0L, firstName=response?.firstName?: "", lastName=response?.lastName?: "", email=response?.email?: "", password=response?.password?: "", phoneNumber=response?.phoneNumber?: "", payedDate=response?.payedDate?: LocalDate.orToday(), role=response?.role?: "")
    fun toUser(response: UserResponse?) = User(id=response?.id?:0L, firstName=response?.firstName?: "", lastName=response?.lastName?: "", email=response?.email?: "", password=response?.password?: "", phoneNumber=response?.phoneNumber?: "", payedDate=response?.payedDate?: LocalDate.orToday(), role=response?.role?: "")
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
    fun toUserEntity(data: UserResponse?) = UserDbModel(email = data?.email?:"", id = data?.id?:0L, firstName = data?.firstName?:"", lastName = data?.lastName?:"", password = data?.password?:"", phoneNumber = data?.phoneNumber?:"", role = data?.role?:"", payedDate = data?.payedDate?: LocalDate.orToday())
}
