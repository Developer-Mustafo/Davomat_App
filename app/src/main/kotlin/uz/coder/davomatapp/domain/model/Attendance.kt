package uz.coder.davomatapp.domain.model

import kotlinx.datetime.LocalDate

data class Attendance(
    val date: LocalDate,
    val id: Long,
    val status: String,
    val studentId: Long
)