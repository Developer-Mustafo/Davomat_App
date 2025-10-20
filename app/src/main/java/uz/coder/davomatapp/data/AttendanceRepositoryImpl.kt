package uz.coder.davomatapp.data

import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody.Part
import okhttp3.RequestBody.Companion.toRequestBody
import uz.coder.davomatapp.data.map.AttendanceMap
import uz.coder.davomatapp.data.network.ApiService
import uz.coder.davomatapp.domain.model.CreateAttendance
import uz.coder.davomatapp.domain.repository.AttendanceRepository
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AttendanceRepositoryImpl @Inject constructor(
    private val map: AttendanceMap,
    private val apiService: ApiService
) : AttendanceRepository {

    override fun attendanceList(studentId: Long) = flow {
        val response = apiService.attendanceList(studentId)
        if (response.code==200){
            emit(map.toAttendanceList(response.data))
        }else if(response.code==500){
            throw RuntimeException(response.message)
        }
    }

    override fun createAttendance(createAttendance: CreateAttendance) = flow {
        val response = apiService.createAttendance(map.toAttendanceRequest(createAttendance))
        if (response.code==200){
            emit(map.toAttendance(response.data))
        }else if (response.code==500){
            throw RuntimeException(response.message)
        }
    }

    override fun uploadAttendanceExcel(file: File)= flow {
        val requestBody = file.readBytes().toRequestBody()
        val formData = Part.createFormData("file", file.name, requestBody)
        val response = apiService.uploadAttendanceExcel(formData)
        if (response.code==200){
            emit(response.data?:"")
        }else if (response.code==500){
            throw RuntimeException(response.message)
        }
    }
}
