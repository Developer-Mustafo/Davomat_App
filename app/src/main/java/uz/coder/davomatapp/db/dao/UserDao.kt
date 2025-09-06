package uz.coder.davomatapp.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uz.coder.davomatapp.db.model.UserDbModel

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserDbModel)
    @Query("select * from user where user.id=:id")
    fun getUserById(id:Long): Flow<UserDbModel>
    @Query("delete from user where user.id=:id")
    suspend fun deleteUserById(id:Long)
}