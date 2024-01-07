package uz.coder.davomatapp.domain.admin

import javax.inject.Inject

data class Admin @Inject constructor(
    var id:Int = UNDEFINE_ID,
    val name:String,
    val email:String,
    val password:String,
    val phone:String,
    val gender:String
){
    companion object{
        const val UNDEFINE_ID = 0
    }
}