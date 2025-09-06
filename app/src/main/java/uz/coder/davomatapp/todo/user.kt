package uz.coder.davomatapp.todo

import uz.coder.davomatapp.App.application

private val sharedPref = SharedPref.getInstance(application)

var userId: Long
    get()= sharedPref.getLong("id")
    set(value) = sharedPref.setLong("id", value)
var role: String
    get() = sharedPref.getString("role")
    set(value) = sharedPref.setString("role", value)