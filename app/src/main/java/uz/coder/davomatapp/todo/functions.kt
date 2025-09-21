package uz.coder.davomatapp.todo

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter


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

fun LocalDate.formatedDate(): String{
    val result = this.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    return result
}

fun Context.logOut() {
    try {
        // Ilova fayllarini o'chirish (ichki xotira)
        val filesDir = this.filesDir
        if (filesDir.exists()) {
            filesDir.deleteRecursively()
        }

        // Kesh fayllarini o'chirish (ichki kesh)
        val cacheDir = this.cacheDir
        if (cacheDir.exists()) {
            cacheDir.deleteRecursively()
        }

        // Tashqi xotiradagi fayllarni o'chirish (agar ishlatilsa)
        val externalFilesDir = this.getExternalFilesDir(null)
        if (externalFilesDir != null && externalFilesDir.exists()) {
            externalFilesDir.deleteRecursively()
        }

        // SharedPreferences ni tozalash
        val sharedPref = SharedPref.getInstance(this)
        sharedPref.clear()

        // O'chirish muvaffaqiyatli bo'lganini loglash (ixtiyoriy)
        println("Logout successful: Files and SharedPreferences cleared")
    } catch (e: Exception) {
        // Xatolarni loglash yoki foydalanuvchiga xabar berish
        println("Error during logout: ${e.message}")
    }
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