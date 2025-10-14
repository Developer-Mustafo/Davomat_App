package uz.coder.davomatapp.model

import java.time.LocalDate

data class CreateAttendance(
    val studentId:Long,
    val date: LocalDate,
    val status:String
)
