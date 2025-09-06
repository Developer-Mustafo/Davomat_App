package uz.coder.davomatapp.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "user")
data class UserDbModel(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val role: String,
    val payedDate: LocalDate,
    @PrimaryKey(autoGenerate = true)
    val id: Long = -1
)