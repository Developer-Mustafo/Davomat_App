package uz.coder.davomatapp.data.student

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.coder.davomatapp.domain.student.Student
import javax.inject.Inject

@Entity(tableName = "student")
class StudentDbModel @Inject constructor(
    @PrimaryKey(autoGenerate = true)
    var id:Int = Student.UNDEFINE_ID,
    val name:String,
    val surname:String,
    val age:Int,
    val phone:String,
    val gender:String,
    val course:String,
    val courseId:Int = Student.UNDEFINE_ID
)