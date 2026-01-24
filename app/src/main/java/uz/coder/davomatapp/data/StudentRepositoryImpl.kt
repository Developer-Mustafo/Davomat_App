package uz.coder.davomatapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody.Part
import okhttp3.RequestBody.Companion.toRequestBody
import uz.coder.davomatapp.data.map.StudentMap
import uz.coder.davomatapp.data.network.ApiService
import uz.coder.davomatapp.domain.model.CreateStudent
import uz.coder.davomatapp.domain.repository.StudentRepository
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StudentRepositoryImpl @Inject constructor(
    private val map: StudentMap,
    private val apiService: ApiService
): StudentRepository {
    override fun seeCourses() = flow {
        val response = apiService.seeCourses()
        if(response.code==200){
            emit(map.toStudentCourses(response.data))
        }
        else if(response.code==500){
            throw RuntimeException(response.message)
        }
    }

    override fun findByGroupId(
        groupId: Long
    ) = flow {
        val response = apiService.findByGroupId(groupId)
        if (response.code==200){
            emit(map.toStudent(response.data))
        }else if(response.code==500){
            throw RuntimeException(response.message)
        }
    }

    override fun createStudent(createStudent: CreateStudent) = flow {
        val response = apiService.createStudent(map.toCreateStudentRequest(createStudent))
        if (response.code==200){
            emit(map.toStudent(response.data))
        }else if (response.code==500){
            throw RuntimeException(response.message)
        }
    }

    override fun uploadStudentExcel(file: File): Flow<String> = flow {
        val requestBody = file.readBytes().toRequestBody()
        val formData = Part.createFormData("file", file.name, requestBody)
        val response = apiService.uploadStudentExcel(formData)
        if (response.code==200){
            emit(response.message?:"")
        }else if (response.code==500){
            throw RuntimeException(response.message)
        }
    }
}