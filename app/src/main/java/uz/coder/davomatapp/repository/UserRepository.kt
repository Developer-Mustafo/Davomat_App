package uz.coder.davomatapp.repository

import kotlinx.coroutines.flow.Flow
import uz.coder.davomatapp.model.User

interface UserRepository {
    fun loginUser(email:String, password:String): Flow<User>
    fun registerUser(email: String, firstName: String, lastName: String, password: String, phoneNumber: String, role: String): Flow<User>
    fun deleteUser(id: Long): Flow<Int>
    fun getUser(id: Long): Flow<User>
    fun getUserByPhoneNumber(phoneNumber: String): Flow<User>
    fun updateUser(email: String, firstName: String, id: Long, lastName: String, password: String, phoneNumber: String, role: String): Flow<User>
}