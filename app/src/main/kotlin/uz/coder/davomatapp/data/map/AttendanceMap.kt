package uz.coder.davomatapp.data.map

import kotlinx.datetime.LocalDate
import uz.coder.davomatapp.domain.model.Attendance
import uz.coder.davomatapp.domain.model.CreateAttendance
import uz.coder.davomatapp.data.network.dto.AttendanceResponse
import uz.coder.davomatapp.data.network.dto.CreateAttendanceRequest
import uz.coder.davomatapp.todo.orToday
import javax.inject.Inject

class AttendanceMap @Inject constructor() {
    fun toAttendanceList(data: List<AttendanceResponse>?) = data?.map {
        Attendance(it.date?: LocalDate.orToday(), it.id?:0L, it.status?:"", it.studentId?:0L)
    }?:emptyList()

    fun toAttendanceRequest(createAttendance: CreateAttendance) = CreateAttendanceRequest(studentId = createAttendance.studentId, date = createAttendance.date, status = createAttendance.status)
    fun toAttendance(data: AttendanceResponse?) = Attendance(date=data?.date?: LocalDate.orToday(), id =  data?.id?:0L, status = data?.status?:"", studentId = data?.studentId?:0L)

}
