package uz.coder.davomatapp.data.admin

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.coder.davomatapp.domain.admin.Admin.Companion.UNDEFINE_ID
import javax.inject.Inject

@Entity(tableName = "admin")
data class AdminDbModel @Inject constructor(
    @PrimaryKey(autoGenerate = true)
    var id:Int = UNDEFINE_ID,
    val name:String,
    val email:String,
    val password:String,
    val phone:String,
    val gender:String
)