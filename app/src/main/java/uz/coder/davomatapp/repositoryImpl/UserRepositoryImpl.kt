package uz.coder.davomatapp.repositoryImpl

import android.app.Application
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import uz.coder.davomatapp.db.AppDatabase
import uz.coder.davomatapp.map.UserMap
import uz.coder.davomatapp.network.ApiClient
import uz.coder.davomatapp.network.ApiService
import uz.coder.davomatapp.repository.UserRepository

class UserRepositoryImpl(context: Context): UserRepository {
    private val map = UserMap()
    private val apiService = ApiClient.getRetrofit().create(ApiService::class.java)
    private val database = AppDatabase.getInstance(context)
    override fun loginUser(
        email: String,
        password: String
    ) = flow {
        val loginUser = withContext(Dispatchers.IO){apiService.loginUser(email, password)}
        if(loginUser.code==200){
            emit(map.toUser(loginUser.data))
            database.userDao().insertUser(map.toUserEntity(loginUser.data))
        }
        else if(loginUser.code==500){
            throw RuntimeException(loginUser.message)
        }
    }

    override fun registerUser(
        email: String,
        firstName: String,
        lastName: String,
        password: String,
        phoneNumber: String,
        role: String
    ) = flow {
        val response = withContext(Dispatchers.IO){apiService.registerUser(map.toRegisterRequest(email, firstName, lastName, password, phoneNumber, role))}
        if(response.code==200){
            emit(map.toUser(response.data))
        }
        else if(response.code==500){
            throw RuntimeException(response.message)
        }
    }

    override fun deleteUser(id: Long) = flow {
        val response = withContext(Dispatchers.IO){apiService.deleteUser(id)}
        if(response.code==200){
            emit(response.data?:0)
            database.userDao().deleteUserById(id)
        }
        else if(response.code==500){
            throw RuntimeException(response.message)
        }
    }

    override fun getUser(id: Long) = flow {
        val response = withContext(Dispatchers.IO){apiService.getUser(id)}
        if(response.code==200){
            emit(map.toUser(response.data))
        }
        else if(response.code==500){
            throw RuntimeException(response.message)
        }
    }

    override fun getUserFromDatabase(id: Long) = flow {
        val user = database.userDao().getUserById(id)
        user.collect {
            emit(map.toUser(it))
        }
    }

    override fun getUserByPhoneNumber(phoneNumber: String) = flow {
        val response = withContext(Dispatchers.IO){apiService.getUserByPhoneNumber(phoneNumber)}
        if(response.code==200){
            emit(map.toUser(response.data))
        }
        else if(response.code==500){
            throw RuntimeException(response.message)
        }
    }

    override fun updateUser(
        email: String,
        firstName: String,
        id: Long,
        lastName: String,
        password: String,
        phoneNumber: String,
        role: String
    ) = flow {
        val response = withContext(Dispatchers.IO){apiService.updateUser(map.toUserRequest(email, firstName, id, lastName, password, phoneNumber, role))}
        if(response.code==200){
            emit(map.toUser(response.data))
            database.userDao().insertUser(map.toUserEntity(response.data))
        }
        else if(response.code==500){
            throw RuntimeException(response.message)
        }
    }
}