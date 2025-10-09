package uz.coder.davomatapp.map

import uz.coder.davomatapp.model.Attendance
import uz.coder.davomatapp.network.dto.AttendanceResponse
import java.time.LocalDate

class AttendanceMap {
    fun toAttendanceList(data: List<AttendanceResponse>?) = data?.map {
        Attendance(it.date?: LocalDate.now(), it.id?:0L, it.status?:"", it.studentId?:0L)
    }?:emptyList()

}
