package uz.coder.davomatapp.repositoryImpl

import kotlinx.coroutines.flow.flow
import uz.coder.davomatapp.map.AttendanceMap
import uz.coder.davomatapp.model.Attendance
import uz.coder.davomatapp.network.ApiClient
import uz.coder.davomatapp.network.ApiService
import uz.coder.davomatapp.repository.AttendanceRepository

class AttendanceRepositoryImpl : AttendanceRepository {
    private val map = AttendanceMap()
    private val apiService = ApiClient.getRetrofit().create(ApiService::class.java)

    override fun attendanceList(studentId: Long) = flow {
        val response = apiService.attendanceList(studentId)
        if (response.code==200){
            emit(map.toAttendanceList(response.data))
        }else if(response.code==500){
            throw RuntimeException(response.message)
        }
    }
}
