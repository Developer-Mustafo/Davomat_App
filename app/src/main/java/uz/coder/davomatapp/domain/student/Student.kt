package uz.coder.davomatapp.domain.student

data class Student(
    var id:Int = UNDEFINE_ID,
    val name:String,
    val surname:String,
    val age:Int,
    val phone:String,
    val gender:String,
    val course:String
){
    companion object{
        const val UNDEFINE_ID = 0
    }
}
