package uz.coder.davomatapp.network.dto

import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class RegisterResponse(
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("firstName")
    val firstName: String? = null,
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("lastName")
    val lastName: String? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("phoneNumber")
    val phoneNumber: String? = null,
    @SerializedName("role")
    val role: String? = null,
    @SerializedName("payedDate")
    val payedDate: LocalDate? = null
)