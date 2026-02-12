package uz.coder.davomatapp.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.coder.davomatapp.domain.model.Attendance
import uz.coder.davomatapp.domain.model.CreateAttendance
import java.io.File

interface AttendanceRepository {
    fun attendanceList(studentId: Long): Flow<List<Attendance>>
    fun createAttendance(createAttendance: CreateAttendance): Flow<Attendance>
    fun uploadAttendanceExcel(file: File): Flow<String>
}