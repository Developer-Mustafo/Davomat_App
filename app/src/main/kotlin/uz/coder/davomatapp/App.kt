package uz.coder.davomatapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import uz.coder.davomatapp.todo.LocaleHelper.setLocale

@HiltAndroidApp
class App : Application() {
    companion object{
        lateinit var application: Application
    }
    override fun attachBaseContext(base: Context) {
        val newBase = setLocale(base, "uz")
        super.attachBaseContext(newBase)
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}
