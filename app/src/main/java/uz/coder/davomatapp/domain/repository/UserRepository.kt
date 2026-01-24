package uz.coder.davomatapp.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.coder.davomatapp.domain.model.User

interface UserRepository {
    fun loginUser(email:String, password:String): Flow<User>
    fun registerUser(email: String, firstName: String, lastName: String, password: String, phoneNumber: String, role: String): Flow<User>
    fun deleteUser(): Flow<Int>
    fun getUser(): Flow<User>
    fun getUserFromDatabase(id: Long): Flow<User>
    fun getUserByPhoneNumber(phoneNumber: String): Flow<User>
    fun updateUser(email: String, firstName: String, id: Long, lastName: String, password: String, phoneNumber: String, role: String): Flow<User>
}