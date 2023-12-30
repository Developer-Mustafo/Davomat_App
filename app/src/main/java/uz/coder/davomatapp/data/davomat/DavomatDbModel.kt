package uz.coder.davomatapp.data.davomat

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.coder.davomatapp.domain.davomat.Davomat

@Entity(tableName = "davomat")
data class DavomatDbModel(
    @PrimaryKey(autoGenerate = true)
    var id:Int = Davomat.UNDEFINE_ID,
    val name:String,
    val surname:String,
    val gender:String,
    val davomat:String,
    val vaqt:String,
    val studentId:Int = Davomat.UNDEFINE_ID
)