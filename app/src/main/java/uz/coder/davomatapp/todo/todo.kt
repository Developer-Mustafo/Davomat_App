package uz.coder.davomatapp.todo

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun Context.isConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        ?: return false

    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
           capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
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

fun String.isRole(): Int{
    if (this.isEmpty() || this.isBlank()) return EMPTY
    else if (this.contains(ROLE_STUDENT) || this.contains(ROLE_TEACHER) || this.contains(ROLE_ADMIN)) return OK
    return ERROR_ROLE
}

/***
 * States***/
const val OK = 1
const val EMPTY = 0
const val ERROR_EMAIL = -1
const val EMAIL_NEEDS = -4
const val ERROR_PASSWORD = -2
const val ERROR_ROLE = -3
const val SHORT = -7

const val ROLE_STUDENT = "ROLE_STUDENT"
const val ROLE_TEACHER = "ROLE_TEACHER"
const val ROLE_ADMIN = "ROLE_ADMIN"