package uz.coder.davomatapp.todo

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.todayIn
import java.time.format.DateTimeFormatter
import kotlin.time.Clock
import java.time.LocalDate as JLocalDate


fun Context.isConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        ?: return false

    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}


fun parseString(str:String?): String{
    return str?.trim() ?: ""
}

fun String.isEmail():Int{
    if (this.isEmpty() || this.isBlank()) return EMPTY
    else if (this.length < 6) return SHORT
    else if (this.contains("@") && this.contains(".")) return OK
    else if (!this.contains("@") || !this.contains(".")) return EMAIL_NEEDS
    else if (this.contains('=') || this.contains(' ') || this.contains(',') || this.contains('?') || this.contains('/') || this.contains(':') || this.contains('!') || this.contains('-') || this.contains('~') || this.contains('`') || this.contains('*') || this.contains('$') || this.contains('#') || this.contains('%') || this.contains('^') || this.contains('&') || this.contains('(') || this.contains(')')) return ERROR_EMAIL
    return OK
}

fun String.isPassword(): Int{
    if (this.isEmpty() || this.isBlank()) return EMPTY
    else if (this.length < 6) return SHORT
    else if (this.contains("@") || this.contains(".") || this.contains('=') || this.contains(' ') || this.contains(',') || this.contains('?') || this.contains('/') || this.contains(':') || this.contains('!') || this.contains('-') || this.contains('~') || this.contains('`') || this.contains('*') || this.contains('$') || this.contains('#') || this.contains('%') || this.contains('^') || this.contains('&') || this.contains('(') || this.contains(')') || this.contains('\"') || this.contains('\'')) return ERROR_PASSWORD
    return OK
}
fun LocalDate.formatedDate(): String = this.formattedDate()

fun Context.logOut() {
    try {
        val filesDir = this.filesDir
        if (filesDir.exists()) {
            filesDir.deleteRecursively()
        }
        val cacheDir = this.cacheDir
        if (cacheDir.exists()) {
            cacheDir.deleteRecursively()
        }
        val externalFilesDir = this.getExternalFilesDir(null)
        if (externalFilesDir != null && externalFilesDir.exists()) {
            externalFilesDir.deleteRecursively()
        }
        val sharedPref = SharedPref.getInstance(this)
        sharedPref.clear()
        println("Logout successful: Files and SharedPreferences cleared")
    } catch (e: Exception) {
        println("Error during logout: ${e.message}")
    }
}
fun LocalDate.Companion.orToday(): LocalDate {
    return Clock.System.todayIn(TimeZone.currentSystemDefault())
}
fun LocalDate.formattedDate(): String {
    // kotlinx.datetime.LocalDate -> java.time.LocalDate
    val jDate = JLocalDate.of(this.year, month.number, day)
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return jDate.format(formatter)
}

/***
 * States***/
const val OK = 1
const val EMPTY = 0
const val ERROR_EMAIL = -1
const val EMAIL_NEEDS = -4
const val ERROR_PASSWORD = -2
const val SHORT = -7
const val ROLE_STUDENT = "ROLE_STUDENT"
const val ROLE_TEACHER = "ROLE_TEACHER"
const val ROLE_ADMIN = "ROLE_ADMIN"