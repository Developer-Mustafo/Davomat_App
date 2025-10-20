package uz.coder.davomatapp.data

import kotlinx.coroutines.flow.flow
import uz.coder.davomatapp.data.map.CourseMap
import uz.coder.davomatapp.domain.model.CreateCourse
import uz.coder.davomatapp.data.network.ApiService
import uz.coder.davomatapp.domain.repository.CourseRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CourseRepositoryImpl @Inject constructor(
    private val map: CourseMap,
    private val apiService: ApiService
): CourseRepository {
    override fun getAllCourses(userId: Long) = flow {
        val response = apiService.getAllCourses(userId)
        if (response.code==200){
            emit(map.toCourses(response.data))
        }else if (response.code==500){
            throw RuntimeException(response.message)
        }
    }

    override fun createCourse(createCourse: CreateCourse)= flow {
        val response = apiService.createCourse(map.toCourseRequest(createCourse))
        if (response.code==200){
            emit(map.toCourse(response.data))
        }else if (response.code==500){
            throw RuntimeException(response.message)
        }
    }
}