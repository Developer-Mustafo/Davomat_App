package uz.coder.davomatapp.domain.admin

data class Admin(
    var id:Int = UNDEFINE_ID,
    val name:String,
    val surname:String,
    val age:Int,
    val phone:String
){
    companion object{
        const val UNDEFINE_ID = 0
    }
}