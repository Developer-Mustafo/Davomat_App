package uz.coder.davomatapp.domain.student

import javax.inject.Inject

data class Student @Inject constructor(
    var id:Int = UNDEFINE_ID,
    val name:String,
    val surname:String,
    val age:Int,
    val phone:String,
    val gender:String,
    val course:String,
    val courseId:Int = UNDEFINE_ID
){
    companion object{
        const val UNDEFINE_ID = 0
    }
}
