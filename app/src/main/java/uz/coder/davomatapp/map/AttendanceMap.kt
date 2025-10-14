package uz.coder.davomatapp.map

import uz.coder.davomatapp.model.Attendance
import uz.coder.davomatapp.model.CreateAttendance
import uz.coder.davomatapp.network.dto.AttendanceResponse
import uz.coder.davomatapp.network.dto.CreateAttendanceRequest
import java.time.LocalDate

class AttendanceMap {
    fun toAttendanceList(data: List<AttendanceResponse>?) = data?.map {
        Attendance(it.date?: LocalDate.now(), it.id?:0L, it.status?:"", it.studentId?:0L)
    }?:emptyList()

    fun toAttendanceRequest(createAttendance: CreateAttendance) = CreateAttendanceRequest(studentId = createAttendance.studentId, date = createAttendance.date, status = createAttendance.status)
    fun toAttendance(data: AttendanceResponse?) = Attendance(date=data?.date?: LocalDate.now(), id =  data?.id?:0L, status = data?.status?:"", studentId = data?.studentId?:0L)

}
