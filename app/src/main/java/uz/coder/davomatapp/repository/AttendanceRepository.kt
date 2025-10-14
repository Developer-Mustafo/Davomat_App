package uz.coder.davomatapp.repository

import kotlinx.coroutines.flow.Flow
import uz.coder.davomatapp.model.Attendance
import uz.coder.davomatapp.model.CreateAttendance
import java.io.File

interface AttendanceRepository {
    fun attendanceList(studentId: Long): Flow<List<Attendance>>
    fun createAttendance(createAttendance: CreateAttendance): Flow<Attendance>
    fun uploadAttendanceExcel(file: File): Flow<String>
}