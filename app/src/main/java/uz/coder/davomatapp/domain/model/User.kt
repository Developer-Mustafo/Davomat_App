package uz.coder.davomatapp.domain.model

import kotlinx.datetime.LocalDate

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val payedDate: LocalDate,
    val role: String
)