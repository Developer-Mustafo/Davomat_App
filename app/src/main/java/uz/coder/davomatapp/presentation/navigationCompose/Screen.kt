package uz.coder.davomatapp.presentation.navigationCompose

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(){
    @Serializable
    data object Home: Screen()
    @Serializable
    data object CreateCourse: Screen()
    @Serializable
    data object CreateGroup: Screen()
    @Serializable
    data object CreateStudent: Screen()
    @Serializable
    data object CreateAttendance: Screen()
}