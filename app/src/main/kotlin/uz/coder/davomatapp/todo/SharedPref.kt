package uz.coder.davomatapp.todo

import android.content.Context
import android.content.SharedPreferences
import uz.coder.davomatapp.R
import androidx.core.content.edit

class SharedPref private constructor(context: Context) {
    init {
        sharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    }

    fun setBoolean(key: String?, value: Boolean) {
        sharedPreferences.edit { putBoolean(key, value) }
    }

    fun getBoolean(key: String?): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun setString(key: String?, value: String?) {
        sharedPreferences.edit { putString(key, value) }
    }

    fun getString(key: String?): String {
        return sharedPreferences.getString(key, "")!!
    }

    fun setInt(key: String?, value: Int) {
        sharedPreferences.edit { putInt(key, value) }
    }

    fun getInt(key: String?): Int {
        return sharedPreferences.getInt(key, 0)
    }

    fun setLong(key: String?, value: Long) {
        sharedPreferences.edit { putLong(key, value) }
    }

    fun getLong(key: String?): Long {
        return sharedPreferences.getLong(key, 0)
    }

    fun setFloat(key: String?, value: Float) {
        sharedPreferences.edit {putFloat(key, value)}
    }

    fun getFloat(key: String?): Float {
        return sharedPreferences.getFloat(key, 0f)
    }

    fun clear() {
        sharedPreferences.edit { clear() }
    }

    companion object {
        private lateinit var sharedPreferences: SharedPreferences

        fun getInstance(context: Context): SharedPref {
            return SharedPref(context)
        }
    }
}
