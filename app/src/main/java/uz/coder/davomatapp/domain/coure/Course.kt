package uz.coder.davomatapp.domain.coure

import javax.inject.Inject

data class Course @Inject constructor(
    val adminId:Int = UNDEFINE_ID,
    val id:Int = UNDEFINE_ID,
    val name:String
) {
    companion object{
        const val UNDEFINE_ID = 0
    }
}