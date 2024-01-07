package uz.coder.davomatapp.data.course

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.coder.davomatapp.domain.coure.Course
import javax.inject.Inject

@Entity(tableName = "course")
data class CourseDbModel @Inject constructor(
    val adminId:Int = Course.UNDEFINE_ID,
    @PrimaryKey(autoGenerate = true)
    val id:Int = Course.UNDEFINE_ID,
    val name:String
)