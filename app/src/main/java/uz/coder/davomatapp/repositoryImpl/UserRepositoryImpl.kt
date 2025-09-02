package uz.coder.davomatapp.repositoryImpl

import android.app.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import uz.coder.davomatapp.R
import uz.coder.davomatapp.map.UserMap
import uz.coder.davomatapp.network.ApiClient
import uz.coder.davomatapp.network.ApiService
import uz.coder.davomatapp.repository.UserRepository
import uz.coder.davomatapp.todo.isConnected

class UserRepositoryImpl(private val application: Application): UserRepository {
    private val map = UserMap()
    private val apiService = ApiClient.getRetrofit().create(ApiService::class.java)
    override fun loginUser(
        email: String,
        password: String
    ) = flow {
        if(application.isConnected()){
            val loginUser = withContext(Dispatchers.IO){apiService.loginUser(email, password)}
            if(loginUser.code==200){
                emit(map.toUser(loginUser.data))
            }
            else if(loginUser.code==500){
                throw RuntimeException(loginUser.message)
            }
        }else{
            throw RuntimeException(application.getString(R.string.internet_error))
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
        if(application.isConnected()){
            val response = withContext(Dispatchers.IO){apiService.registerUser(map.toRegisterRequest(email, firstName, lastName, password, phoneNumber, role))}
            if(response.code==200){
                emit(map.toUser(response.data))
            }
            else if(response.code==500){
                throw RuntimeException(response.message)
            }
        }
        else{
            throw RuntimeException(application.getString(R.string.internet_error))
        }
    }

    override fun deleteUser(id: Long) = flow {
        if(application.isConnected()){
            val response = withContext(Dispatchers.IO){apiService.deleteUser(id)}
            if(response.code==200){
                emit(response.data?:0)
            }
            else if(response.code==500){
                throw RuntimeException(response.message)
            }
        }
        else{
            throw RuntimeException(application.getString(R.string.internet_error))
        }
    }

    override fun getUser(id: Long) = flow {
        if(application.isConnected()){
            val response = withContext(Dispatchers.IO){apiService.getUser(id)}
            if(response.code==200){
                emit(map.toUser(response.data))
            }
            else if(response.code==500){
                throw RuntimeException(response.message)
            }
        }
        else{
            throw RuntimeException(application.getString(R.string.internet_error))
        }
    }

    override fun getUserByPhoneNumber(phoneNumber: String) = flow {
        if(application.isConnected()){
            val response = withContext(Dispatchers.IO){apiService.getUserByPhoneNumber(phoneNumber)}
            if(response.code==200){
                emit(map.toUser(response.data))
            }
            else if(response.code==500){
                throw RuntimeException(response.message)
            }
        }
        else{
            throw RuntimeException(application.getString(R.string.internet_error))
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
        if(application.isConnected()){
            val response = withContext(Dispatchers.IO){apiService.updateUser(map.toUserRequest(email, firstName, id, lastName, password, phoneNumber, role))}
            if(response.code==200){
                emit(map.toUser(response.data))
            }
            else if(response.code==500){
                throw RuntimeException(response.message)
            }
        }
        else{
            throw RuntimeException(application.getString(R.string.internet_error))
        }
    }

}