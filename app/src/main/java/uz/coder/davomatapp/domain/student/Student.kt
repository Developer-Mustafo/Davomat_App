package uz.coder.davomatapp.domain.student

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "student")
data class Student(
    val name:String,
    val surname:String,
    val phone:String,
    @PrimaryKey(autoGenerate = true)
    val id:Int = UNDIFINEID
):Serializable{
companion object{
    const val UNDIFINEID = -1
}
}
