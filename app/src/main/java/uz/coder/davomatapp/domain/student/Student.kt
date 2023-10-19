package uz.coder.davomatapp.domain.student

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "student")
data class Student(
    @PrimaryKey(autoGenerate = true)
    var id:Int = UNDEFINE_ID,
    val name:String,
    val surname:String,
    val phone:String

):Serializable{
    companion object{
        const val UNDEFINE_ID = 0
    }
}
