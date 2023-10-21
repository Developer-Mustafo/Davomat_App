package uz.coder.davomatapp.domain.coure

data class Course(
    val id:Int = UNDEFINE_ID,
    val img:Int,
    val name:String
) {
    companion object{
        const val UNDEFINE_ID = 0
    }
}