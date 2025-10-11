package uz.coder.davomatapp.repositoryImpl

import kotlinx.coroutines.flow.flow
import uz.coder.davomatapp.map.CourseMap
import uz.coder.davomatapp.network.ApiClient
import uz.coder.davomatapp.network.ApiService
import uz.coder.davomatapp.repository.CourseRepository

class CourseRepositoryImpl: CourseRepository {
    private val map = CourseMap()
    private val apiService = ApiClient.getRetrofit().create(ApiService::class.java)
    override fun getAllCourses(userId: Long) = flow {
        val response = apiService.getAllCourses(userId)
        if (response.code==200){
            emit(map.toCourses(response.data))
        }else if (response.code==500){
            throw RuntimeException(response.message)
        }
    }
}