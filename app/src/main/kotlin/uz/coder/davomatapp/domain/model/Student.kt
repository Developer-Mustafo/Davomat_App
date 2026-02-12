package uz.coder.davomatapp.domain.model

import kotlinx.datetime.LocalDate

data class Student(
    val fullName: String,
    val groupId: Long,
    val id: Long,
    val phoneNumber: String,
    val userId: Long,
    val createdDate: LocalDate
)