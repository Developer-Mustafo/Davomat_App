package uz.coder.davomatapp.model

import java.time.LocalDate

data class Attendance(
    val date: LocalDate,
    val id: Long,
    val status: String,
    val studentId: Long
)