package uz.coder.davomatapp.data.davomat

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.coder.davomatapp.domain.davomat.Davomat

@Dao
interface DavomatDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(davomat: DavomatDbModel)
    @Query("delete from davomat where id = :id")
    suspend fun delete(id:Int)
    @Query("select * from davomat where id = :id limit 1")
    suspend fun getDavomatById(id: Int): DavomatDbModel
    @Query("select * from davomat where studentId = :id")
    fun getDavomatListByStudentId(id: Int):LiveData<List<DavomatDbModel>>
    @Query("select * from davomat where studentId =:sId and vaqt =:vaqt")
    suspend fun getDavomatOneTime(sId:Int,vaqt:String):DavomatDbModel
}