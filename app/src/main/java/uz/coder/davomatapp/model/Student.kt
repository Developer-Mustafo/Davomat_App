package uz.coder.davomatapp.model

import java.time.LocalDate

data class Student(
    val fullName: String,
    val groupId: Long,
    val id: Long,
    val phoneNumber: String,
    val userId: Long,
    val createdDate: LocalDate
)