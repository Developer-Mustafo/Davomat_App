package uz.coder.davomatapp.todo

import uz.coder.davomatapp.App.Companion.application


private val sharedPref = SharedPref.getInstance(application)

var userId: Long
    get()= sharedPref.getLong("id")
    set(value) = sharedPref.setLong("id", value)
var role: String
    get() = sharedPref.getString("role")
    set(value) = sharedPref.setString("role", value)
var token: String
    get() = sharedPref.getString("token")
    set(value) = sharedPref.setString("token", value)