package uz.coder.davomatapp.domain.model

import kotlinx.datetime.LocalDate

data class CreateAttendance(
    val studentId:Long,
    val date: LocalDate,
    val status:String
)
