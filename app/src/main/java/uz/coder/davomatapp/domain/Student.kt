package uz.coder.davomatapp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
data class Student(
    val name:String,
    val surname:String,
    val phone:String,
    val list: List<String>,
    @PrimaryKey(autoGenerate = true)
    val id:Int = UNDIFINEID
){
companion object{
    const val UNDIFINEID = -1
}
}
