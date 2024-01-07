package uz.coder.davomatapp.presentation

import android.app.Application
import uz.coder.davomatapp.di.DaggerAppComponent

class App:Application() {
    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }
}