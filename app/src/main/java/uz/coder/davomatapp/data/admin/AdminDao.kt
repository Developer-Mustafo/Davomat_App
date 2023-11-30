package uz.coder.davomatapp.data.admin

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.coder.davomatapp.domain.admin.Admin

@Dao
interface AdminDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAdmin(admin: AdminDbModel)
    @Query("delete from admin where id = :id")
    suspend fun deleteAdmin(id:Int)
    @Query("select * from admin where id = :id")
    suspend fun getAdminId(id: Int): AdminDbModel
}