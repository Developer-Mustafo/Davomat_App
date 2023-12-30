package uz.coder.davomatapp.domain.davomat

data class Davomat(
    var id:Int = UNDEFINE_ID,
    val name:String,
    val surname:String,
    val gender:String,
    val davomat:String,
    val vaqt:String,
    val studentId:Int = UNDEFINE_ID
){
    companion object{
        const val UNDEFINE_ID = 0
    }
}