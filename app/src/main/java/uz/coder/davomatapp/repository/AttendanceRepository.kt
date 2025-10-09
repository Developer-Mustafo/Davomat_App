package uz.coder.davomatapp.repository

import kotlinx.coroutines.flow.Flow
import uz.coder.davomatapp.model.Attendance

interface AttendanceRepository {
    fun attendanceList(studentId: Long): Flow<List<Attendance>>
}