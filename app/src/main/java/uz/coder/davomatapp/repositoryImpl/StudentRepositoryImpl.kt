package uz.coder.davomatapp.repositoryImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.coder.davomatapp.map.StudentMap
import uz.coder.davomatapp.model.Student
import uz.coder.davomatapp.network.ApiClient
import uz.coder.davomatapp.network.ApiService
import uz.coder.davomatapp.repository.StudentRepository

class StudentRepositoryImpl: StudentRepository {
    private val map = StudentMap()
    private val apiService = ApiClient.getRetrofit().create(ApiService::class.java)
    override fun seeCourses(userId: Long) = flow {
        val response = apiService.seeCourses(userId)
        if(response.code==200){
            emit(map.toStudentCourses(response.data))
        }
        else if(response.code==500){
            throw RuntimeException(response.message)
        }
    }

    override fun findByGroupIdAndUserId(
        userId: Long,
        groupId: Long
    ) = flow {
        val response = apiService.findByGroupIdAndUserId(userId, groupId)
        if (response.code==200){
            emit(map.toStudent(response.data))
        }else if(response.code==500){
            throw RuntimeException(response.message)
        }
    }
}